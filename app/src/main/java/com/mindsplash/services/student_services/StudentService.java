package com.mindsplash.services.student_services;

import com.mindsplash.helper.CallBack;

public interface StudentService {
    void getStudentDetails(String mobile, CallBack callBack);

    void getSubjectDetails(CallBack callBack);

    void getChapters(String subjectId,CallBack callBack);

    void bookmark(String userId,String question,String concepts,String subject,CallBack callBack);

    void getMyBookMarks(String userId,CallBack callBack);

    void removeAccount(String myRegId,CallBack callBack);
}
