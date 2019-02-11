package com.mobile.etl.util.ip;

import org.nutz.http.Http;
import org.nutz.http.Response;
import org.nutz.json.Json;

import java.util.Map;

/**
 * @Auther: 72428
 * @Date: 2018/12/3 13:45
 * @Description:
 */
public class AnalyseIp {
    public  static  IPbean getResult(String ip){
        Response response = Http.get("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);
        IPbean result = new IPbean();
        if ((ip != null) && (response.getStatus() == 200)) {
            try
            {
                String content = response.getContent();
                Map<String, Object> contentMap = (Map)Json.fromJson(content);
                if (((Integer)contentMap.get("code")) == 0)
                {
                    Map<String, Object> dataMap = (Map)contentMap.get("data");
                    result.setCountry((String)dataMap.get("country"));
                    result.setRegion((String)dataMap.get("region"));
                    result.setCity((String)dataMap.get("city"));
                    result.setCounty((String)dataMap.get("county"));
                    result.setIsp((String)dataMap.get("isp"));
                    result.setArea((String)dataMap.get("area"));
                    result.setIp((String)dataMap.get("ip"));
                    result.setCode(0);
                    return result;
                }
            }
            catch (Exception localException) {}
        }
        result.setCode(-1);
        result.setCountry("XX");
        result.setRegion("XX");
        result.setCity("XX");
        result.setCounty("XX");
        result.setIsp("XX");
        result.setArea("XX");
        result.setIp(ip);
        return result;
    }
}
