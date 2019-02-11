package com.mobile.paser.modle.dim.base;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

/**
 * @Auther: 72428
 * @Date: 2018/12/5 10:16
 * @Description:
 */
public class PlatfromDimension extends BaseDimeension {

     private  int id;
     private String platform_name;

    public PlatfromDimension() {
    }

    public PlatfromDimension(String platform_name) {
        this.platform_name = platform_name;
    }

    public PlatfromDimension(int id, String platform_name) {
        this.id = id;
        this.platform_name = platform_name;
    }


    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.id);
        dataOutput.writeUTF(this.platform_name);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id = dataInput.readInt();
        this.platform_name=dataInput.readUTF();
    }

    @Override
    public int compareTo(BaseDimeension o) {
        if(this == o ){
            return  0;
        }
        PlatfromDimension other= (PlatfromDimension) o;
        int temp = this.id-other.id;
        if(temp!=0){
            return  temp;
        }
        temp = this.platform_name.compareTo(other.platform_name);
        return temp;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlatfromDimension)) return false;
        PlatfromDimension that = (PlatfromDimension) o;
        return getId() == that.getId() &&
                Objects.equals(getPlatform_name(), that.getPlatform_name());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getPlatform_name());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlatform_name() {
        return platform_name;
    }

    public void setPlatform_name(String platform_name) {
        this.platform_name = platform_name;
    }
}
