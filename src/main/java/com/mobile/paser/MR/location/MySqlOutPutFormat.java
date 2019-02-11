package com.mobile.paser.MR.location;

import com.mobile.Util.JDBCUtil;

import com.mobile.paser.modle.dim.base.StatsLocationDimension;
import com.mobile.paser.modle.dim.value.reduce.ReduceOutPUtWritable;
import com.mobile.paser.service.DimensionOperateI;
import com.mobile.paser.service.serviceImpl.DimensionOPerateImpl;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.sql.Connection;

/**
 * @Auther: 72428
 * @Date: 2018/12/12 14:29
 * @Description:
 */
public class MySqlOutPutFormat extends OutputFormat<StatsLocationDimension,ReduceOutPUtWritable> {
    @Override
    public RecordWriter<StatsLocationDimension, ReduceOutPUtWritable> getRecordWriter(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {

        Connection conn= JDBCUtil.getConn();
        Configuration conf= taskAttemptContext.getConfiguration();
        DimensionOperateI convert = new DimensionOPerateImpl();
        return new MyRecordWriter(conn,conf,convert);
    }

    @Override
    public void checkOutputSpecs(JobContext jobContext) throws IOException, InterruptedException {

    }

    @Override
    public OutputCommitter getOutputCommitter(TaskAttemptContext context) throws IOException, InterruptedException {
        return new FileOutputCommitter(FileOutputFormat.getOutputPath(context), context);
    }
}
