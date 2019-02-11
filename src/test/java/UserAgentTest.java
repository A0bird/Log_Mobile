import java.io.UnsupportedEncodingException;

/**
 * @ClassName UserAgentTest
 * @Author lyd
 * @Date $ $
 * @Vesion 1.0
 * @Description userAgent的解析测试
 **/
public class UserAgentTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        /*System.out.println(UserAgentUtil.parserUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 SE 2.X MetaSr 1.0"));
        System.out.println(UserAgentUtil.parserUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36"));
        System.out.println(UserAgentUtil.parserUserAgent("Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)"));*/
        String a="1192.168.216.1^A1543847355.455^A192.168.216.111^A/index.jsp?en=e_pv&p_url=http%3A%2F%2Flocalhost%3A8080%2FMobile_log%2Fdemo3.jsp&p_ref=http%3A%2F%2Flocalhost%3A8080%2FMobile_log%2Fdemo.jsp&tt=%E6%B5%8B%E8%AF%95%E9%A1%B5%E9%9D%A23&ver=1&pl=website&sdk=js&u_ud=F10545DF-A972-40B7-B449-09713C98D77C&u_mid=liyadong&u_sd=618E94D1-874E-4562-BD93-F494BB0EF89D&c_time=1543847355075&l=zh-CN&b_iev=Mozilla%2F5.0%20(Windows%20NT%2010.0%3B%20Win64%3B%20x64)%20AppleWebKit%2F537.36%20(KHTML%2C%20like%20Gecko)%20Chrome%2F70.0.3538.102%20Safari%2F537.36&b_rst=1536*864";
        String b= java.net.URLDecoder.decode(a, "UTF-8");
        System.out.println("b = " + b);
        String[] str=b.split("\\^A");
        String[] str2=str[3].split("\\?");
        String[] str3=str2[1].split("&");


        for (String s : str) {
            System.out.println("s = " + s);
        }
        for (String s2 : str2) {
            System.out.println("s2 = " + s2);
        }
        for (String s : str3) {
            System.out.println("s = " + s);
        }

    }
}