package com.mobile.paser.MR.AU;

import com.mobile.common.DateTypeEnum;
import com.mobile.common.kpiTypeEnum;
import com.mobile.paser.modle.dim.StatsCommonDimension;
import com.mobile.paser.modle.dim.base.*;
import com.mobile.paser.modle.dim.value.map.TimeOutPutWriteable;
import com.mobile.paser.service.DimensionOperateI;
import com.mobile.paser.service.serviceImpl.DimensionOPerateImpl;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * @Auther: 72428
 * @Date: 2018/12/5 21:57
 * @Description:
 *      解析一行日志
 *      得到的结果为 StatsUserDiemension-》包含（DateDimension，PlatfromDimension，KpiDimension）
 *      DateDimension 用户登录或者注册的时间-》解析为年月日天--
 *      PlatfromDimension 用户登录和注册的平台
 *      KpiDimension kpi 指标（new_user,browser_new_user,active_user）
 *
 *      TimeOutPutWriteable uid \ mid \sid
 */
public class ActiveUserMapper extends Mapper<LongWritable,Text,StatsUserDiemension,TimeOutPutWriteable> {
    public static final Logger logger=Logger.getLogger(ActiveUserMapper.class);
    private StatsUserDiemension statsUserDiemension=new StatsUserDiemension();
    private DimensionOperateI dimensionOperateI=new DimensionOPerateImpl();
    private StatsCommonDimension commonDimension=new StatsCommonDimension();
    private TimeOutPutWriteable outPutWriteable=new TimeOutPutWriteable();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] split = line.split("\001");

        String time=split[10].substring(0, 10);
        long aLong = Long.valueOf(time);


        //k->DateDimension
        DateDimension dateDimension = DateDimension.buildDate(aLong, DateTypeEnum.DAY);
        int dateID=dimensionOperateI.getDimensionIdByDimension(dateDimension);
        dateDimension.setId(dateID);
        //k->PlatfromDimension
        PlatfromDimension platfromDimension=new PlatfromDimension(split[2]);
        int platID=dimensionOperateI.getDimensionIdByDimension(platfromDimension);
        platfromDimension.setId(platID);


        //value->TimeOutPutWriteable
        outPutWriteable.setId(split[6]);//uuid 用户唯一标识
        outPutWriteable.setTime(Long.valueOf(split[24]));//获取

        //k->KpiDimension
        // kpi browser_new_user
        KpiDimension kpiDimension = new KpiDimension(kpiTypeEnum.BROWSER_ACTIVE_USER.kpiName);
        int kpiID=dimensionOperateI.getDimensionIdByDimension(kpiDimension);
        kpiDimension.setId(kpiID);

        //kpi browser_active_user
        //StatsCommonDimension 获取到值
        //k->BrowserDiemenson
        BrowserDiemenson browserDiemenson = new BrowserDiemenson(split[28],split[29]);
        int browerId=dimensionOperateI.getDimensionIdByDimension(browserDiemenson);
        browserDiemenson.setId(browerId);
        commonDimension.setDateDimension(dateDimension);
        commonDimension.setPlatfromDimension(platfromDimension);
        commonDimension.setKpiDimension(kpiDimension);
        //StatsUserDiemension获取到值
        statsUserDiemension.setBrowserDiemenson(browserDiemenson);
        statsUserDiemension.setStatsCommonDimension(commonDimension);
        context.write(statsUserDiemension, outPutWriteable);

        // kpi new_user
        KpiDimension kpinewUser = new KpiDimension(kpiTypeEnum.ACTIVE_USER.kpiName);
        kpiID=dimensionOperateI.getDimensionIdByDimension(kpinewUser);
        kpinewUser.setId(kpiID);
        BrowserDiemenson defualtBrower = new BrowserDiemenson("","");
        commonDimension.setKpiDimension(kpinewUser);
        commonDimension.setPlatfromDimension(platfromDimension);
        commonDimension.setDateDimension(dateDimension);
        statsUserDiemension.setBrowserDiemenson(defualtBrower);
        statsUserDiemension.setStatsCommonDimension(commonDimension);
        context.write(statsUserDiemension, outPutWriteable);

        //kpi hourly_active_user
        KpiDimension kpiHourly = new KpiDimension(kpiTypeEnum.HOURLY_ACTIVE_USER.kpiName);
        kpiID=dimensionOperateI.getDimensionIdByDimension(kpiHourly);
        kpiHourly.setId(kpiID);
        BrowserDiemenson hourBrower=new BrowserDiemenson("","");

        //reduce 端的key输出->commonDimension->BrowserDiemenson
        commonDimension.setDateDimension(dateDimension);
        commonDimension.setKpiDimension(kpiHourly);
        commonDimension.setPlatfromDimension(platfromDimension);
        statsUserDiemension.setBrowserDiemenson(hourBrower);
        statsUserDiemension.setStatsCommonDimension(commonDimension);

        context.write(statsUserDiemension, outPutWriteable);
    }
}
