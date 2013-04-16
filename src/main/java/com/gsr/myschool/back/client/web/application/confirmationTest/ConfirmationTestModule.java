package com.gsr.myschool.back.client.web.application.confirmationTest;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.gsr.myschool.back.client.web.application.confirmationTest.popup.DenyForTestPresenter;
import com.gsr.myschool.back.client.web.application.confirmationTest.popup.DenyForTestUiHandlers;
import com.gsr.myschool.back.client.web.application.confirmationTest.popup.DenyForTestView;
import com.gsr.myschool.back.client.web.application.confirmationTest.renderer.ConfirmationTestActionCellFactory;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ConfirmationTestModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(ConfirmationTestUiHandlers.class).to(ConfirmationTestPresenter.class);
        bind(DenyForTestUiHandlers.class).to(DenyForTestPresenter.class);

        bindSingletonPresenterWidget(DenyForTestPresenter.class, DenyForTestPresenter.MyView.class,
                DenyForTestView.class);
        bindPresenter(ConfirmationTestPresenter.class, ConfirmationTestPresenter.MyView.class, ConfirmationTestView.class,
                ConfirmationTestPresenter.MyProxy.class);

        install(new GinFactoryModuleBuilder().build(ConfirmationTestActionCellFactory.class));
    }
}
