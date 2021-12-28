package com.mindsplash.network.model;

public class OlympiadQues {
    private String oq_id;
    private String olympiad_id;
    private String school_id;
    private String question;
    private String image;
    private String choices;
    private String correct_choice;
    private String solution;
    private String is_correct;
    private String practiceid;
    private String timestamp;

    public String getOq_id() {
        return oq_id;
    }

    public void setOq_id(String oq_id) {
        this.oq_id = oq_id;
    }

    public String getOlympiad_id() {
        return olympiad_id;
    }

    public void setOlympiad_id(String olympiad_id) {
        this.olympiad_id = olympiad_id;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getChoices() {
        return choices;
    }

    public void setChoices(String choices) {
        this.choices = choices;
    }

    public String getCorrect_choice() {
        return correct_choice;
    }

    public void setCorrect_choice(String correct_choice) {
        this.correct_choice = correct_choice;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getIs_correct() {
        return is_correct;
    }

    public void setIs_correct(String is_correct) {
        this.is_correct = is_correct;
    }

    public String getPracticeid() {
        return practiceid;
    }

    public void setPracticeid(String practiceid) {
        this.practiceid = practiceid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
