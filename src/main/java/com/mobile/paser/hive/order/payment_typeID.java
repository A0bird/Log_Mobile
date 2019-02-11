package com.mobile.paser.hive.order;

import com.mobile.paser.modle.dim.base.payTypeDimension;
import com.mobile.paser.service.DimensionOperateI;
import com.mobile.paser.service.serviceImpl.DimensionOPerateImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;


/**
 * @Auther: 72428
 * @Date: 2018/12/13 14:36
 * @Description:
 */
public class payment_typeID extends UDF {
    DimensionOperateI operateI=new DimensionOPerateImpl();

    public int evaluate(String payType){
        if(StringUtils.isNotEmpty(payType)){
            payTypeDimension payTypeDimension= new payTypeDimension(payType);

            int payTypeId = operateI.getDimensionIdByDimension(payTypeDimension);
            return payTypeId;
        }

        return -1;
    }

    public static void main(String[] args) {
        System.out.println(new payment_typeID().evaluate("zhifubaoPay"));
    }
}
