package com.mobile.paser.hive;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Auther: 72428
 * @Date: 2018/12/17 17:00
 * @Description:
 */
public class WeekYearUDF extends UDF{

    public  String  evaluate(String date){

        if(StringUtils.isEmpty(date)){
            return  "ERROR";
        }
        int weeks=0;
        int year=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = format.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parse);
            year=calendar.get(Calendar.YEAR);
             weeks=calendar.get(Calendar.WEEK_OF_YEAR);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(year)+String.valueOf(weeks);
    }

    public static void main(String[] args) {
        System.out.println(new WeekYearUDF().evaluate("2015-5-21"));
    }

}
