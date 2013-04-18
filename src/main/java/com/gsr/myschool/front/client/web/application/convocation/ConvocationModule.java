package com.gsr.myschool.front.client.web.application.convocation;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ConvocationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(ConvocationUiHandlers.class).to(ConvocationPresenter.class);

        bindPresenter(ConvocationPresenter.class, ConvocationPresenter.MyView.class, ConvocationView.class,
                ConvocationPresenter.MyProxy.class);
    }
}
