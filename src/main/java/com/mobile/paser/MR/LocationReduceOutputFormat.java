package com.mobile.paser.MR;

import com.mobile.paser.modle.dim.base.StatsLocationDimension;
import com.mobile.paser.modle.dim.value.StatsBaseOutputWritable;
import com.mobile.paser.service.DimensionOperateI;
import org.apache.hadoop.conf.Configuration;

import java.sql.PreparedStatement;

/**
 * @Auther: 72428
 * @Date: 2018/12/12 14:37
 * @Description:
 */
public interface LocationReduceOutputFormat  {


    void outputWritter(Configuration conf, StatsLocationDimension key, StatsBaseOutputWritable value
            , PreparedStatement ps, DimensionOperateI convert);
}
