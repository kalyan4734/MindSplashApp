package com.mindsplash.network.model;

import java.util.List;

public class QuationsResponse {
    private int status;
    private String message;
    private List<Quation> data;

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

    public List<Quation> getData() {
        return data;
    }

    public void setData(List<Quation> data) {
        this.data = data;
    }
}
