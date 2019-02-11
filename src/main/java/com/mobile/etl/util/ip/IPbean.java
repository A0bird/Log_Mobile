package com.mobile.etl.util.ip;

/**
 * @Auther: 72428
 * @Date: 2018/12/3 09:04
 * @Description:
 *
 * {"code":0,"data":{"ip":"210.75.225.254","country":"\u4e2d\u56fd","area":"\u534e\u5317",
"region":"\u5317\u4eac\u5e02","city":"\u5317\u4eac\u5e02","county":"","isp":"\u7535\u4fe1",
"country_id":"86","area_id":"100000","region_id":"110000","city_id":"110000",
"county_id":"-1","isp_id":"100017"}}
 */
public class IPbean {
    private  int code ;
    private  String ip;
    private  String country;
    private  String area;
    private  String region;
    private  String city;
    private  String county;
    private  String isp;

    public IPbean() {
    }

    public IPbean(int code, String ip, String country, String area, String region, String city, String county, String isp) {
        this.code = code;
        this.ip = ip;
        this.country = country;
        this.area = area;
        this.region = region;
        this.city = city;
        this.county = county;
        this.isp = isp;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }
}
