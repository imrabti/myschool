package com.gsr.myschool.back.client.web.application.valueList;

import com.gwtplatform.mvp.client.UiHandlers;

public interface ListLOVUiHandlers extends UiHandlers {
    void getParent();

    void refreshList();

    void delete();

    void modify();
}
