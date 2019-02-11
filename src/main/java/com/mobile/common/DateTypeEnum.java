package com.mobile.common;

/**
 * @Auther: 72428
 * @Date: 2018/12/5 11:05
 * @Description:
 */

public enum DateTypeEnum {
    YEAR("year"),
    SEASON("season"),
    MONTH("month"),
    WEEK("week"),
    DAY("day"),
    HOUR("hour");

    public  String type;

    DateTypeEnum (String type){
        this.type=type;
    }

    public  static DateTypeEnum valuseoftype(String type){
        return null;
    }
}

