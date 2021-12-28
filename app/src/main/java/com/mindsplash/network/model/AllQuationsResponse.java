package com.mindsplash.network.model;

import java.util.List;

public class AllQuationsResponse {
    private int status;
    private String message;
    private List<AllQuation> data;

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

    public List<AllQuation> getData() {
        return data;
    }

    public void setData(List<AllQuation> data) {
        this.data = data;
    }
}
