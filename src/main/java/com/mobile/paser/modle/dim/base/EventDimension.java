package com.mobile.paser.modle.dim.base;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @Auther: 72428
 * @Date: 2018/12/10 09:39
 * @Description:
 */
public class EventDimension extends  BaseDimeension{
    private int id;
    private String category;
    private String action;


    public EventDimension(int id, String category, String action) {
        this.id = id;
        this.category = category;
        this.action = action;
    }

    public EventDimension(String category, String action) {
        this.category = category;
        this.action = action;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.id);
        dataOutput.writeUTF(this.category);
        dataOutput.writeUTF(this.action);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id=dataInput.readInt();
        this.action=dataInput.readUTF();
        this.category=dataInput.readUTF();
    }
    @Override
    public int compareTo(BaseDimeension o) {
        if(this == o ){
            return 0;
        }
        EventDimension other=(EventDimension) o;
        int temp=this.id-((EventDimension) o).id;
        if(temp!=0){
            return  temp;
        }
        temp = this.action.compareTo(other.action);
        if(temp!=0){
            return temp;
        }
        return temp = this.category.compareTo(other.category);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
