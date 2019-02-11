package com.mobile.paser.hive;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.util.Calendar;

/**
 * @Auther: 72428
 * @Date: 2018/12/15 20:49
 * @Description:
 */
public class AgeUDF extends UDF {

    public String evaluate(String birthday){
        if(StringUtils.isEmpty(birthday)||birthday.equals("NULL")||birthday==null){
            return "unknow";
        }
        int age=0;
        String[] birthdays = birthday.split("-");
        if(birthdays[0].toCharArray().length<4||birthdays[0].equals("0000")){
            return "unknow";
        }
        Integer year =Integer.valueOf(birthdays[0]) ;
        Integer month = Integer.valueOf(birthdays[1]);

        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH)+1;

        age = nowYear-year;
        if(nowMonth>month){
            age++;
        }else {
            age--;
        }
        if(age>150){
            return "unknow";
        }
        return String.valueOf(age);
    }

    public static void main(String[] args) {
        System.out.println(new AgeUDF().evaluate("NULL"));
    }

}
