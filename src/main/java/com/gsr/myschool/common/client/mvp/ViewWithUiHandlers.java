package com.gsr.myschool.common.client.mvp;

import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gwtplatform.mvp.client.UiHandlers;

public abstract class ViewWithUiHandlers<H extends UiHandlers> extends ViewImpl
        implements UiHandlersStrategy<H> {
    private UiHandlersStrategy<H> uiHandlersStrategy;

    public ViewWithUiHandlers(final UiHandlersStrategy<H> uiHandlersStrategy) {
        this.uiHandlersStrategy = uiHandlersStrategy;
    }

    @Override
    public H getUiHandlers() {
        return uiHandlersStrategy.getUiHandlers();
    }

    @Override
    public void setUiHandlers(H uiHandlers) {
        uiHandlersStrategy.setUiHandlers(uiHandlers);
    }
}
