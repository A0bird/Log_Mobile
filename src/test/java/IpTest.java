import com.mobile.etl.util.IpUtil;
import com.mobile.etl.util.ip.IPSeeker;

import java.util.List;

/**
 * @ClassName IpTest
 * @Author lyd
 * @Date $ $
 * @Vesion 1.0
 * @Description 解析ip的测试类
 **/
public class IpTest {
    public static void main(String[] args) {
/*        System.out.println(IPSeeker.getInstance().getCountry("183.184.0.100"));
        String address=IPSeeker.getInstance().getCountry("183.184.0.100");
        IpUtil.RegionInfo info = IpUtil.parserIp("120.197.87.216");
        System.out.println(info.getCountry());
        System.out.println("info.getProvince() = " + info.getProvince());
        System.out.println("info.getCity = " + info.getCity());*/
        System.out.println(IPSeeker.getInstance().getCountry("0.255.255.255"));
        List<String> ips= IPSeeker.getInstance().getAllIp();
/*        for (String ip : ips) {
            System.out.println("ip = " + ip);
            IPbean result = AnalyseIp.getResult(ip);
            if (result.getCode() == 0){
                System.out.println("国家/地区：" + result.getCountry());
                System.out.println("省份：" + result.getRegion());
                System.out.println("城市：" + result.getCity());
                System.out.println("运营商：" + result.getIsp());
            }else {
                System.err.println("ip地址查询失败，请检查ip地址是否正确");
            }
        }*/
        for (String ip : ips) {
            System.out.println(IpUtil.parserIp(ip));
        }
    }
}