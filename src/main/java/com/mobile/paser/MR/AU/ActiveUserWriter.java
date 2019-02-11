package com.mobile.paser.MR.AU;

import com.mobile.common.GlobalConstans;
import com.mobile.common.kpiTypeEnum;
import com.mobile.paser.MR.ReduceOutPutFormatI;
import com.mobile.paser.modle.dim.StatsBaseDimension;
import com.mobile.paser.modle.dim.base.StatsUserDiemension;
import com.mobile.paser.modle.dim.value.StatsBaseOutputWritable;
import com.mobile.paser.modle.dim.value.reduce.ReduceOutPUtWritable;
import com.mobile.paser.service.DimensionOperateI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Auther: 72428
 * @Date: 2018/12/7 11:07
 * @Description: 为新增用户的ps赋值
 */
public class ActiveUserWriter implements ReduceOutPutFormatI {
    @Override
    public void outputWritter(Configuration conf, StatsBaseDimension key, StatsBaseOutputWritable value, PreparedStatement ps, DimensionOperateI convert) {
        ReduceOutPUtWritable v= (ReduceOutPUtWritable) value;
        StatsUserDiemension k= (StatsUserDiemension) key;

        //new_browser_user
        int j=0;
        if(v.getKpi().kpiName.equals(kpiTypeEnum.BROWSER_ACTIVE_USER.kpiName)) {
            int active_members =((IntWritable)v.getValue().get(new IntWritable(-1))).get();
            try {
                ps.setInt(++j, k.getStatsCommonDimension().getDateDimension().getId());
                ps.setInt(++j, k.getStatsCommonDimension().getPlatfromDimension().getId());
                ps.setInt(++j, k.getBrowserDiemenson().getId());
                ps.setInt(++j, active_members);
                ps.setString(++j, conf.get(GlobalConstans.RUNNING_DATE));
                ps.setInt(++j, active_members);

                ps.addBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(v.getKpi().kpiName.equals(kpiTypeEnum.ACTIVE_USER.kpiName)){
            int active_members =((IntWritable)v.getValue().get(new IntWritable(-1))).get();
            try {
                ps.setInt(++j, k.getStatsCommonDimension().getDateDimension().getId());
                System.out.println(k.getStatsCommonDimension().getDateDimension().getId());
                ps.setInt(++j, convert.getDimensionIdByDimension(k.getStatsCommonDimension().getPlatfromDimension()));
                ps.setInt(++j, active_members);
                ps.setString(++j, conf.get(GlobalConstans.RUNNING_DATE));
                ps.setInt(++j, active_members);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(v.getKpi().kpiName.equals(kpiTypeEnum.HOURLY_ACTIVE_USER.kpiName)) {
            try {
                ps.setInt(++j, k.getStatsCommonDimension().getDateDimension().getId());
                ps.setInt(++j, k.getStatsCommonDimension().getPlatfromDimension().getId());
                ps.setInt(++j, k.getStatsCommonDimension().getKpiDimension().getId());
                System.out.println(k.getStatsCommonDimension().getKpiDimension().getId());
                for(j++;j<28;j++){
                    ps.setInt(j,((IntWritable)v.getValue().get(new IntWritable(j-4))).get());
                    ps.setInt(j+25,((IntWritable)v.getValue().get(new IntWritable(j-4))).get());
                }
                ps.setString(j, conf.get(GlobalConstans.RUNNING_DATE));
                ps.addBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
}
