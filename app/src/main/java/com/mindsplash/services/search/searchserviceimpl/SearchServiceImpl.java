package com.mindsplash.services.search.searchserviceimpl;

import com.mindsplash.helper.CallBack;
import com.mindsplash.network.ApiClient;
import com.mindsplash.network.ApiInterface;
import com.mindsplash.network.model.SearchResponse;
import com.mindsplash.services.search.SearchService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchServiceImpl implements SearchService {


    private ApiInterface apiInterface;


    public SearchServiceImpl(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }
    @Override
    public void getSearchList(String searchTerm, CallBack callBack) {
        Call<SearchResponse> call = apiInterface.getSearchList(searchTerm);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.body()!=null && response.body().getStatus() == 1) {
                    callBack.onSuccess(response.body());
                } else
                    callBack.onSuccess(null);
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                callBack.onError(t.getMessage());
            }
        });
    }
}
