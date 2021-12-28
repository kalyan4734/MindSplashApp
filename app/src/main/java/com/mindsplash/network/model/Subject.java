package com.mindsplash.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Subject implements Serializable {
    @SerializedName("sid")
    @Expose
    private String sid;
    @SerializedName("subjectname")
    @Expose
    private String subjectname;
    @SerializedName("author_name")
    @Expose
    private String author_name;
    @SerializedName("class")
    @Expose
    private String class_;
    @SerializedName("edition")
    @Expose
    private String edition;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getClass_() {
        return class_;
    }

    public void setClass_(String class_) {
        this.class_ = class_;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
