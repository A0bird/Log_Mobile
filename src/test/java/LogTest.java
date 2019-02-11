import com.mobile.etl.util.LogUtil;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @Auther: 72428
 * @Date: 2018/12/3 23:30
 * @Description:
 */
public class LogTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String log="192.168.216.1^A1543847355.455^A192.168.216.111^A/index.jsp?en=e_pv&p_url=http%3A%2F%2Flocalhost%3A8080%2FMobile_log%2Fdemo3.jsp&p_ref=http%3A%2F%2Flocalhost%3A8080%2FMobile_log%2Fdemo.jsp&tt=%E6%B5%8B%E8%AF%95%E9%A1%B5%E9%9D%A23&ver=1&pl=website&sdk=js&u_ud=F10545DF-A972-40B7-B449-09713C98D77C&u_mid=liyadong&u_sd=618E94D1-874E-4562-BD93-F494BB0EF89D&c_time=1543847355075&l=zh-CN&b_iev=Mozilla%2F5.0%20(Windows%20NT%2010.0%3B%20Win64%3B%20x64)%20AppleWebKit%2F537.36%20(KHTML%2C%20like%20Gecko)%20Chrome%2F70.0.3538.102%20Safari%2F537.36&b_rst=1536*864";
        Map<String,String> map = LogUtil.parserLog(log);


        for(Map.Entry<String,String> m:map.entrySet()){
            System.out.println(m);
        }
    }
}
