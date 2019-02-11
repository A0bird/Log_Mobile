package com.mobile.paser.hive;


import com.mobile.Util.TimeUtil;
import com.mobile.common.DateTypeEnum;
import com.mobile.paser.modle.dim.base.DateDimension;
import com.mobile.paser.service.DimensionOperateI;
import com.mobile.paser.service.serviceImpl.DimensionOPerateImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;

/**
 * @Auther: 72428
 * @Date: 2018/12/10 09:56
 * @Description:
 */
public class DateDimensionUDF extends UDF {

       DimensionOperateI operateI=new DimensionOPerateImpl();
    public IntWritable evaluate(String date){
        if(StringUtils.isEmpty(date)){
            return  new IntWritable(-1);
        }
        DateDimension dateDimension = DateDimension.buildDate(TimeUtil.string2Long(date), DateTypeEnum.DAY);
        int dateDimensionID=operateI.getDimensionIdByDimension(dateDimension);
        return  new IntWritable(dateDimensionID);
    }

}
