package com.mindsplash.services.subject.concepimpl;

import com.mindsplash.helper.CallBack;
import com.mindsplash.network.ApiClient;
import com.mindsplash.network.ApiInterface;
import com.mindsplash.network.model.ConceptResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConceptServiceImpl implements ConceptService {

    private ApiInterface apiInterface;

    public ConceptServiceImpl() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    public void getDefinitions(String sid, String cid, CallBack callBack) {
        Call<ConceptResponse> call = apiInterface.getConcepts(sid,cid);
        call.enqueue(new Callback<ConceptResponse>() {
            @Override
            public void onResponse(Call<ConceptResponse> call, Response<ConceptResponse> response) {
                if (response.body()!=null && response.body().getStatus() == 1) {
                    callBack.onSuccess(response.body());
                } else
                    callBack.onSuccess(null);
            }

            @Override
            public void onFailure(Call<ConceptResponse> call, Throwable t) {
                callBack.onError(t.getMessage());
            }
        });
    }
}
