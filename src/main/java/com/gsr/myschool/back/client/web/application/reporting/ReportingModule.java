package com.gsr.myschool.back.client.web.application.reporting;

import com.gsr.myschool.back.client.web.application.reporting.widget.ConvocationReportPresenter;
import com.gsr.myschool.back.client.web.application.reporting.widget.ConvocationReportUiHandlers;
import com.gsr.myschool.back.client.web.application.reporting.widget.ConvocationReportView;
import com.gsr.myschool.back.client.web.application.reporting.widget.MultipleInscriptionsReportPresenter;
import com.gsr.myschool.back.client.web.application.reporting.widget.MultipleInscriptionsReportUiHandlers;
import com.gsr.myschool.back.client.web.application.reporting.widget.MultipleInscriptionsReportView;
import com.gsr.myschool.back.client.web.application.reporting.widget.SummaryReportPresenter;
import com.gsr.myschool.back.client.web.application.reporting.widget.SummaryReportUiHandlers;
import com.gsr.myschool.back.client.web.application.reporting.widget.SummaryReportView;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ReportingModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(ReportingUiHandlers.class).to(ReportingPresenter.class);
        bind(SummaryReportUiHandlers.class).to(SummaryReportPresenter.class);
        bind(ConvocationReportUiHandlers.class).to(ConvocationReportPresenter.class);
        bind(MultipleInscriptionsReportUiHandlers.class).to(MultipleInscriptionsReportPresenter.class);

        bindPresenter(ReportingPresenter.class, ReportingPresenter.MyView.class, ReportingView.class,
                ReportingPresenter.MyProxy.class);

        bindSingletonPresenterWidget(SummaryReportPresenter.class, SummaryReportPresenter.MyView.class,
                SummaryReportView.class);
        bindSingletonPresenterWidget(ConvocationReportPresenter.class, ConvocationReportPresenter.MyView.class,
                ConvocationReportView.class);
        bindSingletonPresenterWidget(MultipleInscriptionsReportPresenter.class,
                MultipleInscriptionsReportPresenter.MyView.class, MultipleInscriptionsReportView.class);
    }
}
