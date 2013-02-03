package com.gsr.myschool.back.client.web.application.reception;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.TypeLiteral;
import com.gsr.myschool.back.client.web.application.reception.renderer.ReceptionActionCellFactory;
import com.gsr.myschool.common.client.mvp.uihandler.SetterUiHandlersStrategy;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ReceptionModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<UiHandlersStrategy<ReceptionUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<ReceptionUiHandlers>>() {});

        bindPresenter(ReceptionPresenter.class, ReceptionPresenter.MyView.class, ReceptionView.class,
                ReceptionPresenter.MyProxy.class);

        install(new GinFactoryModuleBuilder().build(ReceptionActionCellFactory.class));
    }
}
