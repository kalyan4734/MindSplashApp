package com.mindsplash.services.auth_reg.auth_reg_impl;

import com.google.gson.Gson;
import com.mindsplash.helper.CallBack;
import com.mindsplash.network.ApiClient;
import com.mindsplash.network.ApiInterface;
import com.mindsplash.network.model.ApiStandardResponse;
import com.mindsplash.network.model.ErrorResponse;
import com.mindsplash.network.model.VerifyEmailResponse;
import com.mindsplash.network.model.VerifyMobile;
import com.mindsplash.network.model.VerifyMobileOtp;
import com.mindsplash.services.auth_reg.MobileVerifyService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileVerifyImpl implements MobileVerifyService {
    private ApiInterface apiInterface;

    public MobileVerifyImpl() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    public void verifyMobile(String mobileNumber, CallBack callBack) {
        Call<VerifyMobile> call = apiInterface.verifyMobile(mobileNumber);
        call.enqueue(new Callback<VerifyMobile>() {
            @Override
            public void onResponse(Call<VerifyMobile> call, Response<VerifyMobile> response) {
                if (response.body()!=null && response.body().getStatus() == 1) {
                    callBack.onSuccess(response.body());
                } else if (response.errorBody()!=null) {
                    try {
                        ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().string(),ErrorResponse.class);
                        if (errorResponse.getStatus() == 0) {
                            callBack.onSuccess(errorResponse);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    callBack.onSuccess(null);
                } else
                    callBack.onSuccess(null);
            }

            @Override
            public void onFailure(Call<VerifyMobile> call, Throwable t) {
                    callBack.onError(t.getMessage());
            }
        });
    }

    @Override
    public void verifyMobileOtp(String mobileNumber, String otp, CallBack callBack) {
        Call<VerifyMobileOtp> call = apiInterface.verifyMobileOtp(mobileNumber,otp);
        call.enqueue(new Callback<VerifyMobileOtp>() {
            @Override
            public void onResponse(Call<VerifyMobileOtp> call, Response<VerifyMobileOtp> response) {
                if ((response.body() != null ? response.body().getStatus() : 0) == 1) {
                    callBack.onSuccess(response.body());
                } else
                    callBack.onSuccess(null);
            }

            @Override
            public void onFailure(Call<VerifyMobileOtp> call, Throwable t) {
                callBack.onError(t.getMessage());
            }
        });
    }

    @Override
    public void verifyEmailId(String mobile, String emailId, CallBack callBack) {
        Call<VerifyEmailResponse> call = apiInterface.verifyEmailId(mobile,emailId);
        call.enqueue(new Callback<VerifyEmailResponse>() {
            @Override
            public void onResponse(Call<VerifyEmailResponse> call, Response<VerifyEmailResponse> response) {
                if (response.body().getStatus() == 1) {
                    callBack.onSuccess(response.body());
                } else
                    callBack.onSuccess(null);
            }

            @Override
            public void onFailure(Call<VerifyEmailResponse> call, Throwable t) {
                callBack.onError(t.getMessage());
            }
        });
    }

    @Override
    public void createStudent(String mobile, String fname, String lname, String parentEmail, int class_,CallBack callBack) {
        Call<ApiStandardResponse> call = apiInterface.createAccount(mobile,fname,lname,parentEmail,class_);
        call.enqueue(new Callback<ApiStandardResponse>() {
            @Override
            public void onResponse(Call<ApiStandardResponse> call, Response<ApiStandardResponse> response) {
                if (response.body().getStatus() == 1) {
                    callBack.onSuccess(response.body());
                } else
                    callBack.onSuccess(null);
            }

            @Override
            public void onFailure(Call<ApiStandardResponse> call, Throwable t) {
                callBack.onError(t.getMessage());
            }
        });
    }
}
