package com.mobile.paser.hive;

import com.mobile.paser.modle.dim.base.PlatfromDimension;
import com.mobile.paser.service.DimensionOperateI;
import com.mobile.paser.service.serviceImpl.DimensionOPerateImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;

/**
 * @Auther: 72428
 * @Date: 2018/12/10 15:09
 * @Description:
 */
public class PlatfromDimensionUDF extends UDF {
    DimensionOperateI operateI=new DimensionOPerateImpl();

    public IntWritable evaluate (String platFrom){
        if(StringUtils.isEmpty(platFrom)){
            return new IntWritable(-1);
        }
        PlatfromDimension platfromDimension=new PlatfromDimension(platFrom);
        int platfromID=operateI.getDimensionIdByDimension(platfromDimension);
        return new  IntWritable(platfromID);
    }

}
