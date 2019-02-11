package com.mobile.etl.util;


import com.mobile.common.LogConstants;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName LogUtil
 * @Author lyd
 * @Date $ $
 * @Vesion 1.0
 * @Description 将hdfs中的数据的每一行都进行ip、useragent、时间戳等的解析
 **/
public class LogUtil {


    /**
     * @param log 一行日志
     * @return 将解析后的k-v存储到map中，以便etl的mapper进行使用
     */
    public static Map parserLog(String log) throws UnsupportedEncodingException {
        String str = URLDecoder.decode(log, "UTF-8");
        if (str.isEmpty()) {
            return null;
        }
        String[] fileds = str.split(LogConstants.LOG_SPAOT);
        ConcurrentHashMap<String, String> info = new ConcurrentHashMap<>();
        if (fileds.length == 4) {
            info.put(LogConstants.LOG_IP, fileds[0]);
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date((Long.valueOf(fileds[1].substring(0, 10)))*1000);
            info.put(LogConstants.LOG_SERVICE_TIME, fileds[1].substring(0, 10));
            //解析ip地址的信息
            analyzeUrl(fileds[3],info);
            analyzeIp(info);
            analyzeUseragent(info);
        }
        return info;

    }

    private static void analyzeUseragent(ConcurrentHashMap<String, String> info) {
        String useragent = info.getOrDefault(LogConstants.LOG_USER_AGENT, "UNKNOW");
        UserAgentUtil.UserAgentInfo userAgentInfo = UserAgentUtil.parserUserAgent(useragent);
        info.put(LogConstants.LOG_BROWSERNAME, userAgentInfo.getBrowserName());

        info.put(LogConstants.LOG_BROWSERVERSION, userAgentInfo.getBrowserVersion());
        info.put(LogConstants.LOG_OSNAME, userAgentInfo.getOsName());
        info.put(LogConstants.LOG_OSVERSION, userAgentInfo.getOsVersion());
    }

    private static void analyzeUrl(String filed, ConcurrentHashMap<String, String> info) {
        if(StringUtils.isNotEmpty(filed)){
            int index = filed.indexOf("?");
            String url = filed.substring(index + 1);
            try {
                String kvs=URLDecoder.decode(url, "utf-8");
                String[] fileds = kvs.split("&");

                for (String s : fileds) {
                    String[] kv = s.split("=");
                    if(kv.length==2){
                        String k=kv[0];
                        String v=kv[1];
                        if(k!=null && !k.trim().isEmpty()){
                            info.put(k,v );
                        }
                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private static void analyzeIp(ConcurrentHashMap<String, String> info) {
        String ip = info.getOrDefault(LogConstants.LOG_IP, "unknow");
        IpUtil.RegionInfo regionInfo = IpUtil.parserIp(ip);
        info.put(LogConstants.LOG_COUNTRY, regionInfo.getCountry());
        info.put(LogConstants.LOG_PROVINCE, regionInfo.getProvince());
        info.put(LogConstants.LOG_CITY, regionInfo.getCity());
    }


}