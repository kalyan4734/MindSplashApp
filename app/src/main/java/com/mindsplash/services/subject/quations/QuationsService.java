package com.mindsplash.services.subject.quations;

import com.mindsplash.helper.CallBack;

public interface QuationsService {

    void getOlympiadDetails(CallBack callBack);

    void getOlympiadQuestions(String olyId, CallBack callBack);

    void getSchoolQuestions(String studentId,CallBack callBack);

    void getAllSchoolQuestions(CallBack callBack);
}
