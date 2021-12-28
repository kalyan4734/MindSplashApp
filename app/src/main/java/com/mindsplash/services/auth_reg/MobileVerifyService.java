package com.mindsplash.services.auth_reg;

import com.mindsplash.helper.CallBack;

public interface MobileVerifyService {

    void verifyMobile(String mobileNumber, CallBack callBack);

    void verifyMobileOtp(String mobileNumber, String otp, CallBack callBack);

    void verifyEmailId(String mobile,String emailId,CallBack callBack);

    void createStudent(String mobile, String fname, String lname, String parentEmail, int class_, CallBack callBack);
}
