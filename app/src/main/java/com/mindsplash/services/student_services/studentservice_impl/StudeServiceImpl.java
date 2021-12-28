package com.mindsplash.services.student_services.studentservice_impl;

import com.mindsplash.helper.CallBack;
import com.mindsplash.network.ApiClient;
import com.mindsplash.network.ApiInterface;
import com.mindsplash.network.model.BookmarkRes;
import com.mindsplash.network.model.ChapterListResponse;
import com.mindsplash.network.model.MyBookMarkResponse;
import com.mindsplash.network.model.Student;
import com.mindsplash.network.model.StudentDetails;
import com.mindsplash.network.model.SubjectDetails;
import com.mindsplash.network.model.VerifyMobile;
import com.mindsplash.services.student_services.StudentService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudeServiceImpl implements StudentService {
    private ApiInterface apiInterface;

    public StudeServiceImpl() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }
    @Override
    public void getStudentDetails(String mobile, CallBack callBack) {
        Call<StudentDetails> call = apiInterface.getStudentDetails(mobile);
        call.enqueue(new Callback<StudentDetails>() {
            @Override
            public void onResponse(Call<StudentDetails> call, Response<StudentDetails> response) {
                if (response.body()!=null && response.body().getStatus() == 1) {
                    callBack.onSuccess(response.body());
                } else
                    callBack.onSuccess(null);
            }

            @Override
            public void onFailure(Call<StudentDetails> call, Throwable t) {
                callBack.onError(t.getMessage());
            }
        });
    }

    @Override
    public void getSubjectDetails(CallBack callBack) {
        Call<SubjectDetails> call = apiInterface.getSubjectDetails();
        call.enqueue(new Callback<SubjectDetails>() {
            @Override
            public void onResponse(Call<SubjectDetails> call, Response<SubjectDetails> response) {
                if (response.body()!=null && response.body().getStatus() == 1) {
                    callBack.onSuccess(response.body());
                } else
                    callBack.onSuccess(null);
            }

            @Override
            public void onFailure(Call<SubjectDetails> call, Throwable t) {
                callBack.onError(t.getMessage());
            }
        });
    }

    @Override
    public void getChapters(String subjectId, CallBack callBack) {
        Call<ChapterListResponse> call = apiInterface.getChapterList(subjectId);
        call.enqueue(new Callback<ChapterListResponse>() {
            @Override
            public void onResponse(Call<ChapterListResponse> call, Response<ChapterListResponse> response) {
                if (response.body()!=null && response.body().getStatus() == 1) {
                    callBack.onSuccess(response.body());
                } else
                    callBack.onSuccess(null);
            }

            @Override
            public void onFailure(Call<ChapterListResponse> call, Throwable t) {
                callBack.onError(t.getMessage());
            }
        });
    }

    @Override
    public void bookmark(String userId, String question, String concepts, String subject, CallBack callBack) {
        Call<BookmarkRes> call = apiInterface.bookmark(Integer.parseInt(userId),question,concepts,subject);
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

    @Override
    public void getMyBookMarks(String userId, CallBack callBack) {
        Call<MyBookMarkResponse> call = apiInterface.getMyBookMarks(Integer.parseInt(userId));
        call.enqueue(new Callback<MyBookMarkResponse>() {
            @Override
            public void onResponse(Call<MyBookMarkResponse> call, Response<MyBookMarkResponse> response) {
                if (response.body()!=null && response.body().getStatus() == 1) {
                    callBack.onSuccess(response.body());
                } else
                    callBack.onSuccess(null);
            }

            @Override
            public void onFailure(Call<MyBookMarkResponse> call, Throwable t) {
                callBack.onError(t.getMessage());
            }
        });
    }

    @Override
    public void removeAccount(String myRegId, CallBack callBack) {
        Call<Object> call = apiInterface.removeAccount(myRegId);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.body()!=null) {
                    callBack.onSuccess(response.body());
                } else
                    callBack.onSuccess(null);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                callBack.onError(t.getMessage());
            }
        });
    }
}
