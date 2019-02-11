package com.mobile.common;

/**
 * @Auther: 72428
 * @Date: 2018/12/5 14:19
 * @Description:
 */
public enum  kpiTypeEnum {
    NEW_USER("new_user"),
    BROWSER_NEW_USER("browser_new_user"),
    ACTIVE_USER("active_user"),
    ACTIVE_MEMBER("active_members"),
    NEW_MEMBER("new_member"),
    BROWSER_ACTIVE_MEMBER("browser_active_members"),
    BROWSER_NEW_MEMBER("browser_active_members"),
    BROWSER_ACTIVE_USER("browser_active_user"),
    SESSIONS("sessions"),
    BROWSER_SESSIONS("browser_sessions"),
    HOURLY_ACTIVE_USER("hourly_active_user"),
    PV_BROWSER("pv_browser"),
    LOCATION("location")
    ;
    public String kpiName;

    kpiTypeEnum(String kpiName) {
        this.kpiName = kpiName;
    }
    /**
     * 功能描述:
     * 根据传进来的kpi 获取对应的kpiname
     * @param:
     * @return:
     * @auther: 72428
     * @date: 2018/12/5 14:23
     */

    public static  kpiTypeEnum valueofKpi(String kpi){
        for (kpiTypeEnum k: values()){
            if(k.kpiName.equals(kpi)){
                return k;
            }
        }
        return null;
    }



}
