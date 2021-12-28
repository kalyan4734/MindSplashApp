package com.mindsplash.services.search;

import com.mindsplash.helper.CallBack;

public interface SearchService {

    void getSearchList(String searchTerm, CallBack callBack);
}
