package com.gsr.myschool.back.client.web.application.validation;

import com.google.inject.TypeLiteral;
import com.gsr.myschool.common.client.mvp.uihandler.SetterUiHandlersStrategy;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ValidationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<UiHandlersStrategy<ValidationUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<ValidationUiHandlers>>() {});

        bindPresenter(ValidationPresenter.class, ValidationPresenter.MyView.class, ValidationView.class,
                ValidationPresenter.MyProxy.class);
    }
}
