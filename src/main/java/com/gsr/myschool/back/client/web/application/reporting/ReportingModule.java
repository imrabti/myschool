package com.gsr.myschool.back.client.web.application.reporting;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ReportingModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(ReportingUiHandlers.class).to(ReportingPresenter.class);

        bindPresenter(ReportingPresenter.class, ReportingPresenter.MyView.class, ReportingView.class,
                ReportingPresenter.MyProxy.class);
    }
}
