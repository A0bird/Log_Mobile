package com.mobile.paser.MR;

import com.mobile.paser.modle.dim.StatsBaseDimension;
import com.mobile.paser.modle.dim.value.StatsBaseOutputWritable;
import com.mobile.paser.service.DimensionOperateI;
import org.apache.hadoop.conf.Configuration;

import java.sql.PreparedStatement;

/**
 * @Auther: 72428
 * @Date: 2018/12/7 10:01
 * @Description: 定义每一个输出sql赋值接口
 */
public interface ReduceOutPutFormatI  {
    /**
     * 功能描述:
     *   将reduce输出的key-value 值赋值到对应的sql语句
     * @param:
     * @return:
     * @auther: 72428
     * @date: 2018/12/7 10:06
     */
    void outputWritter(Configuration conf, StatsBaseDimension key, StatsBaseOutputWritable value
    , PreparedStatement ps, DimensionOperateI convert);
}
