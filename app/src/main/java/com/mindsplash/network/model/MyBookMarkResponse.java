package com.mindsplash.network.model;

import java.util.List;

public class MyBookMarkResponse {
    private int status;
    private String message;
    private BookmarkData data;

    public class BookmarkData {
        private List<String> questions;
        private List<String> concepts;
        private List<String> subkects;

        public List<String> getQuestions() {
            return questions;
        }

        public void setQuestions(List<String> questions) {
            this.questions = questions;
        }

        public List<String> getConcepts() {
            return concepts;
        }

        public void setConcepts(List<String> concepts) {
            this.concepts = concepts;
        }

        public List<String> getSubkects() {
            return subkects;
        }

        public void setSubkects(List<String> subkects) {
            this.subkects = subkects;
        }
    }

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

    public BookmarkData getData() {
        return data;
    }

    public void setData(BookmarkData data) {
        this.data = data;
    }
}
