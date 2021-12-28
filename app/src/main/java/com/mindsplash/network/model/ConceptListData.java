package com.mindsplash.network.model;

import java.util.List;

public class ConceptListData {
    private String conceptid;
    private String sid;
    private String cid;
    private List<String> definitions;
    private List<String> theorems;
    private List<String> formulae;
    private List<String> quick_tips;
    private String timestamp;

    public String getConceptid() {
        return conceptid;
    }

    public void setConceptid(String conceptid) {
        this.conceptid = conceptid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public List<String> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<String> definitions) {
        this.definitions = definitions;
    }

    public List<String> getTheorems() {
        return theorems;
    }

    public void setTheorems(List<String> theorems) {
        this.theorems = theorems;
    }

    public List<String> getFormulae() {
        return formulae;
    }

    public void setFormulae(List<String> formulae) {
        this.formulae = formulae;
    }

    public List<String> getQuick_tips() {
        return quick_tips;
    }

    public void setQuick_tips(List<String> quick_tips) {
        this.quick_tips = quick_tips;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
