package com.mobile.Util;

import com.mobile.common.DateTypeEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Auther: 72428
 * @Date: 2018/12/4 21:45
 * @Description:
 */
public class TimeUtil {

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd";

    //获取昨天的时间
    public static String getyesTonday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }


    public static long string2Long(String time) {
        return string2Long(time, DEFAULT_PATTERN);
    }

    private static long string2Long(String time, String defaultPattern) {

        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_PATTERN);
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //获取给定时间的年份
    public static int getDateInfo(long time, DateTypeEnum type) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
        String format = dateFormat.format(new Date(time));
        String[] split = format.split("/");
        if(type.equals(DateTypeEnum.YEAR)){
            return Integer.valueOf(split[0]);
        }
        if (type.equals(DateTypeEnum.SEASON)){
            int seeion=0;
            switch (Integer.valueOf(split[1])){
                case 1:
                case 2:
                case 3:
                     seeion=1;
                    break;
                case 4:
                case 5:
                case 6:
                    seeion=2;
                    break;
                case 7:
                case 8:
                case 9:
                    seeion=3;
                    break;
                case 10:
                case 11:
                case 12:
                    seeion=3;
                    break;
                    default:
                        seeion=-1;
            }

            return seeion;
        }
        if(type.equals(DateTypeEnum.MONTH)){
            return Integer.valueOf(split[1]);
        }
        if(type.equals(DateTypeEnum.WEEK)){
            Calendar calendar =Calendar.getInstance();
            calendar.setTime(new Date(time));
            return calendar.get(Calendar.WEEK_OF_YEAR);
        }
        if(type.equals(DateTypeEnum.DAY)){
            return Integer.valueOf(split[2]);
        }
        return -1;//没有匹配的type类型
    }

    public static long getFirstDayWeek(long time) {

        Calendar calendar =Calendar.getInstance();
        calendar.setTime(new Date(time));
        calendar.get(Calendar.DAY_OF_WEEK);
        int d = 0;
        if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -7;
        } else {
            d = 1 - calendar.get(Calendar.DAY_OF_WEEK);
        }
        calendar.add(Calendar.DAY_OF_WEEK, d);
        Date calendarTime = calendar.getTime();
        return  calendarTime.getTime();
    }

    public static  int getHourlyTonday(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        return hour;
    }

    public static void main(String[] args) {
        String time="1544483697";
        long l = TimeUtil.string2Long(time);
        System.out.println("l = " + l);

    }
}
