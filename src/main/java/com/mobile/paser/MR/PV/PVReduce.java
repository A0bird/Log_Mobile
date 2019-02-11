package com.mobile.paser.MR.PV;

import com.mobile.common.kpiTypeEnum;
import com.mobile.paser.modle.dim.base.StatsUserDiemension;
import com.mobile.paser.modle.dim.value.map.TimeOutPutWriteable;
import com.mobile.paser.modle.dim.value.reduce.ReduceOutPUtWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @Auther: 72428
 * @Date: 2018/12/5 21:57
 * @Description:
 *      k->StatsUserDiemension-》
 *      dimension_browser（浏览器的维度），dimension_date（时间维度），dimension_kpi（kpi维度），dimension_platform（平台维度）
 *      v->ReduceOutPUtWritable kpiname+mapWritable<k,新增用户数>
 *
 */
public class PVReduce extends Reducer<StatsUserDiemension,TimeOutPutWriteable,StatsUserDiemension,ReduceOutPUtWritable>{
    private ReduceOutPUtWritable reduceoutvalue = new ReduceOutPUtWritable();

    @Override
    protected void reduce(StatsUserDiemension key, Iterable<TimeOutPutWriteable> values, Context context) throws IOException, InterruptedException {

        int count=0;
        for (TimeOutPutWriteable value : values) {
             count++;
        }

        MapWritable mapWritable=new MapWritable();
        mapWritable.put(new IntWritable(-1), new IntWritable(count));

        reduceoutvalue.setValue(mapWritable);
        reduceoutvalue.setKpiname(kpiTypeEnum.valueofKpi(key.getStatsCommonDimension().getKpiDimension().getKpi_name()));

        context.write(key, reduceoutvalue);


    }
}
