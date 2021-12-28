package com.mindsplash.network.model;

import java.util.List;

import java.util.ArrayList;
import java.util.List;
public class Concept
{
    private List<String> definitions;

    private String conceptid;

    private String sid;

    private String cid;

    private String timestamp;

    private List<String> theorems;

    private List<String> formulae;

    public void setDefinitions(List<String> definitions){
        this.definitions = definitions;
    }
    public List<String> getDefinitions(){
        return this.definitions;
    }
    public void setConceptid(String conceptid){
        this.conceptid = conceptid;
    }
    public String getConceptid(){
        return this.conceptid;
    }
    public void setSid(String sid){
        this.sid = sid;
    }
    public String getSid(){
        return this.sid;
    }
    public void setCid(String cid){
        this.cid = cid;
    }
    public String getCid(){
        return this.cid;
    }
    public void setTimestamp(String timestamp){
        this.timestamp = timestamp;
    }
    public String getTimestamp(){
        return this.timestamp;
    }
    public void setTheorems(List<String> theorems){
        this.theorems = theorems;
    }
    public List<String> getTheorems(){
        return this.theorems;
    }
    public void setFormulae(List<String> formulae){
        this.formulae = formulae;
    }
    public List<String> getFormulae(){
        return this.formulae;
    }
}