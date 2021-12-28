package com.mindsplash.services.subject.concepimpl;

import com.mindsplash.helper.CallBack;

public interface ConceptService {

    void getDefinitions(String sid, String cid, CallBack callBack);
}
