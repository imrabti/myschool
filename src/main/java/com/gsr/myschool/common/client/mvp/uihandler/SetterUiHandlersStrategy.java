package com.gsr.myschool.common.client.mvp.uihandler;

import com.gwtplatform.mvp.client.UiHandlers;

public class SetterUiHandlersStrategy<H extends UiHandlers> implements UiHandlersStrategy<H> {
    private H uiHandlers;

    @Override
    public void setUiHandlers(H uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    @Override
    public H getUiHandlers() {
        return uiHandlers;
    }
}
