package com.mindsplash.network.model;

import java.util.List;

public class OlympiadQuesRes {
    private int status;
    private String message;
    private List<OlympiadQues> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<OlympiadQues> getData() {
        return data;
    }

    public void setData(List<OlympiadQues> data) {
        this.data = data;
    }
}
