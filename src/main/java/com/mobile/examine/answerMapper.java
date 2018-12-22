package com.mobile.examine;

import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Random;
import java.util.Set;

/**
 * @Auther: 72428
 * @Date: 2018/12/21 21:30
 * @Description:
 */
public class answerMapper extends Mapper<LongWritable,Text,objective_answer,NullWritable> {

    objective_answer answer_paper = new objective_answer();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();
        String[] fields = line.split("\001");
        Random random = new Random();
        int i = random.nextInt(99999);
        answer_paper.setAnswer_paper_id(fields[0]);
        answer_paper.setExam_id(fields[1]);
        answer_paper.setPaper_id(fields[2]);
        answer_paper.setExaminee_id(fields[3]);
        String md5Str = DigestUtils.md5Hex(fields[4]+i );
        answer_paper.setExaminee_name(md5Str);
        answer_paper.setExaminee_num(fields[5]);
        answer_paper.setClass_id(fields[6]);
        answer_paper.setClass_name(fields[7]);
        answer_paper.setStart_date(fields[8]);
        answer_paper.setObjective_mark(fields[11]);
        String answer_json = fields[16];
        if (StringUtils.isEmpty(answer_json)||answer_json.equals("")) {
            return;
        }
        JSONObject fromObject = JSONObject.fromObject(answer_json);

        Set<String> set = fromObject.keySet();

        for (String jsonKey : set) {
            if (StringUtils.isEmpty(jsonKey)) {
                return;
            } else {
                //将json 的value值转化为string
                String jsonValue = fromObject.get(jsonKey).toString();
                JSONObject jsonObjectValue = JSONObject.fromObject(jsonValue);
                String useranswer = jsonObjectValue.get("useranswer").toString();
                String score = jsonObjectValue.get("score").toString();
                answer_paper.setAnswer_id(jsonKey);
                answer_paper.setAnswer_quesion(useranswer);
                answer_paper.setAnswer_score(score);
            }
            context.write(answer_paper, NullWritable.get());
        }
    }
}
