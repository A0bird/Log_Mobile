package com.mobile.etl.util;

import com.mobile.etl.util.ip.IPSeeker;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @ClassName IpUtil
 * @Author lyd
 * @Date $ $
 * @Vesion 1.0
 * @Description //TODO $
 **/
public class IpUtil {
    private static final Logger logger = Logger.getLogger(IpUtil.class);
    static RegionInfo info = null;
    /**
     * 解析ip的方法
     * @param ip 被解析的ip
     * @return
     */

    public static RegionInfo parserIp(String ip){
        if(StringUtils.isEmpty(ip)){
            return info;
        }
        //ip是一个正常值
        String country = IPSeeker.getInstance().getCountry(ip);
        if(StringUtils.isNotEmpty(country)){
            info = new RegionInfo();
            //判断country是否为局域网
            if(country.equals("局域网")){
                info.setCountry("中国");
                info.setProvince("北京市");
                info.setCity("昌平区");
            } else {
                //返回字段中是否包括省这个字，否则判断是前两个字符是否为北京、天津、上海、重庆和广西、内蒙、西藏、新疆、宁夏和香港、澳门、台湾

                //返回的字段中是否包括市
            }
        }
        return null;
    }


    /**
     * 封装ip解析出来的信息
     */
    public static class RegionInfo{
        private String DEFAULT_VALUE = "unknown";
        private String country = DEFAULT_VALUE;
        private String province = DEFAULT_VALUE;
        private String city = DEFAULT_VALUE;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        @Override
        public String toString() {
            return "RegionInfo{" +
                    country + '\t' + province + '\t' + city + '\t' + '}';
        }
    }
}