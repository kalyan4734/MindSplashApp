package com.mindsplash.services.test;

import com.mindsplash.helper.CallBack;
import com.mindsplash.network.ApiClient;
import com.mindsplash.network.ApiInterface;
import com.mindsplash.network.model.BookmarkRes;
import com.mindsplash.network.model.ConceptResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentTestImpl implements StudentTest{
    private ApiInterface apiInterface;

    public StudentTestImpl() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }
    @Override
    public void addBookmark(int userId, String quation, String concept, String subject, CallBack callBack) {
        Call<BookmarkRes> call = apiInterface.bookmark(userId,quation,concept,subject);
        call.enqueue(new Callback<BookmarkRes>() {
            @Override
            public void onResponse(Call<BookmarkRes> call, Response<BookmarkRes> response) {
                if (response.body()!=null && response.body().getStatus() == 1) {
                    callBack.onSuccess(response.body());
                } else
                    callBack.onSuccess(null);
            }

            @Override
            public void onFailure(Call<BookmarkRes> call, Throwable t) {
                callBack.onError(t.getMessage());
            }
        });
    }
}
