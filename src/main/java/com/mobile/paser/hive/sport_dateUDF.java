package com.mobile.paser.hive;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @Auther: 72428
 * @Date: 2018/12/17 19:22
 * @Description:
 */
public class sport_dateUDF extends UDF{

    public String evaluate(int start,int end){
        StringBuffer time = new StringBuffer();
        if(end<start){
            return "error";
        }
        if(start<=end){
            time.append(start+",");
            for(; start<end-1; start++){
                time.append(start+1).append(",");
            }
            time.append(end);
        }
        return time.toString();
    }

}
