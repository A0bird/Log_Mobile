package com.mobile.Util;

import java.sql.*;

/**
 * @Auther: 72428
 * @Date: 2018/12/5 23:17
 * @Description:
 */
public class JDBCUtil {

    static {
        try {
            // 1.加载驱动
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //获取jdbc连接
    public static Connection getConn(){
        PropertiesUtil propertiesUtil=  new PropertiesUtil();
        propertiesUtil.setFileName("jdbc.properties");
        Connection conn=null;
        String url = propertiesUtil.readPropertyByKey("url");
        String user=propertiesUtil.readPropertyByKey("username");
        String password=propertiesUtil.readPropertyByKey("password");
        try {
            conn= DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeJDBC(Connection conn, PreparedStatement ps, ResultSet rs){

        try {
            if(rs!=null){
                rs.close();
            }
            if(ps!=null){
                ps.close();
            }
            if(conn!=null){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        System.out.println(JDBCUtil.getConn());
    }

}
