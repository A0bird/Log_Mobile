package com.mobile.examine;


import com.mobile.etl.util.Fsutil;
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

/**
 * @Auther: 72428
 * @Date: 2018/12/21 21:53
 * @Description:
 */
public class answerRunner implements Tool{
    private static final Logger logger=Logger.getLogger(answerRunner.class);
    private Configuration conf=null;

    public int run(String[] args) throws Exception {
        conf=this.getConf();

        Job job = Job.getInstance(conf, "answer_paper");

        job.setJarByClass(answerRunner.class);

        job.setMapperClass(answerMapper.class);
        job.setMapOutputKeyClass(objective_answer.class);
        job.setMapOutputValueClass(NullWritable.class);

        job.setNumReduceTasks(0);

        Path inpath = new Path("/proTest/data/answer_paper/");
        Path outpath = new Path("/proTest/ods_clean/answer_paper");

        FileInputFormat.setInputPaths(job,inpath);
        FileSystem fs= Fsutil.getFs();
        if(fs.exists(outpath)){
            fs.delete(outpath, true);
        }
        FileOutputFormat.setOutputPath(job,outpath );
        return job.waitForCompletion(true)?1:0;
    }

    public void setConf(Configuration conf) {
        this.conf=conf;
    }

    public Configuration getConf() {
        return this.conf;
    }

    public static void main(String[] args) {
        try {
            ToolRunner.run(new answerRunner(),args );
        } catch (Exception e) {
            logger.warn("运行异常",e);
        }
    }
}
