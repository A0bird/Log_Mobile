package com.mobile.etl.toHbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

/**
 * @Auther: 72428
 * @Date: 2018/12/5 08:51
 * @Description:
 */
public class HbaseUtil {

    public static Configuration conf=null;

    static {
        conf= HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "192.168.21.200");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
    }

    public static boolean isExit(String tableName){
        try {
            Connection conn = ConnectionFactory.createConnection();
            Admin admin = conn.getAdmin();
            return  admin.tableExists(TableName.valueOf(tableName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static  void createTable(String tableName,String[] Cloums){

        Admin admin = null;
        try {
            Connection conn = ConnectionFactory.createConnection();
            admin = conn.getAdmin();
            if(isExit(tableName)){
                System.out.println("tableName = " + tableName+"表已经存在");
            }else {
                HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf(tableName));
                for(String family : Cloums){
                    tableDesc.addFamily(new HColumnDescriptor(family));
                }
                admin.createTable(tableDesc);
                System.out.println("Table created");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

    }
}
