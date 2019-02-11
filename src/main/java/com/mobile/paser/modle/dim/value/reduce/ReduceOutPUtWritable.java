package com.mobile.paser.modle.dim.value.reduce;

import com.mobile.common.kpiTypeEnum;
import com.mobile.paser.modle.dim.value.StatsBaseOutputWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.WritableUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @Auther: 72428
 * @Date: 2018/12/5 14:31
 * @Description:
 */
public class ReduceOutPUtWritable  extends StatsBaseOutputWritable{

    private MapWritable value=new MapWritable();
    private kpiTypeEnum kpiname;

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        this.value.write(dataOutput);
        WritableUtils.writeEnum(dataOutput, kpiname);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.value.readFields(dataInput);
        WritableUtils.readEnum(dataInput,kpiTypeEnum.class );
    }
    @Override
    public kpiTypeEnum getKpi() {
        return this.kpiname;//将当前的kpi返回
    }

    public MapWritable getValue() {
        return value;
    }

    public void setValue(MapWritable value) {
        this.value = value;
    }

    public kpiTypeEnum getKpiname() {
        return kpiname;
    }

    public void setKpiname(kpiTypeEnum kpiname) {
        this.kpiname = kpiname;
    }

    @Override
    public String toString() {
        return  value +" "+ kpiname;
    }
}
