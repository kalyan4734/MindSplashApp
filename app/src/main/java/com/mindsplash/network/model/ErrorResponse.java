package com.mindsplash.network.model;

//{"status":0,"message":"Mobile number already exist please login",
// "drivdata":{"id":"18","mobileno":"7709596598","firstname":null,"lastname":null,"class":null,"parentemail":null,"address":null,"otp":"9065","isverified":null,"timestamp":"2021-12-08 07:15:46"}}
public class ErrorResponse {
    private int status;
    private String message;
    protected Student drivdata;

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

    public Student getDrivdata() {
        return drivdata;
    }

    public void setDrivdata(Student drivdata) {
        this.drivdata = drivdata;
    }
}
