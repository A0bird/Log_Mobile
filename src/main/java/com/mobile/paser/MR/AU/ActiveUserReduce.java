package com.mobile.paser.MR.AU;

import com.mobile.Util.TimeUtil;
import com.mobile.common.kpiTypeEnum;
import com.mobile.paser.modle.dim.base.StatsUserDiemension;
import com.mobile.paser.modle.dim.value.map.TimeOutPutWriteable;
import com.mobile.paser.modle.dim.value.reduce.ReduceOutPUtWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Auther: 72428
 * @Date: 2018/12/5 21:57
 * @Description:
 *      k->StatsUserDiemension-》
 *      dimension_browser（浏览器的维度），dimension_date（时间维度），dimension_kpi（kpi维度），dimension_platform（平台维度）
 *      v->ReduceOutPUtWritable kpiname+mapWritable<k,新增用户数>
 *
 */
public class ActiveUserReduce extends Reducer<StatsUserDiemension,TimeOutPutWriteable,StatsUserDiemension,ReduceOutPUtWritable>{
    private ReduceOutPUtWritable reduceoutvalue = new ReduceOutPUtWritable();
    private MapWritable mapWritable=new MapWritable();
    private Set<String> unqune=new HashSet<>();
    private HashMap <Integer,HashSet<String>> hourlyMap= new HashMap();
    private MapWritable hourlyMapWritable=new MapWritable();
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        //循环初始化
        for (int i = 0;i<24;i++){
            hourlyMap.put(i,new HashSet<String>());
            hourlyMapWritable.put(new IntWritable(i),new IntWritable(0));
        }
    }
    @Override
    protected void reduce(StatsUserDiemension key, Iterable<TimeOutPutWriteable> values, Context context) throws IOException, InterruptedException {

        if(key.getStatsCommonDimension().getKpiDimension().getKpi_name().equals(kpiTypeEnum.HOURLY_ACTIVE_USER.kpiName)){
            //将每个小时的活跃用户放入到hourNums
            for (TimeOutPutWriteable hourly : values) {
                int hourlyTonday = TimeUtil.getHourlyTonday(hourly.getTime()*1000);
                hourlyMap.get(hourlyTonday).add(hourly.getId());
            }
            for (Map.Entry<Integer, HashSet<String>> entry : hourlyMap.entrySet()) {
                this.hourlyMapWritable.put(new IntWritable(entry.getKey()), new IntWritable(entry.getValue().size()));
            }
            reduceoutvalue.setValue(hourlyMapWritable);
            reduceoutvalue.setKpiname(kpiTypeEnum.valueofKpi(key.getStatsCommonDimension().getKpiDimension().getKpi_name()));
            context.write(key, reduceoutvalue);
        }else {
            for (TimeOutPutWriteable value : values) {
                unqune.add(value.getId());

            }
            mapWritable.put(new IntWritable(-1), new IntWritable(unqune.size()));
            reduceoutvalue.setValue(mapWritable);
            reduceoutvalue.setKpiname(kpiTypeEnum.valueofKpi(key.getStatsCommonDimension().getKpiDimension().getKpi_name()));
            context.write(key, reduceoutvalue);
        }





    }
}
