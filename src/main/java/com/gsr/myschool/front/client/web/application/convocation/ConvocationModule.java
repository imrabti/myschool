package com.gsr.myschool.front.client.web.application.convocation;

import com.google.inject.TypeLiteral;
import com.gsr.myschool.common.client.mvp.uihandler.SetterUiHandlersStrategy;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ConvocationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<UiHandlersStrategy<ConvocationUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<ConvocationUiHandlers>>() {});

        bindPresenter(ConvocationPresenter.class, ConvocationPresenter.MyView.class, ConvocationView.class,
                ConvocationPresenter.MyProxy.class);
    }
}
