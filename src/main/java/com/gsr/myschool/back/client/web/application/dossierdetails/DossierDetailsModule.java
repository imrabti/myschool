package com.gsr.myschool.back.client.web.application.dossierdetails;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class DossierDetailsModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(InscriptionDetailUiHandlers.class).to(InscriptionDetailPresenter.class);

        bindPresenter(InscriptionDetailPresenter.class, InscriptionDetailPresenter.MyView.class,
                InscriptionDetailView.class, InscriptionDetailPresenter.MyProxy.class);
    }
}
