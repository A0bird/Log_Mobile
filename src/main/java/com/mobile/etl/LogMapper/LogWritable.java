package com.mobile.etl.LogMapper;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @Auther: 72428
 * @Date: 2018/12/4 14:33
 * @Description: 封装存储到hdfs中
 */
public class LogWritable  implements Writable{

    public String en     ;
    public String ver    ;
    public String pl     ;
    public String sdk    ;
    public String b_rst  ;
    public String b_iev  ;
    public String u_ud   ;
    public String l      ;
    public String u_mid  ;
    public String u_sd   ;
    public String c_time ;
    public String p_url  ;
    public String p_ref  ;
    public String tt     ;
    public String ca     ;
    public String ac     ;
    public String kv     ;
    public String du     ;
    public String oid    ;
    public String on     ;
    public String cua    ;
    public String cut    ;
    public String pt     ;
    public String ip     ;
    public String s_time ;
    public String country;
    public String province;
    public String city;
    public String browname;
    public String browvresion;
    public String os_name;
    public String os_version;


    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF( en     );
        dataOutput.writeUTF( ver    );
        dataOutput.writeUTF( pl     );
        dataOutput.writeUTF( sdk    );
        dataOutput.writeUTF( b_rst  );
        dataOutput.writeUTF( b_iev  );
        dataOutput.writeUTF( u_ud   );
        dataOutput.writeUTF( l      );
        dataOutput.writeUTF( u_mid  );
        dataOutput.writeUTF( u_sd   );
        dataOutput.writeUTF( c_time );
        dataOutput.writeUTF( p_url  );
        dataOutput.writeUTF( p_ref  );
        dataOutput.writeUTF( tt     );
        dataOutput.writeUTF( ca     );
        dataOutput.writeUTF( ac     );
        dataOutput.writeUTF( kv     );
        dataOutput.writeUTF( du     );
        dataOutput.writeUTF( oid    );
        dataOutput.writeUTF( on     );
        dataOutput.writeUTF( cua    );
        dataOutput.writeUTF( cut    );
        dataOutput.writeUTF( pt     );
        dataOutput.writeUTF( ip     );
        dataOutput.writeUTF( s_time );
        dataOutput.writeUTF( country);
        dataOutput.writeUTF( province);
        dataOutput.writeUTF( city);
        dataOutput.writeUTF( browname);
        dataOutput.writeUTF( browvresion);
        dataOutput.writeUTF( os_name);
        dataOutput.writeUTF( os_version);

    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.en =dataInput.readUTF();
        this.ver =dataInput.readUTF();
        this.pl =dataInput.readUTF();
        this.sdk =dataInput.readUTF();
        this.b_rst =dataInput.readUTF();
        this.b_iev =dataInput.readUTF();
        this.u_ud =dataInput.readUTF();
        this.l =dataInput.readUTF();
        this.u_mid =dataInput.readUTF();
        this.u_sd =dataInput.readUTF();
        this.c_time =dataInput.readUTF();
        this.p_url =dataInput.readUTF();
        this.p_ref =dataInput.readUTF();
        this.tt =dataInput.readUTF();
        this.ca =dataInput.readUTF();
        this.ac =dataInput.readUTF();
        this.kv =dataInput.readUTF();
        this.du =dataInput.readUTF();
        this.oid =dataInput.readUTF();
        this.on =dataInput.readUTF();
        this.cua =dataInput.readUTF();
        this.cut =dataInput.readUTF();
        this.pt =dataInput.readUTF();
        this.ip =dataInput.readUTF();
        this.s_time =dataInput.readUTF();
        this.country=dataInput.readUTF();
        this.province=dataInput.readUTF();
        this.city=dataInput.readUTF();
        this.browname=dataInput.readUTF();
        this.browvresion=dataInput.readUTF();
        this.os_name=dataInput.readUTF();
        this.os_version=dataInput.readUTF();
    }

    @Override
    public String toString() {
        return en+"\001"+ver+"\001"+pl+"\001"+sdk+"\001"+b_rst+"\001"+b_iev+"\001"+u_ud+"\001"+l+"\001"+u_mid
                +"\001"+u_sd+"\001"+c_time+"\001"+p_url+"\001"+p_ref+"\001"+tt+"\001"+ca+"\001"+ac+"\001"+kv
                +"\001"+du +"\001"+oid+"\001"+on+"\001"+cua+"\001"+cut+"\001"+pt+"\001"+ip+"\001"+s_time
                +"\001"+country+"\001"+province +"\001"+city+"\001"+browname+"\001"+browvresion+"\001"+os_name+
                "\001"+os_version;

    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getPl() {
        return pl;
    }

    public void setPl(String pl) {
        this.pl = pl;
    }

    public String getSdk() {
        return sdk;
    }

    public void setSdk(String sdk) {
        this.sdk = sdk;
    }

    public String getB_rst() {
        return b_rst;
    }

    public void setB_rst(String b_rst) {
        this.b_rst = b_rst;
    }

    public String getB_iev() {
        return b_iev;
    }

    public void setB_iev(String b_iev) {
        this.b_iev = b_iev;
    }

    public String getU_ud() {
        return u_ud;
    }

    public void setU_ud(String u_ud) {
        this.u_ud = u_ud;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getU_mid() {
        return u_mid;
    }

    public void setU_mid(String u_mid) {
        this.u_mid = u_mid;
    }

    public String getU_sd() {
        return u_sd;
    }

    public void setU_sd(String u_sd) {
        this.u_sd = u_sd;
    }

    public String getC_time() {
        return c_time;
    }

    public void setC_time(String c_time) {
        this.c_time = c_time;
    }

    public String getP_url() {
        return p_url;
    }

    public void setP_url(String p_url) {
        this.p_url = p_url;
    }

    public String getP_ref() {
        return p_ref;
    }

    public void setP_ref(String p_ref) {
        this.p_ref = p_ref;
    }

    public String getTt() {
        return tt;
    }

    public void setTt(String tt) {
        this.tt = tt;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getAc() {
        return ac;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }

    public String getKv() {
        return kv;
    }

    public void setKv(String kv) {
        this.kv = kv;
    }

    public String getDu() {
        return du;
    }

    public void setDu(String du) {
        this.du = du;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOn() {
        return on;
    }

    public void setOn(String on) {
        this.on = on;
    }

    public String getCua() {
        return cua;
    }

    public void setCua(String cua) {
        this.cua = cua;
    }

    public String getCut() {
        return cut;
    }

    public void setCut(String cut) {
        this.cut = cut;
    }

    public String getPt() {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getS_time() {
        return s_time;
    }

    public void setS_time(String s_time) {
        this.s_time = s_time;
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

    public String getBrowname() {
        return browname;
    }

    public void setBrowname(String browname) {
        this.browname = browname;
    }

    public String getBrowvresion() {
        return browvresion;
    }

    public void setBrowvresion(String browvresion) {
        this.browvresion = browvresion;
    }

    public String getOs_name() {
        return os_name;
    }

    public void setOs_name(String os_name) {
        this.os_name = os_name;
    }

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }
}
