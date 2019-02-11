package com.mobile.paser.modle.dim.base;

import com.mobile.Util.TimeUtil;
import com.mobile.common.DateTypeEnum;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @Auther: 72428
 * @Date: 2018/12/5 10:30
 * @Description:
 */
public class DateDimension extends BaseDimeension {

    private  int id;
    private  int year;
    private  int season;
    private  int mounth;
    private  int week;
    private  int day;
    private Date calender=new Date();
    private  String type;

    public DateDimension() {
    }

    public DateDimension(int year, int season, int mounth, int week, int day) {
        this.year = year;
        this.season = season;
        this.mounth = mounth;
        this.week = week;
        this.day = day;
    }

    public DateDimension(int year, int season, int mounth, int week, int day, Date calender) {
        this(year,season,mounth,week,day);
        this.calender = calender;
    }

    public DateDimension(int id, int year, int season, int mounth, int week, int day, Date calender) {

        this(year,season,mounth,week,day,calender);
        this.id = id;
    }

    public DateDimension(int year, int season, int mounth, int week, int day,Date calender, String type) {
        this(year,season,mounth,week,day,calender);
        this.type = type;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.id);
        dataOutput.writeInt(this.year);
        dataOutput.writeInt(this.season);
        dataOutput.writeInt(this.mounth);
        dataOutput.writeInt(this.week);
        dataOutput.writeInt(this.day);
        dataOutput.writeLong(this.calender.getTime());
        dataOutput.writeUTF(this.type);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id=dataInput.readInt();
        this.year=dataInput.readInt();
        this.season=dataInput.readInt();
        this.mounth=dataInput.readInt();
        this.week=dataInput.readInt();
        this.day=dataInput.readInt();
        this.calender.setTime((dataInput.readLong()));
        this.type=dataInput.readUTF();
    }

    @Override
    public int compareTo(BaseDimeension o) {
        if(this == o){
            return 0;
        }
        DateDimension other=(DateDimension) o;
        int temp =this.id - other.id;
        if(temp != 0){
            return temp;
        }
         temp =this.year - other.year;
        if(temp != 0){
            return temp;
        }
         temp =this.season - other.season;
        if(temp != 0){
            return temp;
        }
         temp =this.mounth - other.mounth;
        if(temp != 0){
            return temp;
        }
        temp =this.week - other.week;
        if(temp != 0){
            return temp;
        }
        temp =this.day - other.day;
        if(temp != 0){
            return temp;
        }

        return this.type.compareTo(other.type);

    }

    /**
     * 功能描述:
     *  获取统计值的统计周期额时间维度
     * @param:
     * @return:
     * @auther: 72428
     * @date: 2018/12/5 19:32
     */

    public static  DateDimension buildDate(long time , DateTypeEnum type){

        Calendar calendar = Calendar.getInstance();
        calendar.clear();//清空时间，获取创建来的当前时间
        //获取年,精确到改年份的1月1号
        int year = TimeUtil.getDateInfo(time,DateTypeEnum.YEAR);
        if(type.equals(DateTypeEnum.YEAR)){
            calendar.set(year, 0, 1);
            return  new  DateDimension(year,1,1,1,1,calendar.getTime(),type.type);
        }
        //获取当前季度所在的第一个月份
        int session = TimeUtil.getDateInfo(time,DateTypeEnum.SEASON);
        if(type.equals(DateTypeEnum.SEASON)){
            int mounth=session*3-2;
            calendar.set(year, mounth-1, 1);
            return  new  DateDimension(year,session,mounth,1,1,calendar.getTime(),type.type);
        }
        //获取当前月份的第一天
        int moun = TimeUtil.getDateInfo(time,DateTypeEnum.MONTH);
        if(type.equals(DateTypeEnum.MONTH)){
            calendar.set(year, moun-1, 1);
            return  new  DateDimension(year,session,moun,1,1,calendar.getTime(),type.type);
        }
        //获取周的第一天
        int week = TimeUtil.getDateInfo(time,DateTypeEnum.WEEK);
        if(type.equals(DateTypeEnum.WEEK)){
            long firstday= TimeUtil.getFirstDayWeek(time);
            year=TimeUtil.getDateInfo(firstday, DateTypeEnum.YEAR);
            session=TimeUtil.getDateInfo(firstday, DateTypeEnum.SEASON);
            moun= TimeUtil.getDateInfo(firstday, DateTypeEnum.MONTH);
            calendar.set(year, moun-1, 1);
            return  new  DateDimension(year,session,moun,week,1,calendar.getTime(),type.type);
        }
        //精确到天
        int day = TimeUtil.getDateInfo(time,DateTypeEnum.DAY);
        if(type.equals(DateTypeEnum.DAY)){
            calendar.set(year, moun-1, day);
            return  new  DateDimension(year,session,moun,week,day,calendar.getTime(),type.type);
        }
        throw  new RuntimeException("没有匹配到DateDimension中的类型");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateDimension)) return false;
        DateDimension that = (DateDimension) o;
        return getId() == that.getId() &&
                getYear() == that.getYear() &&
                getSeason() == that.getSeason() &&
                getMounth() == that.getMounth() &&
                getWeek() == that.getWeek() &&
                getDay() == that.getDay() &&
                Objects.equals(getCalender(), that.getCalender()) &&
                Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getYear(), getSeason(), getMounth(), getWeek(), getDay(), getCalender(), getType());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getMounth() {
        return mounth;
    }

    public void setMounth(int mounth) {
        this.mounth = mounth;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Date getCalender() {
        return calender;
    }

    public void setCalender(Date calender) {
        this.calender = calender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
