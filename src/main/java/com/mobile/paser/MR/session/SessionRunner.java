package com.mobile.paser.MR.session;

import com.mobile.Util.TimeUtil;
import com.mobile.common.GlobalConstans;
import com.mobile.paser.modle.dim.base.StatsUserDiemension;
import com.mobile.paser.modle.dim.value.map.TimeOutPutWriteable;
import com.mobile.paser.modle.dim.value.reduce.ReduceOutPUtWritable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * @Auther: 72428
 * @Date: 2018/12/6 23:03
 * @Description:
 */
public class SessionRunner implements Tool{

    private static final Logger logger = Logger.getLogger(SessionRunner.class);
    private Configuration conf = new Configuration();

    @Override
    public int run(String[] args) throws Exception {
        conf=this.getConf();
        setArgs(args,conf);

        Job job = Job.getInstance(conf, "new_user");
        job.setJarByClass((SessionRunner.class));

        //map端
        job.setMapperClass(SessionMapper.class);
        job.setMapOutputKeyClass(StatsUserDiemension.class);
        job.setMapOutputValueClass(TimeOutPutWriteable.class);

        job.setReducerClass(SessionReduce.class);
        job.setOutputKeyClass(StatsUserDiemension.class);
        job.setOutputValueClass(ReduceOutPUtWritable.class);

        headInputAndoutPut(job);

        job.setOutputFormatClass(MysqlOutPutFormat.class);

        return  job.waitForCompletion(true)?1:0;

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
            ToolRunner.run(new SessionRunner(), args);
        } catch (Exception e) {
            logger.warn("运行异常",e);
        }
    }
}
