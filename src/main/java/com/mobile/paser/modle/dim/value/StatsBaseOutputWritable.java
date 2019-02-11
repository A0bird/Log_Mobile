package com.mobile.paser.modle.dim.value;

import com.mobile.common.kpiTypeEnum;
import org.apache.hadoop.io.Writable;

/**
 * @Auther: 72428
 * @Date: 2018/12/5 14:27
 * @Description:
 */
public abstract class StatsBaseOutputWritable implements Writable{

     public abstract kpiTypeEnum getKpi();
}
