package com.mobile.paser.MR.AM;

import com.mobile.Util.JDBCUtil;
import com.mobile.paser.modle.dim.base.StatsUserDiemension;
import com.mobile.paser.modle.dim.value.reduce.ReduceOutPUtWritable;
import com.mobile.paser.service.DimensionOperateI;
import com.mobile.paser.service.serviceImpl.DimensionOPerateImpl;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;

/**
 * @Auther: 72428
 * @Date: 2018/12/6 19:47
 * @Description:
 */
public class MysqlOutPutFormat extends OutputFormat<StatsUserDiemension,ReduceOutPUtWritable>{
    private static final Logger logger=Logger.getLogger(MysqlOutPutFormat.class);

    @Override
    public RecordWriter<StatsUserDiemension, ReduceOutPUtWritable> getRecordWriter(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        //
        Connection conn= JDBCUtil.getConn();
        Configuration conf= taskAttemptContext.getConfiguration();
        DimensionOperateI convert = new DimensionOPerateImpl();
        return new MyRecordWriter(conn,conf,convert);
    }

    @Override
    public void checkOutputSpecs(JobContext jobContext) throws IOException, InterruptedException {
            //检测输出空间的内容
    }

    @Override
    public OutputCommitter getOutputCommitter(TaskAttemptContext context) throws IOException, InterruptedException {
        //提交
        return new FileOutputCommitter(FileOutputFormat.getOutputPath(context), context);
//        return new FileOutputCommitter(null, context);
    }
}
