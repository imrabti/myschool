package com.gsr.myschool.back.client.web.application.admission;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.TypeLiteral;
import com.gsr.myschool.back.client.web.application.admission.popup.CommentAdmissionPresenter;
import com.gsr.myschool.back.client.web.application.admission.popup.CommentAdmissionUiHandlers;
import com.gsr.myschool.back.client.web.application.admission.popup.CommentAdmissionView;
import com.gsr.myschool.back.client.web.application.admission.renderer.AdmissionActionCellFactory;
import com.gsr.myschool.common.client.mvp.uihandler.SetterUiHandlersStrategy;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class AdmissionModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<UiHandlersStrategy<AdmissionUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<AdmissionUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<CommentAdmissionUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<CommentAdmissionUiHandlers>>() {});

        bindSingletonPresenterWidget(CommentAdmissionPresenter.class, CommentAdmissionPresenter.MyView.class,
                CommentAdmissionView.class);
        bindPresenter(AdmissionPresenter.class, AdmissionPresenter.MyView.class, AdmissionView.class,
                AdmissionPresenter.MyProxy.class);

        install(new GinFactoryModuleBuilder().build(AdmissionActionCellFactory.class));
    }
}
