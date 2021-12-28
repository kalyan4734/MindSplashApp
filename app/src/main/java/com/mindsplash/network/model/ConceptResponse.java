package com.mindsplash.network.model;

import java.util.List;

public class ConceptResponse {
    private int status;
    private String message;
    private ConceptListData data;

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

    public ConceptListData getData() {
        return data;
    }

    public void setData(ConceptListData data) {
        this.data = data;
    }
}
