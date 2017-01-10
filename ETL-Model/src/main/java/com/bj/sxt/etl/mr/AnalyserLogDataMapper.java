package com.bj.sxt.etl.mr;

import com.bj.sxt.com.bj.sxt.util.LoggerUtil;
import com.bj.sxt.common.EventLogConstants;
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
 * Created by Administrator on 2017/1/8.
 */
public class AnalyserLogDataMapper extends Mapper<LongWritable,Text,NullWritable,Put>{

    private final Logger log = Logger.getLogger(AnalyserLogDataMapper.class);
    private int inputRecords,filterRecords,outputRecords;
    private byte[] family = Bytes.toBytes(EventLogConstants.EVENT_LOGS_FAMILY_NAME);
    private CRC32 crc32 = new CRC32();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            this.inputRecords++;
            log.debug("Analyse data of:"+value);
        //解析日志
        Map<String,String> clientInfo = LoggerUtil.handleLog(value.toString());
        //解析失败的处理
        if(clientInfo.isEmpty()){
            filterRecords++;
            return;
        }
        //获取事件的名称
        try {
            String eventAlisName = clientInfo.get(EventLogConstants.LOG_COLUMN_NAME_EVENT_NAME);
            EventLogConstants.EventEnum eventEnum = EventLogConstants.EventEnum.valueOfAlias(eventAlisName);
            switch (eventEnum){
                case LAUNCH:
                case PAGEVIEW:
                case CHARGEREFUND:
                case CHARGERREQUEST:
                case CHARGESUCCESS:
                case EVENT:
                    this.handleData(clientInfo,eventEnum,context);
                    break;
                default:
                    this.filterRecords++;
                    this.log.warn("该事件没法解析,事件名称："+eventAlisName);
            }
        } catch (Exception e) {
            this.filterRecords++;
            this.log.error("数据处理异常"+value,e);
        }




    }

    private void handleData(Map<String, String> clientInfo, EventLogConstants.EventEnum event, Context context) {

        String uuid = clientInfo.get(EventLogConstants.LOG_COLUMN_NAME_UUID);
        String memberId = clientInfo.get(EventLogConstants.LOG_COLUMN_NAME_MEMBER_ID);
        String serverTime = clientInfo.get(EventLogConstants.LOG_COLUMN_NAME_SERVER_TIME);
        if(StringUtils.isNotBlank(serverTime)){
             clientInfo.remove(EventLogConstants.LOG_COLUMN_NAME_USER_AGENT);
             String rowKey = this.generateRowKey(uuid,memberId,event.alias,serverTime);
             Put put = new Put(Bytes.toBytes(rowKey));
            for (Map.Entry<String, String> entry : clientInfo.entrySet()) {
                if(StringUtils.isNotBlank(entry.getKey()) && StringUtils.isNotBlank(entry.getValue())){
                    put.add(family,Bytes.toBytes(entry.getKey()),Bytes.toBytes(entry.getValue()));
                }
            }
            this.outputRecords++;
        }else{
            this.filterRecords++;
        }
    }

    /**
     * 根据uuid,memberId,serverTime创建rowKey
     * @param uuid
     * @param memberId
     * @param alias
     * @param serverTime
     * @return
     */
    private String generateRowKey(String uuid, String memberId, String alias, String serverTime) {
         StringBuilder sb = new StringBuilder();
         sb.append(serverTime).append("_");
         this.crc32.reset();
         if(StringUtils.isNotBlank(uuid)){
             this.crc32.update(uuid.getBytes());
         }
         if(StringUtils.isNotBlank(memberId)){
             this.crc32.update(memberId.getBytes());
         }
         if(StringUtils.isNotBlank(alias)){
             this.crc32.update(alias.getBytes());
         }

         sb.append(this.crc32.getValue()/100000000L);
        return sb.toString();
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        super.cleanup(context);
        log.info("输入数据："+this.inputRecords+",输出数据："+this.outputRecords+",过滤数据："+this.filterRecords);
    }
}
