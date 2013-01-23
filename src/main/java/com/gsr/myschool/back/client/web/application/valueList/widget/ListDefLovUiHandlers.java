package com.gsr.myschool.back.client.web.application.valueList.widget;

import com.gwtplatform.mvp.client.UiHandlers;

public interface ListDefLovUiHandlers extends UiHandlers {
    public void selectionChanged();

    public void modify();

    public void delete();

    void addValueType();
}
