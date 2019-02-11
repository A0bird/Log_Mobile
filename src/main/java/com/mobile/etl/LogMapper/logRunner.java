package com.mobile.etl.LogMapper;


import com.mobile.Util.Fsutil;
import com.mobile.Util.TimeUtil;
import com.mobile.common.GlobalConstans;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * @Auther: 72428
 * @Date: 2018/12/3 23:21  com.mobile.etl.LogMapper.logRunner
 * @Description:
 */
public class logRunner implements Tool {

    private static final Logger logger=Logger.getLogger(logRunner.class);

    private Configuration conf = null;

    public static void main(String[] args) {
        try {
            ToolRunner.run(new logRunner(), args);
        } catch (Exception e) {
            logger.warn("运行异常",e);
        }
    }

    @Override
    public int run(String[] args) throws Exception {
        conf=this.getConf();

        setArgs(args,conf);

        Job job = Job.getInstance(conf, "etl");

        job.setJarByClass(logRunner.class);

        job.setMapperClass(logMap.class);
        job.setMapOutputKeyClass(LogWritable.class);
        job.setOutputValueClass(NullWritable.class);

        headInputAndoutPut(job);

        job.setNumReduceTasks(0);

        int a=job.waitForCompletion(true)?1:0;


        return a;
    }



    private void setArgs(String[] args,Configuration conf) {
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
        this.conf=configuration;
    }

    @Override
    public Configuration getConf() {
        return this.conf;
    }

    private void headInputAndoutPut(Job job) throws IOException {
        String data = job.getConfiguration().get(GlobalConstans.RUNNING_DATE);
        String[] split = data.split("-");
        Path inpath = new Path("/logs/" + split[1] + "/" + split[2]);
        Path outpath = new Path("/ods/" + split[1] + "/" + split[2]);
        FileInputFormat.setInputPaths(job,inpath);
        FileSystem fs= Fsutil.getFs();
        if(fs.exists(outpath)){
            fs.delete(outpath, true);
        }
        FileOutputFormat.setOutputPath(job,outpath );
    }
}
