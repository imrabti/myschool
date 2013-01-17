package com.gsr.myschool.back.client.web.administration.valueList;

import com.gwtplatform.mvp.client.UiHandlers;

public interface ListLOVUiHandlers extends UiHandlers {
    public void getParent();
    public void refreshList();
    public void delete();
    public void modify();
}
