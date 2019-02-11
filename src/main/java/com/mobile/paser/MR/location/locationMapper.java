package com.mobile.paser.MR.location;

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
 * @Date: 2018/12/11 22:20
 * @Description:
 */
public class locationMapper extends Mapper<LongWritable,Text,StatsLocationDimension,TimeOutPutWriteable> {
    public static final Logger logger=Logger.getLogger(locationMapper.class);

    private StatsLocationDimension statsLocationDimension= new StatsLocationDimension();
    private TimeOutPutWriteable timeOutPutWriteable=new TimeOutPutWriteable();
    private StatsCommonDimension statsCommonDimension=new StatsCommonDimension();
    private DimensionOperateI dimensionOperateI= new DimensionOPerateImpl();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //分析日志得到
        //date_dimension_id` platform_dimension_id` kpiDimension location_dimension_id
        //uuid time

        String line = value.toString();
        String[] filds = line.split("\001");

        String time= filds[10].substring(0,10 );
        String plat=filds[2];
        String country=filds[25];
        String province=filds[26];
        String city=filds[27];

        DateDimension dateDimension =DateDimension.buildDate(Long.valueOf(time), DateTypeEnum.DAY);
        int dataId=dimensionOperateI.getDimensionIdByDimension(dateDimension);
        dateDimension.setId(dataId);

        PlatfromDimension platfromDimension= new PlatfromDimension(plat);
        int platID=dimensionOperateI.getDimensionIdByDimension(platfromDimension);
        dateDimension.setId(platID);

        loactionDimension loactionDimension = new loactionDimension(country,province,city);
        int loactionId=dimensionOperateI.getDimensionIdByDimension(loactionDimension);
        loactionDimension.setId(loactionId);

        KpiDimension kpiDimension = new KpiDimension(kpiTypeEnum.LOCATION.kpiName);
        int kpiID=dimensionOperateI.getDimensionIdByDimension(kpiDimension);
        kpiDimension.setId(kpiID);
        statsCommonDimension.setDateDimension(dateDimension);
        statsCommonDimension.setPlatfromDimension(platfromDimension);
        statsCommonDimension.setKpiDimension(kpiDimension);

        //2018-12-08 website location   uuid s_time
        //定义输出的k
        statsLocationDimension.setStatsCommonDimension(statsCommonDimension);
        statsLocationDimension.setLoactionDimension(loactionDimension);

        //定义输出的value

        timeOutPutWriteable.setId(filds[6]);
        timeOutPutWriteable.setTime(Long.valueOf(filds[24]));

        context.write(statsLocationDimension, timeOutPutWriteable);
    }
}
