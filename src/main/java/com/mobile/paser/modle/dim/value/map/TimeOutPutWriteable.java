package com.mobile.paser.modle.dim.value.map;

import com.mobile.common.kpiTypeEnum;
import com.mobile.paser.modle.dim.value.StatsBaseOutputWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

/**
 * @Auther: 72428
 * @Date: 2018/12/5 14:25
 * @Description:
 */
public class TimeOutPutWriteable  extends StatsBaseOutputWritable {

    private  String id;// 泛指 uid \ mid \sid
    private Long time;//传递的时间戳，用于统计session的时长

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.id);
        dataOutput.writeLong(time);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id=dataInput.readUTF();
        this.time=dataInput.readLong();
    }

    @Override
    public kpiTypeEnum getKpi() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeOutPutWriteable)) return false;
        TimeOutPutWriteable that = (TimeOutPutWriteable) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), time);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
