package com.mobile.paser.modle.dim.base;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

/**
 * @Auther: 72428
 * @Date: 2018/12/5 10:06
 * @Description:
 */
public class BrowserDiemenson extends BaseDimeension {

    private int id;
    private String browser_name;
    private String browser_version;

    public BrowserDiemenson(String browser_name, String browser_version) {
        this.browser_name = browser_name;
        this.browser_version = browser_version;
    }

    public BrowserDiemenson(int id, String browser_name, String browser_version) {
        this(browser_name,browser_version);
        this.id = id;
    }

    public BrowserDiemenson() {

    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.id);
        dataOutput.writeUTF(this.browser_name);
        dataOutput.writeUTF(this.browser_version);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id=dataInput.readInt();
        this.browser_name=dataInput.readUTF();
        this.browser_version=dataInput.readUTF();
    }

    @Override
    public int compareTo(BaseDimeension o) {
        if(this == o){
            return  0;
        }
        BrowserDiemenson other= (BrowserDiemenson) o;
        int temp=this.id-other.id;
        if(temp !=0 ){
            return temp;
        }
       temp=this.browser_name.compareTo(other.browser_name);
        if(temp != 0){
            return temp;
        }
        return  this.browser_version.compareTo(other.browser_version);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BrowserDiemenson)) return false;
        BrowserDiemenson that = (BrowserDiemenson) o;
        return id == that.id &&
                Objects.equals(browser_name, that.browser_name) &&
                Objects.equals(browser_version, that.browser_version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, browser_name, browser_version);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrowser_name() {
        return browser_name;
    }

    public void setBrowser_name(String browser_name) {
        this.browser_name = browser_name;
    }

    public String getBrowser_version() {
        return browser_version;
    }

    public void setBrowser_version(String browser_version) {
        this.browser_version = browser_version;
    }
}
