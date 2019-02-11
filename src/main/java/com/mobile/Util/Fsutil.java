package com.mobile.Util;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * @Auther: 72428
 * @Date: 2018/12/4 22:38
 * @Description:
 */
public class Fsutil {
    public static final Logger logger=Logger.getLogger(Fsutil.class);
    public  static FileSystem getFs(){
        Configuration conf = new Configuration();
        FileSystem fs=null;
        try {
            fs=FileSystem.get(conf);
        } catch (IOException e) {
            logger.error("fs对象获取异常");
        }
        return fs;
    }

    public  static  void closeFs(FileSystem fs){
        if(fs!=null){
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
