package com.mobile.etl.LogMapper;


import com.mobile.common.LogConstants;
import com.mobile.etl.util.LogUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Map;

/**
 * @Auther: 72428
 * @Date: 2018/12/3 23:20
 * @Description:
 */
public class logMap extends Mapper<LongWritable,Text,LogWritable,NullWritable>{

    private static final Logger logger=Logger.getLogger(logMap.class);
    private int filterRecords,outputRecoder,inputRecoder=0;
    private LogWritable k=new LogWritable();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        this.inputRecoder++;
        if(StringUtils.isEmpty(line)){
            this.filterRecords++;
            return;
        }
        Map<String,String> parserLog = LogUtil.parserLog(line);
        this.k.setEn(parserLog.get(LogConstants.LOG_EVENT));
        this.k.setVer(parserLog.get(LogConstants.LOG_VERSTON));
        this.k.setPl(parserLog.get(LogConstants.LOG_PLATFORM));
        this.k.setSdk(parserLog.get(LogConstants.LOG_SDK));
        this.k.setB_rst(parserLog.get(LogConstants.LOG_RESOULATION));
        this.k.setB_iev(parserLog.get(LogConstants.LOG_USER_AGENT));
        this.k.setU_ud(parserLog.get(LogConstants.LOG_UUID));
        this.k.setL(parserLog.get(LogConstants.LOG_LANGEUAGE));
        this.k.setU_mid(parserLog.get(LogConstants.LOG_MEMBERID));
        this.k.setU_sd(parserLog.get(LogConstants.LOG_SESSIOSNID));
        this.k.setC_time(parserLog.get(LogConstants.LOG_CLIENT_TIME));
        this.k.setP_url(parserLog.get(LogConstants.LOG_CURRENT_URL));
        this.k.setP_ref(parserLog.get(LogConstants.LOG_PREFIX_URL));
        this.k.setTt(parserLog.get(LogConstants.LOG_TITEL));
        this.k.setCa(parserLog.get(LogConstants.LOG_CATEGORY));
        this.k.setAc(parserLog.get(LogConstants.LOG_ACTION));
        this.k.setKv(parserLog.get(LogConstants.LOG_KEY_VALUE));
        this.k.setDu(parserLog.get(LogConstants.LOG_DURATION));
        this.k.setOid(parserLog.get(LogConstants.LOG_ORDER_ID));
        this.k.setOn(parserLog.get(LogConstants.LOG_ORDER_NAME));
        this.k.setCua(parserLog.get(LogConstants.LOG_ORDER_AMOUNT));
        this.k.setCut(parserLog.get(LogConstants.LOG_ORDER_TYPE));
        this.k.setPt(parserLog.get(LogConstants.LOG_ORDER_PAY_TYPE));
        this.k.setIp(parserLog.get(LogConstants.LOG_IP));
        this.k.setS_time(parserLog.get(LogConstants.LOG_SERVICE_TIME));
        this.k.setCountry(parserLog.get(LogConstants.LOG_COUNTRY));
        this.k.setProvince(parserLog.get(LogConstants.LOG_PROVINCE));
        this.k.setCity(parserLog.get(LogConstants.LOG_CITY));
        this.k.setBrowname(parserLog.get(LogConstants.LOG_BROWSERNAME));
        this.k.setBrowvresion(parserLog.get(LogConstants.LOG_BROWSERVERSION));
        this.k.setOs_name(parserLog.get(LogConstants.LOG_OSNAME));
        this.k.setOs_version(parserLog.get(LogConstants.LOG_OSVERSION));
        context.write(k, NullWritable.get());
        this.outputRecoder++;
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        logger.info("filterRecords:"+filterRecords+"inputRecoder:"+inputRecoder+"outputRecoder:"+outputRecoder);
    }
}
