package com.mobile.paser.modle.dim.base;

import com.mobile.paser.modle.dim.StatsBaseDimension;
import com.mobile.paser.modle.dim.StatsCommonDimension;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

/**
 * @Auther: 72428
 * @Date: 2018/12/5 21:33
 * @Description:  用户模块的维度类
 */
public class StatsUserDiemension extends StatsBaseDimension {

    StatsCommonDimension statsCommonDimension=new StatsCommonDimension();
    BrowserDiemenson browserDiemenson = new BrowserDiemenson();

    public StatsUserDiemension() {
    }

    public StatsUserDiemension(StatsCommonDimension statsCommonDimension, BrowserDiemenson browserDiemenson) {
        this.statsCommonDimension = statsCommonDimension;
        this.browserDiemenson = browserDiemenson;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        this.statsCommonDimension.write(dataOutput);
        this.browserDiemenson.write(dataOutput);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.statsCommonDimension.readFields(dataInput);
        this.browserDiemenson.readFields(dataInput);
    }

    @Override
    public int compareTo(BaseDimeension o) {
        if(this == o){
            return 0;
        }
        StatsUserDiemension other= (StatsUserDiemension) o;
        int temp =this.statsCommonDimension.compareTo(other.statsCommonDimension);
        if(temp !=0){
            return temp;
        }
        temp = this.browserDiemenson.compareTo(other.browserDiemenson);
        return  temp;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatsUserDiemension)) return false;
        StatsUserDiemension that = (StatsUserDiemension) o;
        return Objects.equals(statsCommonDimension, that.statsCommonDimension) &&
                Objects.equals(browserDiemenson, that.browserDiemenson);
    }

    @Override
    public int hashCode() {

        return Objects.hash(statsCommonDimension, browserDiemenson);
    }

    public StatsCommonDimension getStatsCommonDimension() {
        return statsCommonDimension;
    }

    public void setStatsCommonDimension(StatsCommonDimension statsCommonDimension) {
        this.statsCommonDimension = statsCommonDimension;
    }

    public BrowserDiemenson getBrowserDiemenson() {
        return browserDiemenson;
    }

    public void setBrowserDiemenson(BrowserDiemenson browserDiemenson) {
        this.browserDiemenson = browserDiemenson;
    }
}
