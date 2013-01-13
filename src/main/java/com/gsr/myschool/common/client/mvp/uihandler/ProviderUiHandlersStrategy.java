package com.gsr.myschool.common.client.mvp.uihandler;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.UiHandlers;

public class ProviderUiHandlersStrategy<H extends UiHandlers> implements UiHandlersStrategy<H> {
    private final Provider<H> uiHandlersProvider;

    @Inject
    public ProviderUiHandlersStrategy(final Provider<H> uiHandlersProvider) {
        this.uiHandlersProvider = uiHandlersProvider;
    }

    @Override
    public void setUiHandlers(H uiHandlers) {
    }

    @Override
    public H getUiHandlers() {
        return uiHandlersProvider.get();
    }
}
