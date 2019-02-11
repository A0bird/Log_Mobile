package com.mobile.paser.MR.PV;

import com.mobile.Util.JDBCUtil;
import com.mobile.Util.TimeUtil;
import com.mobile.common.DateTypeEnum;
import com.mobile.common.GlobalConstans;
import com.mobile.paser.modle.dim.base.DateDimension;
import com.mobile.paser.modle.dim.base.StatsUserDiemension;
import com.mobile.paser.modle.dim.value.map.TimeOutPutWriteable;
import com.mobile.paser.modle.dim.value.reduce.ReduceOutPUtWritable;
import com.mobile.paser.service.DimensionOperateI;
import com.mobile.paser.service.serviceImpl.DimensionOPerateImpl;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: 72428
 * @Date: 2018/12/6 23:03
 * @Description:
 */
public class PVRunner implements Tool{

    private static final Logger logger = Logger.getLogger(PVRunner.class);
    private Configuration conf = new Configuration();

    @Override
    public int run(String[] args) throws Exception {
        conf=this.getConf();
        setArgs(args,conf);

        Job job = Job.getInstance(conf, "new_user");
        job.setJarByClass((PVRunner.class));

        //map端
        job.setMapperClass(PVMapper.class);
        job.setMapOutputKeyClass(StatsUserDiemension.class);
        job.setMapOutputValueClass(TimeOutPutWriteable.class);

        job.setReducerClass(PVReduce.class);
        job.setOutputKeyClass(StatsUserDiemension.class);
        job.setOutputValueClass(ReduceOutPUtWritable.class);

        headInputAndoutPut(job);

        job.setOutputFormatClass(MysqlOutPutFormat.class);
        if(job.waitForCompletion(true)){
            this.computeTotalUser(job);
            return 0;
        }else {
            return 1;
        }

    }

    private void computeTotalUser(Job job) {
        String nowdate=job.getConfiguration().get(GlobalConstans.RUNNING_DATE);
        long nowDate= TimeUtil.string2Long(nowdate);
        long yesterDateTime=nowDate-GlobalConstans.DAY_OF_MINSECONDS;

        DateDimension nowdateDimension=DateDimension.buildDate(nowDate, DateTypeEnum.DAY);
        DateDimension yesterDimension=DateDimension.buildDate(yesterDateTime, DateTypeEnum.DAY);
        //获取时间维度的id
        DimensionOperateI operateI=new DimensionOPerateImpl();
        int nowDateId=operateI.getDimensionIdByDimension(nowdateDimension);
        int yesterdayId=operateI.getDimensionIdByDimension(yesterDimension);

        //查询数据库-》查找对应时间id所要的新增用户
        Map<String,Integer> info = new ConcurrentHashMap<String,Integer>();
        //查询昨天的新增总用户
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn= JDBCUtil.getConn();
            //查询上一天的新增总用户
            if(yesterdayId>0){
                String sql=job.getConfiguration().get(GlobalConstans.PREFIX_FIND_USER+"DayId");
                ps=conn.prepareStatement(sql);
                ps.setInt(1, yesterdayId);
                rs= ps.executeQuery();
                while (rs.next()){
                    info.put(rs.getInt("platform_dimension_id")+"", rs.getInt("new_install_users"));
                }
            }
            if(nowDateId>0) {
                //查询今天的新增总用户
                String  nowsql=job.getConfiguration().get(GlobalConstans.PREFIX_FIND_USER+"DayId");
                ps=conn.prepareStatement(nowsql);
                ps.setInt(1, nowDateId);
                rs= ps.executeQuery();
                while (rs.next()){
                    int platform_dimension_id=rs.getInt("platform_dimension_id");
                    int newUsers=rs.getInt("new_install_users");
                    if(info.containsKey(platform_dimension_id+"")){
                        newUsers+=info.get(platform_dimension_id+"");
                    }
                    info.put(rs.getInt("platform_dimension_id")+"", rs.getInt(newUsers));
                }
            }
            for(Map.Entry<String,Integer> m:info.entrySet()){
                String total_sql=job.getConfiguration().get(GlobalConstans.PREFIX_INSERT+"total_user");
                ps.setInt(1, nowDateId);
                ps.setInt(2, Integer.valueOf(m.getKey()));
                ps.setInt(3, m.getValue());
                ps.setString(4, conf.get(GlobalConstans.RUNNING_DATE));
                ps.setInt(5, m.getValue());
                ps.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.closeJDBC(conn,ps,rs);
        }

    }

    private void headInputAndoutPut(Job job) {
        String data = job.getConfiguration().get(GlobalConstans.RUNNING_DATE);
        System.out.println("data = " + data);
        String[] split = data.split("-");
        String FileName="/ods/" + split[1] + "/" + split[2];
        Path inputpath = new Path(FileName);
        try {
            FileInputFormat.setInputPaths(job,inputpath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setArgs(String[] args, Configuration conf) {
        for (int i=0;i<args.length;i++){
            if(args[i].equals("-d")){
                if(i+1<args.length){
                    conf.set(GlobalConstans.RUNNING_DATE, args[i+1]);
                    break;
                }
            }
        }
        if(conf.get(GlobalConstans.RUNNING_DATE)==null) {
            conf.set(GlobalConstans.RUNNING_DATE, TimeUtil.getyesTonday());
        }
    }

    @Override
    public void setConf(Configuration configuration) {
        this.conf.addResource("insert-mapper.xml");
        this.conf.addResource("insert-writer.xml");
        this.conf=configuration;
    }

    @Override
    public Configuration getConf() {
        return this.conf;
    }

    public static void main(String[] args) {
        try {
            ToolRunner.run(new PVRunner(), args);
        } catch (Exception e) {
            logger.warn("运行异常",e);
        }
    }
}
