package com.mindsplash.network.model;

public class Olympiad {
    private String olympiad_id;
    private String cid;
    private String practiceid;
    private String exam_name;
    private String exam_year;
    private String timestamp;

    public String getOlympiad_id() {
        return olympiad_id;
    }

    public void setOlympiad_id(String olympiad_id) {
        this.olympiad_id = olympiad_id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPracticeid() {
        return practiceid;
    }

    public void setPracticeid(String practiceid) {
        this.practiceid = practiceid;
    }

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public String getExam_year() {
        return exam_year;
    }

    public void setExam_year(String exam_year) {
        this.exam_year = exam_year;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
