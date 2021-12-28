package com.mindsplash.services.subject.quations.quationsimpl;

import com.mindsplash.helper.CallBack;
import com.mindsplash.network.ApiClient;
import com.mindsplash.network.ApiInterface;
import com.mindsplash.network.model.AllQuationsResponse;
import com.mindsplash.network.model.OlympiadDetailRes;
import com.mindsplash.network.model.OlympiadQuesRes;
import com.mindsplash.services.subject.quations.QuationsService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuationsImpl implements QuationsService {

    private ApiInterface apiInterface;

    public QuationsImpl() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    public void getOlympiadDetails(CallBack callBack) {
        Call<OlympiadDetailRes> call = apiInterface.getOlympiadDetails();
        call.enqueue(new Callback<OlympiadDetailRes>() {
            @Override
            public void onResponse(Call<OlympiadDetailRes> call, Response<OlympiadDetailRes> response) {
                if (response.body()!=null && response.body().getStatus() == 1) {
                    callBack.onSuccess(response.body());
                } else
                    callBack.onSuccess(null);
            }

            @Override
            public void onFailure(Call<OlympiadDetailRes> call, Throwable t) {
                callBack.onError(t.getMessage());
            }
        });
    }

    @Override
    public void getOlympiadQuestions(String olyId, CallBack callBack) {
        Call<OlympiadQuesRes> call = apiInterface.getOlympiadQuestions(olyId);
        call.enqueue(new Callback<OlympiadQuesRes>() {
            @Override
            public void onResponse(Call<OlympiadQuesRes> call, Response<OlympiadQuesRes> response) {
                if (response.body()!=null && response.body().getStatus() == 1) {
                    callBack.onSuccess(response.body());
                } else
                    callBack.onSuccess(null);
            }

            @Override
            public void onFailure(Call<OlympiadQuesRes> call, Throwable t) {
                callBack.onError(t.getMessage());
            }
        });
    }

    @Override
    public void getSchoolQuestions(String studentId, CallBack callBack) {
        Call<AllQuationsResponse> call = apiInterface.getAllQuations();
        call.enqueue(new Callback<AllQuationsResponse>() {
            @Override
            public void onResponse(Call<AllQuationsResponse> call, Response<AllQuationsResponse> response) {
                if (response.body()!=null && response.body().getStatus() == 1) {
                    callBack.onSuccess(response.body());
                } else
                    callBack.onSuccess(null);
            }

            @Override
            public void onFailure(Call<AllQuationsResponse> call, Throwable t) {
                callBack.onError(t.getMessage());
            }
        });
    }

    @Override
    public void getAllSchoolQuestions(CallBack callBack) {
        Call<AllQuationsResponse> call = apiInterface.getAllQuations();
        call.enqueue(new Callback<AllQuationsResponse>() {
            @Override
            public void onResponse(Call<AllQuationsResponse> call, Response<AllQuationsResponse> response) {
                if (response.body()!=null && response.body().getStatus() == 1) {
                    callBack.onSuccess(response.body());
                } else
                    callBack.onSuccess(null);
            }

            @Override
            public void onFailure(Call<AllQuationsResponse> call, Throwable t) {
                callBack.onError(t.getMessage());
            }
        });
    }
}
