package com.mobile.QFLAP;

/**
 * @Auther: 72428
 * @Date: 2018/12/21 19:32
 * @Description:
 */
public class objective_answer {

    private  String  answer_paper_id;
    private  String  exam_id;
    private  String  paper_id;
    private  String  examinee_id;
    private  String  examinee_name;
    private  String  examinee_num;
    private  String  class_id ;
    private  String  class_name ;
    private  String  start_date ;
    private  String  objective_mark;
    private  String  answer_id;
    private  String  answer_quesion;
    private  String  answer_score;

    public objective_answer() {
    }

    public objective_answer(String answer_paper_id, String exam_id, String paper_id, String examinee_id, String examinee_name, String examinee_num, String class_id, String class_name, String start_date, String objective_mark, String answer_id, String answer_quesion, String answer_score) {
        this.answer_paper_id = answer_paper_id;
        this.exam_id = exam_id;
        this.paper_id = paper_id;
        this.examinee_id = examinee_id;
        this.examinee_name = examinee_name;
        this.examinee_num = examinee_num;
        this.class_id = class_id;
        this.class_name = class_name;
        this.start_date = start_date;
        this.objective_mark = objective_mark;
        this.answer_id = answer_id;
        this.answer_quesion = answer_quesion;
        this.answer_score = answer_score;
    }

    public String getAnswer_paper_id() {
        return answer_paper_id;
    }

    public void setAnswer_paper_id(String answer_paper_id) {
        this.answer_paper_id = answer_paper_id;
    }

    public String getExam_id() {
        return exam_id;
    }

    public void setExam_id(String exam_id) {
        this.exam_id = exam_id;
    }

    public String getPaper_id() {
        return paper_id;
    }

    public void setPaper_id(String paper_id) {
        this.paper_id = paper_id;
    }

    public String getExaminee_id() {
        return examinee_id;
    }

    public void setExaminee_id(String examinee_id) {
        this.examinee_id = examinee_id;
    }

    public String getExaminee_name() {
        return examinee_name;
    }

    public void setExaminee_name(String examinee_name) {
        this.examinee_name = examinee_name;
    }

    public String getExaminee_num() {
        return examinee_num;
    }

    public void setExaminee_num(String examinee_num) {
        this.examinee_num = examinee_num;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getObjective_mark() {
        return objective_mark;
    }

    public void setObjective_mark(String objective_mark) {
        this.objective_mark = objective_mark;
    }

    public String getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(String answer_id) {
        this.answer_id = answer_id;
    }

    public String getAnswer_quesion() {
        return answer_quesion;
    }

    public void setAnswer_quesion(String answer_quesion) {
        this.answer_quesion = answer_quesion;
    }

    public String getAnswer_score() {
        return answer_score;
    }

    public void setAnswer_score(String answer_score) {
        this.answer_score = answer_score;
    }

    @Override
    public String toString() {
        return   answer_paper_id + '\001' +
                 exam_id + '\001' +
                 paper_id + '\001' +
                 examinee_id + '\001' +
                 examinee_name + '\001' +
                 examinee_num + '\001' +
                 class_id + '\001' +
                 class_name + '\001' +
                 start_date + '\001' +
                 objective_mark + '\001' +
                 answer_id + '\001' +
                 answer_quesion + '\001' +
                 answer_score ;
    }
}
