package com.mobile.paser.MR.location;


import com.mobile.common.GlobalConstans;
import com.mobile.common.kpiTypeEnum;
import com.mobile.paser.MR.ReduceOutPutFormatI;
import com.mobile.paser.modle.dim.StatsBaseDimension;
import com.mobile.paser.modle.dim.base.StatsLocationDimension;
import com.mobile.paser.modle.dim.value.StatsBaseOutputWritable;
import com.mobile.paser.modle.dim.value.reduce.ReduceOutPUtWritable;
import com.mobile.paser.service.DimensionOperateI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Auther: 72428
 * @Date: 2018/12/12 14:35
 * @Description:
 */
public class locationWriter implements ReduceOutPutFormatI {

    @Override
    public void outputWritter(Configuration conf, StatsBaseDimension key, StatsBaseOutputWritable value, PreparedStatement ps, DimensionOperateI convert) {
        StatsLocationDimension k=(StatsLocationDimension)key;
        ReduceOutPUtWritable v=(ReduceOutPUtWritable)value;

        int activeUsers = ((IntWritable) v.getValue().get(new IntWritable(-1))).get();

        int i=0;
        try {
            if(v.getKpi().kpiName.equals(kpiTypeEnum.LOCATION.kpiName)){
                ps.setInt(++i, k.getStatsCommonDimension().getDateDimension().getId());
                ps.setInt(++i, k.getStatsCommonDimension().getPlatfromDimension().getId());
                ps.setInt(++i, k.getLoactionDimension().getId());
                ps.setInt(++i, activeUsers);
                ps.setString(++i, conf.get(GlobalConstans.RUNNING_DATE));
                ps.setInt(++i, activeUsers);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
