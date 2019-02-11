package com.mobile.paser.modle.dim;

import com.mobile.paser.modle.dim.base.BaseDimeension;
import com.mobile.paser.modle.dim.base.DateDimension;
import com.mobile.paser.modle.dim.base.KpiDimension;
import com.mobile.paser.modle.dim.base.PlatfromDimension;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

/**
 * @Auther: 72428
 * @Date: 2018/12/5 11:51
 * @Description: 共有的维度类
 */
public class StatsCommonDimension extends StatsBaseDimension {
    private DateDimension dateDimension= new DateDimension();
    private PlatfromDimension platfromDimension=new PlatfromDimension();
    private KpiDimension kpiDimension=new KpiDimension();

    public StatsCommonDimension() {
    }

    public StatsCommonDimension(DateDimension dateDimension, PlatfromDimension platfromDimension, KpiDimension kpiDimension) {
        this.dateDimension = dateDimension;
        this.platfromDimension = platfromDimension;
        this.kpiDimension = kpiDimension;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        this.dateDimension.write(dataOutput);
        this.platfromDimension.write(dataOutput);
        this.kpiDimension.write(dataOutput);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.dateDimension.readFields(dataInput);
        this.platfromDimension.readFields(dataInput);
        this.kpiDimension.readFields(dataInput);
    }

    @Override
    public int compareTo(BaseDimeension o) {
        if(this == o){
            return 0;
        }
        StatsCommonDimension other= (StatsCommonDimension)o;
        int temp= this.dateDimension.compareTo(dateDimension);
        if(temp!=0){
            return temp;
        }
        temp = this.platfromDimension.compareTo(platfromDimension);
        if(temp!=0){
            return temp;
        }
        return this.kpiDimension.compareTo(other.kpiDimension);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatsCommonDimension)) return false;
        StatsCommonDimension that = (StatsCommonDimension) o;
        return Objects.equals(getDateDimension(), that.getDateDimension()) &&
                Objects.equals(getPlatfromDimension(), that.getPlatfromDimension()) &&
                Objects.equals(getKpiDimension(), that.getKpiDimension());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getDateDimension(), getPlatfromDimension(), getKpiDimension());
    }

    public DateDimension getDateDimension() {
        return dateDimension;
    }

    public void setDateDimension(DateDimension dateDimension) {
        this.dateDimension = dateDimension;
    }

    public PlatfromDimension getPlatfromDimension() {
        return platfromDimension;
    }

    public void setPlatfromDimension(PlatfromDimension platfromDimension) {
        this.platfromDimension = platfromDimension;
    }

    public KpiDimension getKpiDimension() {
        return kpiDimension;
    }

    public void setKpiDimension(KpiDimension kpiDimension) {
        this.kpiDimension = kpiDimension;
    }
}
