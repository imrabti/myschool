package com.gsr.myschool.back.client.web.application.valueList;

import com.gwtplatform.mvp.client.UiHandlers;

public interface ListLOVUiHandlers extends UiHandlers {
    public void getParent();
    public void refreshList();
    public void delete();
    public void modify();
}
