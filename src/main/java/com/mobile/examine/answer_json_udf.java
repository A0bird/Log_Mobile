package com.mobile.examine;


import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Set;

/**
 * @Auther: 72428
 * @Date: 2018/12/21 19:23
 * @Description:
 */
public class answer_json_udf {

    public objective_answer evaluate(String answer_json)  {
        HashMap<String, HashMap<String, String>> answer = new HashMap<>();
        if(StringUtils.isEmpty(answer_json)){
                return null;
            }
            objective_answer answer_paper = new objective_answer();
            JSONObject fromObject = JSONObject.fromObject(answer_json);

            Set<String> set = fromObject.keySet();

            for (String jsonKey : set) {
                if (jsonKey.isEmpty()){
                    return null;
                }else {
/*                    //将json 的value值转化为string
                    String jsonValue=fromObject.get(jsonKey).toString();
                    JSONObject jsonObjectValue=JSONObject.fromObject(jsonValue);
                    String useranswer = jsonObjectValue.get("useranswer").toString();
                    String score = jsonObjectValue.get("score").toString();
                    objective_answer.setAnswer_id(jsonKey);
                    objective_answer.setAnswer_quesion(useranswer);
                    objective_answer.setAnswer_score(score);*/
                }

        }

        return  answer_paper;
    }

}
