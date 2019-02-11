package com.mobile.paser.modle.dim.base;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

/**
 * @Auther: 72428
 * @Date: 2018/12/13 14:40
 * @Description:
 */
public class payTypeDimension extends BaseDimeension {

    private int id;
    private String payment_type;

    public payTypeDimension() {
    }

    public payTypeDimension(String payment_type) {
        this.payment_type = payment_type;
    }

    public payTypeDimension(int id, String payment_type) {
        this.id = id;
        this.payment_type = payment_type;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.id);
        dataOutput.writeUTF(this.payment_type);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id=dataInput.readInt();
        this.payment_type=dataInput.readUTF();
    }
    @Override
    public int compareTo(BaseDimeension o) {
        if(this == o){
            return 0;
        }
        payTypeDimension other=(payTypeDimension) o;
        int temp =this.id-other.id;
        if(temp!=0){
            return temp;
        }
        return temp=this.payment_type.compareTo(other.payment_type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof payTypeDimension)) return false;
        payTypeDimension that = (payTypeDimension) o;
        return id == that.id &&
                Objects.equals(payment_type, that.payment_type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, payment_type);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }
}
