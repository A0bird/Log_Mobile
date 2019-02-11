package com.mobile.paser.hive.order;

import com.mobile.paser.modle.dim.base.currencyTypeDimension;
import com.mobile.paser.service.DimensionOperateI;
import com.mobile.paser.service.serviceImpl.DimensionOPerateImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @Auther: 72428
 * @Date: 2018/12/13 14:36
 * @Description:
 */
public class currency_typeID extends UDF {

    DimensionOperateI operateI=new DimensionOPerateImpl();

    public int evaluate(String payType){
        if(StringUtils.isNotEmpty(payType)){
            currencyTypeDimension typeDimension = new currencyTypeDimension();

            int currencyId = operateI.getDimensionIdByDimension(typeDimension);
            return currencyId;
        }

        return -1;
    }
}

