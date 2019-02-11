package com.mobile.paser.hive;

import com.mobile.paser.modle.dim.base.KpiDimension;
import com.mobile.paser.service.DimensionOperateI;
import com.mobile.paser.service.serviceImpl.DimensionOPerateImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @Auther: 72428
 * @Date: 2018/12/12 20:34
 * @Description:
 */
public class view_depth extends UDF {
    DimensionOperateI dimensionOperateI= new DimensionOPerateImpl();
    public int evaluate (String kpiName){
        if(StringUtils.isEmpty(kpiName)){
            return -1;
        }
        KpiDimension kpiDimension = new KpiDimension(kpiName);
        int id = dimensionOperateI.getDimensionIdByDimension(kpiDimension);
        return id;
    }


}
