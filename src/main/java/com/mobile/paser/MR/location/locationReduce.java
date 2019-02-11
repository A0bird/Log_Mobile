package com.mobile.paser.MR.location;

import com.mobile.common.kpiTypeEnum;
import com.mobile.paser.modle.dim.base.StatsLocationDimension;
import com.mobile.paser.modle.dim.value.map.TimeOutPutWriteable;
import com.mobile.paser.modle.dim.value.reduce.ReduceOutPUtWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashSet;

/**
 * @Auther: 72428
 * @Date: 2018/12/12 10:17
 * @Description:
 */
public class locationReduce extends Reducer<StatsLocationDimension,TimeOutPutWriteable,StatsLocationDimension,ReduceOutPUtWritable> {
    //2018-12-08 website location   uuid s_time
    //dataId platID locationId uuid去重个数
    private ReduceOutPUtWritable reduceoutvalue = new ReduceOutPUtWritable();
    HashSet<String> unque= new HashSet();
    MapWritable mapWritable= new MapWritable();
    @Override
    protected void reduce(StatsLocationDimension key, Iterable<TimeOutPutWriteable> values, Context context) throws IOException, InterruptedException {

        for (TimeOutPutWriteable value : values) {
            unque.add(value.getId());
        }

        mapWritable.put(new IntWritable(-1), new IntWritable(unque.size()));

        reduceoutvalue.setValue(mapWritable);
        reduceoutvalue.setKpiname(kpiTypeEnum.valueofKpi(key.getStatsCommonDimension().getKpiDimension().getKpi_name()));
        context.write(key, reduceoutvalue);

    }
}
