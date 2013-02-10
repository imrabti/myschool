package com.gsr.myschool.back.client.web.application.dossierdetails;

import com.google.inject.TypeLiteral;
import com.gsr.myschool.common.client.mvp.uihandler.SetterUiHandlersStrategy;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class DossierDetailsModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<UiHandlersStrategy<InscriptionDetailUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<InscriptionDetailUiHandlers>>() {});

        bindPresenter(InscriptionDetailPresenter.class, InscriptionDetailPresenter.MyView.class,
                InscriptionDetailView.class, InscriptionDetailPresenter.MyProxy.class);
    }
}
