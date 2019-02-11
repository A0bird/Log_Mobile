package com.mobile.paser.modle.dim.base;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

/**
 * @Auther: 72428
 * @Date: 2018/12/13 14:41
 * @Description:
 */
public class currencyTypeDimension extends BaseDimeension {

    private int id;
    private String currency_name;

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.id);
        dataOutput.writeUTF(this.currency_name);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id=dataInput.readInt();
        this.currency_name=dataInput.readUTF();
    }

    @Override
    public int compareTo(BaseDimeension o) {
        if(this ==o){
            return 0;
        }
        currencyTypeDimension other=(currencyTypeDimension) o;
        int temp = this.id-other.id;
        if(temp!=0){
            return temp;
        }
        return temp=this.currency_name.compareTo(other.currency_name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof currencyTypeDimension)) return false;
        currencyTypeDimension that = (currencyTypeDimension) o;
        return id == that.id &&
                Objects.equals(currency_name, that.currency_name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, currency_name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrency_name() {
        return currency_name;
    }

    public void setCurrency_name(String currency_name) {
        this.currency_name = currency_name;
    }
}
