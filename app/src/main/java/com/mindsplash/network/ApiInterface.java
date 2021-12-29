package com.mindsplash.network;

import com.mindsplash.network.model.AllQuationsResponse;
import com.mindsplash.network.model.ApiStandardResponse;
import com.mindsplash.network.model.BookmarkRes;
import com.mindsplash.network.model.ChapterListResponse;
import com.mindsplash.network.model.CommonResponse;
import com.mindsplash.network.model.ConceptResponse;
import com.mindsplash.network.model.MyBookMarkResponse;
import com.mindsplash.network.model.OlympiadDetailRes;
import com.mindsplash.network.model.OlympiadQuesRes;
import com.mindsplash.network.model.QuationsResponse;
import com.mindsplash.network.model.SearchResponse;
import com.mindsplash.network.model.StudentDetails;
import com.mindsplash.network.model.SubjectDetails;
import com.mindsplash.network.model.VerifyEmailResponse;
import com.mindsplash.network.model.VerifyMobile;
import com.mindsplash.network.model.VerifyMobileOtp;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("student_api/student_signup/")
    Call<VerifyMobile> verifyMobile(@Field("mobile") String mobileNumber);

    @FormUrlEncoded
    @POST("student_api/verifysignupotp/")
    Call<VerifyMobileOtp> verifyMobileOtp(@Field("mobile") String mobileNumber,
                                          @Field("otp") String otp);

    @FormUrlEncoded
    @POST("student_api/create_account/")
    Call<ApiStandardResponse> createAccount(@Field("mobile") String mobileNumber,
                                            @Field("fname") String fname,
                                            @Field("lname") String lname,
                                            @Field("parentemail") String parentemail,
                                            @Field("class") int class_);

    @FormUrlEncoded
    @POST("student_api/studentdetails/")
    Call<StudentDetails> getStudentDetails(@Field("mobile") String mobile);

    @POST("subject_api/subjectdetails/")
    Call<SubjectDetails> getSubjectDetails();

    @FormUrlEncoded
    @POST("student_api/chapter_list/")
    Call<ChapterListResponse> getChapterList(@Field("subject_id") String subjectId);

    @FormUrlEncoded
    @POST("student_api/verifyparentemail/")
    Call<VerifyEmailResponse> verifyEmailId(@Field("mobile") String subjectId,
                                            @Field("parent_email") String parentEmail);

    @FormUrlEncoded
    @POST("student_api/concepts_list/")
    Call<ConceptResponse> getConcepts(@Field("subject_id") String subjectId,
                                      @Field("chapter_id") String chapterId);

    @FormUrlEncoded
    @POST("student_api/practice_list/")
    Call<ConceptResponse> getPracticeList(@Field("subject_id") String subjectId,
                                          @Field("chapter_id") String chapterId);


    @FormUrlEncoded
    @POST("student_api/question_list/")
    Call<QuationsResponse> getPracticeQuationsByUserId(@Field("user_id") String subjectId);


    @POST("student_api/getallquestions/")
    Call<AllQuationsResponse> getAllQuations();

    @POST("student_api/olympiad_list/")
    Call<OlympiadDetailRes> getOlympiadDetails();

    @FormUrlEncoded
    @POST("student_api/olympiad_question_list/")
    Call<OlympiadQuesRes> getOlympiadQuestions(@Field("olympiad_id") String olympiadId);

    @FormUrlEncoded
    @POST("student_api/remove_account/")
    Call<Object> removeAccount(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("student_api/add_bookmark/")
    Call<BookmarkRes> bookmark(@Field("user_id") int userId,
                               @Field("questions") String questions,
                               @Field("concepts") String concepts,
                               @Field("subjects") String subjects);

    @Multipart
    @POST("student_question_api/add_student_question/")
    Call<ResponseBody> postaskQues(
            @Part("student_id") String userid,
            @Part("student_que") String text,
            @Part MultipartBody.Part image);

  @FormUrlEncoded
    @POST("student_question_api/add_student_question/")
    Call<ResponseBody> postaskQues64(
            @Field("student_id" ) String userid,
            @Field("student_que") String text,
            @Field(value = "que_image",encoded = false) String img
      );


    @FormUrlEncoded
    @POST("student_api/search/")
    Call<SearchResponse> getSearchList(@Field("input") String searchTerm);

    @FormUrlEncoded
    @POST("student_api/get_bookmark/")
    Call<MyBookMarkResponse> getMyBookMarks(@Field("user_id") int userId);
}
