package com.mobile.paser.MR.session;

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
import org.apache.hadoop.io.Text;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Auther: 72428
 * @Date: 2018/12/7 11:07
 * @Description: 为新增用户的ps赋值
 */
public class SeesionWriter implements ReduceOutPutFormatI {
    @Override
    public void outputWritter(Configuration conf, StatsBaseDimension key, StatsBaseOutputWritable value, PreparedStatement ps, DimensionOperateI convert) {
        ReduceOutPUtWritable v= (ReduceOutPUtWritable) value;
        StatsUserDiemension k= (StatsUserDiemension) key;
        String session = ((Text)v.getValue().get(new IntWritable(-1))).toString();
        String[] split = session.split(",");
        int sessions=Integer.valueOf(split[0]);
        int sessionLength=Integer.valueOf(split[1]);
        //new_browser_user
        int i=0;
        if(v.getKpi().kpiName.equals(kpiTypeEnum.BROWSER_SESSIONS.kpiName)) {
            try {
                ps.setInt(++i, k.getStatsCommonDimension().getDateDimension().getId());
                ps.setInt(++i, k.getStatsCommonDimension().getPlatfromDimension().getId());
                ps.setInt(++i, k.getBrowserDiemenson().getId());
                ps.setInt(++i, sessions);
                ps.setInt(++i, sessionLength);
                ps.setString(++i, conf.get(GlobalConstans.RUNNING_DATE));
                ps.setInt(++i, sessions);
                ps.setInt(++i, sessionLength);

                ps.addBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            try {
                ps.setInt(++i, k.getStatsCommonDimension().getDateDimension().getId());
                ps.setInt(++i, convert.getDimensionIdByDimension(k.getStatsCommonDimension().getPlatfromDimension()));
                ps.setInt(++i, sessions);
                ps.setInt(++i, sessionLength);
                ps.setString(++i, conf.get(GlobalConstans.RUNNING_DATE));
                ps.setInt(++i, sessions);
                ps.setInt(++i, sessionLength);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
}
