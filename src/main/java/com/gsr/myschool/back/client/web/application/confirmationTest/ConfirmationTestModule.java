package com.gsr.myschool.back.client.web.application.confirmationTest;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.TypeLiteral;
import com.gsr.myschool.back.client.web.application.confirmationTest.popup.DenyForTestPresenter;
import com.gsr.myschool.back.client.web.application.confirmationTest.popup.DenyForTestUiHandlers;
import com.gsr.myschool.back.client.web.application.confirmationTest.popup.DenyForTestView;
import com.gsr.myschool.back.client.web.application.confirmationTest.renderer.ConfirmationTestActionCellFactory;
import com.gsr.myschool.common.client.mvp.uihandler.SetterUiHandlersStrategy;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ConfirmationTestModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<UiHandlersStrategy<ConfirmationTestUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<ConfirmationTestUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<DenyForTestUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<DenyForTestUiHandlers>>() {});

        bindSingletonPresenterWidget(DenyForTestPresenter.class, DenyForTestPresenter.MyView.class,
                DenyForTestView.class);
        bindPresenter(ConfirmationTestPresenter.class, ConfirmationTestPresenter.MyView.class, ConfirmationTestView.class,
                ConfirmationTestPresenter.MyProxy.class);

        install(new GinFactoryModuleBuilder().build(ConfirmationTestActionCellFactory.class));
    }
}
