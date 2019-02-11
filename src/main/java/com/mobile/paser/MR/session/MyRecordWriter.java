package com.mobile.paser.MR.session;

import com.mobile.Util.JDBCUtil;
import com.mobile.Util.PropertiesUtil;
import com.mobile.common.GlobalConstans;
import com.mobile.common.kpiTypeEnum;
import com.mobile.paser.MR.ReduceOutPutFormatI;
import com.mobile.paser.modle.dim.base.StatsUserDiemension;
import com.mobile.paser.modle.dim.value.reduce.ReduceOutPUtWritable;
import com.mobile.paser.service.DimensionOperateI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 72428
 * @Date: 2018/12/6 20:12
 * @Description:  封装writter的信息
 */
public class MyRecordWriter extends RecordWriter<StatsUserDiemension, ReduceOutPUtWritable> {
    private static  final Logger logger=Logger.getLogger(MyRecordWriter.class);
    private Connection conn = null;
    private Configuration conf = null;//用于查找sql语句
    private DimensionOperateI covert=null;//获取对应维度的id
    //初始化 propertiesUtil 类
    private PropertiesUtil propertiesUtil = new PropertiesUtil();
    //key 为kpiName ，value为sql语句 最为sql语句的缓存
    private Map<kpiTypeEnum,PreparedStatement>  newUserMap=new HashMap<>();
    //new_user的计数存放，不用重头计算
    //kpiName-ps对应的个数
    private Map<kpiTypeEnum,Integer> kpiNumberMap = new HashMap<>();

    public MyRecordWriter(Connection conn, Configuration conf, DimensionOperateI covert) {
        this.conn = conn;
        this.conf = conf;
        this.covert = covert;
    }

    /**
     * 功能描述:
     *  1. 获取kpi，从value或者key种，统一value为准
     *  2. 用kpi判断缓存中是否存在ps，有则直接使用，没有从conf中获取
     *  3.为ps赋值
     *  4.将赋值好的ps添加到批处理中
     *  5.批处理执行ps
     *  6.执行完成，关闭资源，处理缓存中的ps
     * @param:
     * @return:
     * @auther: 72428
     * @date: 2018/12/7 10:27
     */

    @Override
    public void write(StatsUserDiemension key, ReduceOutPUtWritable value) throws IOException, InterruptedException {
            //判断key ，value 是否为空
            if( key == null && value == null){
                logger.warn("key-value 值为 null");
                return;
            }
            PreparedStatement ps=null;
            //获取kpi
            kpiTypeEnum kpiName=value.getKpi();
            //判断缓存中是否存在
        int count=1;
        try {
            if(this.newUserMap.containsKey(kpiName)){
                ps = this.newUserMap.get(kpiName);
                count = this.kpiNumberMap.get(kpiName);
                count++;
            }else {
                String sql=this.conf.get(kpiName.kpiName);
//                String sql=propertiesUtil.readPropertyByKey("new_userInsert");
                ps = this.conn.prepareStatement(sql);
                //添加到缓存
                this.newUserMap.put(kpiName, ps);
            }
            //将新的kpiname放入到缓存中
            this.kpiNumberMap.put(kpiName, count);
            //为ps赋值，通过在newUserMap中查找对应的kpiname
            String className=this.conf.get(GlobalConstans.PREFIX+kpiName.kpiName);
            //通过反射找到配置文件中对应的sql语句
            Class<?> aClass = Class.forName(className);
            //将类还原成为对象
            //得到赋值的sql的对象
            ReduceOutPutFormatI reduceOutPutFormatI=(ReduceOutPutFormatI) aClass.newInstance();
            //将传入的值进行赋值
            reduceOutPutFormatI.outputWritter(conf, key, value, ps, covert);
            //批量执行
            if( this.kpiNumberMap.get(kpiName) % 50 ==0){
                this.newUserMap.get(kpiName).executeBatch();
                this.conn.commit();
                this.newUserMap.remove(kpiName);
            }
        } catch (Exception e) {
            logger.error("sql执行异常",e);
        }
    }

    /**
     * 功能描述:
     *    关闭资源
     * @param:
     * @return:
     * @auther: 72428
     * @date: 2018/12/7 11:28
     */
    @Override
    public void close(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        //关闭资源
        try {
            for(Map.Entry<kpiTypeEnum,PreparedStatement> en: this.newUserMap.entrySet()){
                en.getValue().execute();//将剩余的ps执行完
                //执行失败的话怎么处理
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(conn !=null){
                JDBCUtil.closeJDBC(conn, null, null);
            }
            //关闭ps
            for(Map.Entry<kpiTypeEnum,PreparedStatement> en: this.newUserMap.entrySet()){
                try {
                    en.getValue().close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
