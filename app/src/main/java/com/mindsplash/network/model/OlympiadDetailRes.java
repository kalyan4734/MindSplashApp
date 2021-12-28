package com.mindsplash.network.model;

import java.util.List;

public class OlympiadDetailRes {
    private int status;
    private String message;
    private List<Olympiad> data;

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

    public List<Olympiad> getData() {
        return data;
    }

    public void setData(List<Olympiad> data) {
        this.data = data;
    }
}
