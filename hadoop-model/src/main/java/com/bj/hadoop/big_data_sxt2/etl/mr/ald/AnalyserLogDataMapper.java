package com.bj.hadoop.big_data_sxt2.etl.mr.ald;

import com.bj.hadoop.big_data_sxt2.common.LogCommon;
import com.bj.hadoop.big_data_sxt2.etl.util.LogUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Map;
import java.util.zip.CRC32;

/**
 * Created by user on 2017/4/12.
 */
public class AnalyserLogDataMapper extends Mapper<LongWritable,Text,NullWritable,Put> {

    private final Logger log = Logger.getLogger(AnalyserLogDataMapper.class);
    private int inputRecords,filterRecords,outputRecords;
    private byte[] family = Bytes.toBytes(LogCommon.EVENT_LOGS_FAMILY_NAME);
    private CRC32 crc = new CRC32();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        inputRecords++;
        log.info("Analyse data of:"+value);
        //将log转化为map
        try {
            Map<String,String> clientInfo = LogUtils.handlelog(value.toString());

            //过滤解析失败的数据
            if(MapUtils.isEmpty(clientInfo)){
                filterRecords++;
                return;
            }

            String eventName = clientInfo.get(LogCommon.LOG_COLUMN_NAME_EVENT_NAME);
            LogCommon.EventEnum eventEnum = LogCommon.EventEnum.valueOfAlias(eventName);
            switch (eventEnum){
                case LAUNCH:
                case PAGEVIEW:
                case CHARGEREQUEST:
                case CHARGESUCCESS:
                case CHARGEREFUND:
                case EVENT:
                    this.handleData(clientInfo,eventEnum,context);
                    break;
                default:
                    this.filterRecords++;
                    this.log.warn("该事件无法解析,事件为:"+eventEnum);
            }
        } catch (Exception e) {
            this.filterRecords++;
            this.log.error("解析数据发生异常,异常为:"+e);
        }


    }

    private void handleData(Map<String, String> clientInfo, LogCommon.EventEnum event, Context context) {
        String uuid = clientInfo.get(LogCommon.LOG_COLUMN_NAME_UUID);//uuid
        String memberId = clientInfo.get(LogCommon.LOG_COLUMN_NAME_MEMBER_ID);//会员id
        String serverTime = clientInfo.get(LogCommon.LOG_COLUMN_NAME_SERVER_TIME);//服务器时间
        try {
            if (StringUtils.isNotEmpty(serverTime)){
                //去掉浏览器信息
                clientInfo.remove(LogCommon.LOG_COLUMN_NAME_USER_AGENT);
                String rowKey = this.generateRowKey(uuid,memberId,event.alias,serverTime);
                Put put = new Put(Bytes.toBytes(rowKey));
                for (Map.Entry<String, String> entry : clientInfo.entrySet()) {
                    if(StringUtils.isNotEmpty(entry.getKey()) && StringUtils.isNotEmpty(entry.getValue())){
                        put.add(family,Bytes.toBytes(entry.getKey()),Bytes.toBytes(entry.getValue()));
                    }
                }
                context.write(NullWritable.get(),put);
            }else{
                filterRecords++;
            }
        } catch (Exception e) {
            filterRecords++;
            log.error("数据发送失败:"+e);
        }

    }

    //生成rowKey,要求短和hash散列
    private String generateRowKey(String uuid, String memberId, String alias, String serverTime) {
        StringBuilder sb = new StringBuilder();
        sb.append(serverTime).append("_");
        crc.reset();
        if(StringUtils.isNotBlank(uuid)){
            crc.update(uuid.getBytes());
        }
        if(StringUtils.isNotEmpty(memberId)){
            crc.update(memberId.getBytes());
        }
        crc.update(alias.getBytes());
        sb.append(crc.getValue()%100000000L);
        return sb.toString();
    }


    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        super.cleanup(context);
        log.info("处理数据:"+inputRecords+"条;输出数据为:"+outputRecords+"条;过滤数据:"+filterRecords+"条;");
    }
}
