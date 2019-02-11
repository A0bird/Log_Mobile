package com.mobile.paser.hive;

import com.mobile.paser.modle.dim.base.EventDimension;
import com.mobile.paser.service.DimensionOperateI;
import com.mobile.paser.service.serviceImpl.DimensionOPerateImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;

/**
 * @Auther: 72428
 * @Date: 2018/12/10 15:17
 * @Description:
 */
public class EventDimensionUDF extends UDF {
    DimensionOperateI operateI=new DimensionOPerateImpl();

    public IntWritable evaluate(String category,String action){
        if(StringUtils.isEmpty(category)){
            category=action="unknown";
        }
        if(StringUtils.isEmpty(action)){
            action="unknown";
        }
        EventDimension eventDimension=new EventDimension(category,action);
        int eventId=operateI.getDimensionIdByDimension(eventDimension);
        return new IntWritable(eventId);
    }


}
