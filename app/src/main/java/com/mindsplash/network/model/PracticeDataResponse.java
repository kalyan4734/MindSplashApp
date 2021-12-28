package com.mindsplash.network.model;

import java.util.List;

public class PracticeDataResponse {
    private int status;
    private String message;
    private List<PracticeData> data;

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

    public List<PracticeData> getData() {
        return data;
    }

    public void setData(List<PracticeData> data) {
        this.data = data;
    }
}
