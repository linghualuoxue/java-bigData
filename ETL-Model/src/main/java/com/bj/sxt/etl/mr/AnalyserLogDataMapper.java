package com.bj.sxt.etl.mr;

import com.bj.sxt.com.bj.sxt.util.LoggerUtil;
import com.bj.sxt.common.EventLogConstants;
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
    private int inputRecords,filterRecords;
    private byte[] family = Bytes.toBytes(EventLogConstants.EVENT_LOGS_FAMILY_NAME);
    private CRC32 crc32 = new CRC32();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            this.inputRecords++;
            log.debug("Analyse data of:"+value);
        Map<String,String> clientInfo = LoggerUtil.handleLog(value.toString());
        if(clientInfo.isEmpty()){
            filterRecords++;
            return;
        }
        String eventAlisName = clientInfo.get(EventLogConstants.LOG_COLUMN_NAME_EVENT_NAME);
        EventLogConstants.EventEnum event = EventLogConstants.EventEnum.valueOfAlias(eventAlisName);
        switch (event){
            case LAUNCH:
            case PAGEVIEW:
            case CHARGERREQUEST:
            case CHARGEREFUND:
            case CHARGESUCCESS:
            case EVENT:
                this.hanleData(clientInfo,event,context);
                break;
            default:
                this.filterRecords++;
                this.log.warn("该事件无法进行解析，事件名称为:"+eventAlisName);
        }


    }
}
