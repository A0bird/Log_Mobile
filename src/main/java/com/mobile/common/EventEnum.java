package com.mobile.common;

/**
 * @Auther: 72428
 * @Date: 2018/12/4 18:13
 * @Description:
 */
public enum EventEnum {
    LANUCH(1,"lanuch_event","e_1"),
    PAGEVIEW(2,"page_view_event","e_pv"),
    CHARGE_REQUEST(3,"change_request_event","e_crt"),
    CHANGE_SUCCESS(4,"change_success_event","e_cs"),
    CHANGE_REFUND(5,"change_refund_event","c_cr"),
    EVENT(6,"event","e_e"),
    ;
    public int id;
    public String eventName;
    public String alias;


    EventEnum(int id, String eventName, String alias) {
        this.id = id;
        this.eventName = eventName;
        this.alias = alias;
    }

    public static  EventEnum values(String aliasName){
        for (EventEnum eventEnum : values()) {
            if(eventEnum.alias.equals(aliasName)){
                return  eventEnum;
            }
        }
        throw new RuntimeException("aliasName 没有对应的event");
    }
}
