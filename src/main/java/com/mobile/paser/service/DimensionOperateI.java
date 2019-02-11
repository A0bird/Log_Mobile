package com.mobile.paser.service;

import com.mobile.paser.modle.dim.base.BaseDimeension;

/**
 * @Auther: 72428
 * @Date: 2018/12/5 23:08
 * @Description:
 */
public interface DimensionOperateI {

    /**
     * 功能描述:
     *   根据传入的维度，获得维度的id
     * @param:
     * @return:
     * @auther: 72428
     * @date: 2018/12/5 23:10
     */
    int getDimensionIdByDimension(BaseDimeension dimension);

}
