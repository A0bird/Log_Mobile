import com.mobile.etl.util.ip.IPSeeker;

/**
 * @ClassName IpTest
 * @Author lyd
 * @Date $ $
 * @Vesion 1.0
 * @Description 解析ip的测试类
 **/
public class IpTest {
    public static void main(String[] args) {
        System.out.println(IPSeeker.getInstance().getCountry("183.62.92.3"));
        System.out.println(IPSeeker.getInstance().getCountry("192.168.216.111"));
    }
}