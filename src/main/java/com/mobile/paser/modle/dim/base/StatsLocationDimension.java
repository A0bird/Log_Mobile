package com.mobile.paser.modle.dim.base;

import com.mobile.paser.modle.dim.StatsBaseDimension;
import com.mobile.paser.modle.dim.StatsCommonDimension;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

/**
 * @Auther: 72428
 * @Date: 2018/12/11 22:24
 * @Description:
 */
public class StatsLocationDimension extends StatsBaseDimension {

    private StatsCommonDimension statsCommonDimension= new StatsCommonDimension();
    private loactionDimension loactionDimension = new loactionDimension();

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        statsCommonDimension.write(dataOutput);
        loactionDimension.write(dataOutput);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        statsCommonDimension.readFields(dataInput);
        loactionDimension.readFields(dataInput);
    }

    @Override
    public int compareTo(BaseDimeension o) {
        if(this==o){
            return 0;
        }
        StatsLocationDimension other = (StatsLocationDimension) o;
        int temp = this.statsCommonDimension.compareTo(other.statsCommonDimension);
        if (temp != 0) {
        return temp;
        }
        return temp=this.loactionDimension.compareTo(other.loactionDimension);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatsLocationDimension)) return false;
        StatsLocationDimension that = (StatsLocationDimension) o;
        return Objects.equals(getStatsCommonDimension(), that.getStatsCommonDimension()) &&
                Objects.equals(getLoactionDimension(), that.getLoactionDimension());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getStatsCommonDimension(), getLoactionDimension());
    }

    public StatsCommonDimension getStatsCommonDimension() {
        return statsCommonDimension;
    }

    public void setStatsCommonDimension(StatsCommonDimension statsCommonDimension) {
        this.statsCommonDimension = statsCommonDimension;
    }

    public com.mobile.paser.modle.dim.base.loactionDimension getLoactionDimension() {
        return loactionDimension;
    }

    public void setLoactionDimension(com.mobile.paser.modle.dim.base.loactionDimension loactionDimension) {
        this.loactionDimension = loactionDimension;
    }
}
