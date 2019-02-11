package com.mobile.Util;

import com.mobile.common.GlobalConstans;
import com.mobile.paser.service.serviceImpl.DimensionOPerateImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Auther: 72428
 * @Date: 2018/12/7 21:51
 * @Description:
 */
public class MemberUtil {
    private  static Logger logger=Logger.getLogger(DimensionOPerateImpl.class);
    //定义一个内存级别的缓存  对象属性的拼接key->维度信息 value->维度所对应的id
    private static Map<String,Boolean> cache= new LinkedHashMap<String,Boolean>(){
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, Boolean> eldest) {
            return this.size()>1000;
        }
    };


    public static boolean checkMeberId(String memberId, Connection conn, Configuration conf) {

        PreparedStatement ps=null;
        ResultSet rs=null;
        Boolean res=null;

        if(StringUtils.isNotEmpty(memberId)){
            res = cache.get(memberId);
            try {
                if(res==null){
                    //在数据库中查询是否有memberId存在
                    String findsql=conf.get(GlobalConstans.PREFIX_SELECT+"member_info");
                    conn=JDBCUtil.getConn();
                    ps=conn.prepareStatement(findsql);
                    ps.setString(1, memberId);
                    rs=ps.executeQuery();
                    System.out.println("rs = " + rs);
                    if(rs==null){
                        return true;
                    }
                    //如果数据库中不存在在插入到数据库中
                    String sql=conf.get(GlobalConstans.PREFIX_INSERT+"member_info");
                    conn=JDBCUtil.getConn();
                    ps=conn.prepareStatement(sql);
                    ps.setString(1, memberId);
                    ps.setDate(2, Date.valueOf(conf.get(GlobalConstans.RUNNING_DATE)));
                    ps.setDate(3,Date.valueOf(conf.get(GlobalConstans.RUNNING_DATE)));
                    ps.setString(4, memberId);
                    res=ps.execute();
                    System.out.println("res = " + res);
                    cache.put(memberId, res);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                JDBCUtil.closeJDBC(conn, ps, rs);
            }

        }

        return res;
    }
}
