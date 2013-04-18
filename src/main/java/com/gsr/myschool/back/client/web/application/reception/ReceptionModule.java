package com.gsr.myschool.back.client.web.application.reception;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.gsr.myschool.back.client.web.application.reception.renderer.ReceptionActionCellFactory;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ReceptionModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(ReceptionUiHandlers.class).to(ReceptionPresenter.class);

        bindPresenter(ReceptionPresenter.class, ReceptionPresenter.MyView.class, ReceptionView.class,
                ReceptionPresenter.MyProxy.class);

        install(new GinFactoryModuleBuilder().build(ReceptionActionCellFactory.class));
    }
}
