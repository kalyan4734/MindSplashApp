package com.mindsplash.services.ask_ques;

import android.util.Log;

import com.google.gson.Gson;
import com.mindsplash.helper.CallBack;
import com.mindsplash.helper.FileHelper;
import com.mindsplash.network.ApiClient;
import com.mindsplash.network.ApiInterface;
import com.mindsplash.network.model.CommonResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AskQuesServiceImpl implements AskQuesService {

    private ApiInterface apiInterface;

    public AskQuesServiceImpl() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public static MultipartBody.Part generateFileBody(String imagePath)
    {
        File file = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), file);
        return MultipartBody.Part.createFormData("que_image", file.getName().trim(), requestFile);
    }
    @Override
    public void postAskQues(String uid, String text, File imagefiele, CallBack callBack) {
        Log.e("userid",uid);

        MultipartBody.Part image;
        MultipartBody.Part filePart;
        if (imagefiele != null) {


                     filePart =generateFileBody(imagefiele.getAbsolutePath());
//                             MultipartBody.Part.createFormData("que_image", imagefiele.getName(), RequestBody.create(MediaType.parse("image/*"), imagefiele));

            image=  new FileHelper().createPart(
                    imagefiele, new FileHelper().createRequestBody(
                            imagefiele
                    ), "que_image");
        }else {
            image = null;
            filePart = null;
        }

        Log.e("userid",new Gson().toJson(image));
        Log.e("userid",new Gson().toJson(filePart));
        Call<ResponseBody> call = apiInterface.postaskQues(
                "42",text, filePart);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body()!=null  ) {
                    Log.e("response",response.body().toString());
                    try {
                        Log.e("response",response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    callBack.onSuccess(response.body());

                }  else
                    callBack.onSuccess(null);
//                Log.e("response", new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBack.onError(t.getMessage());
            }
        });
    }


}
