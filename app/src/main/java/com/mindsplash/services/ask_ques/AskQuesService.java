package com.mindsplash.services.ask_ques;

import com.mindsplash.helper.CallBack;

import java.io.File;

public interface AskQuesService {

    void postAskQues(String uid, String text, File imagefile, CallBack callBack);
}
