package com.mobile.paser.modle.dim.base;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

/**
 * @Auther: 72428
 * @Date: 2018/12/11 22:10
 * @Description:
 */
public class loactionDimension extends BaseDimeension {

    private  int id;
    private  String country;
    private  String province;
    private  String city;


    public loactionDimension() {
    }

    public loactionDimension(int id, String country, String province, String city) {
        this.id = id;
        this.country = country;
        this.province = province;
        this.city = city;
    }

    public loactionDimension(String country, String province, String city) {
        this.country = country;
        this.province = province;
        this.city = city;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
            dataOutput.writeInt(this.id);
            dataOutput.writeUTF(this.country);
            dataOutput.writeUTF(this.province);
            dataOutput.writeUTF(city);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
            this.id=dataInput.readInt();
            this.country=dataInput.readUTF();
            this.province=dataInput.readUTF();
            this.city=dataInput.readUTF();
    }

    @Override
    public int compareTo(BaseDimeension o) {
        if(this == o){
            return 0;
        }
        loactionDimension other=(loactionDimension) o;
        int temp = this.id-other.id;
        if(temp!=0){
            return temp;
        }
        temp=this.country.compareTo(other.country);
        if (temp != 0) {
         return temp;
        }
        temp = this.province.compareTo(other.province);
        if(temp !=0){
            return temp;
        }
        return temp = this.city.compareTo(other.city);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof loactionDimension)) return false;
        loactionDimension that = (loactionDimension) o;
        return id == that.id &&
                Objects.equals(country, that.country) &&
                Objects.equals(province, that.province) &&
                Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, country, province, city);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
