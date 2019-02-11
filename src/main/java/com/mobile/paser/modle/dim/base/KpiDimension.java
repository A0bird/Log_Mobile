package com.mobile.paser.modle.dim.base;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

/**
 * @Auther: 72428
 * @Date: 2018/12/5 10:26
 * @Description:
 */
public class KpiDimension extends BaseDimeension{

    private  int id;
    private  String kpi_name;

    public KpiDimension() {
    }

    public KpiDimension(String kpi_name) {
        this.kpi_name = kpi_name;
    }

    public KpiDimension(int id, String kpi_name) {
        this.id = id;
        this.kpi_name = kpi_name;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.id);
        dataOutput.writeUTF(this.kpi_name);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id=dataInput.readInt();
        this.kpi_name=dataInput.readUTF();
    }

    @Override
    public int compareTo(BaseDimeension o) {
        if(this == o){
            return 0;
        }
        KpiDimension other=(KpiDimension)o;
        int temp =this.id-other.id;
        if(temp!=0){
            return temp;
        }
        temp = this.kpi_name.compareTo(other.kpi_name);

        return temp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KpiDimension)) return false;
        KpiDimension that = (KpiDimension) o;
        return id == that.id &&
                Objects.equals(kpi_name, that.kpi_name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, kpi_name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKpi_name() {
        return kpi_name;
    }

    public void setKpi_name(String kpi_name) {
        this.kpi_name = kpi_name;
    }
}
