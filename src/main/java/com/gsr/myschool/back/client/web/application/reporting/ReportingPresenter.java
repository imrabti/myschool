package com.gsr.myschool.back.client.web.application.reporting;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.web.application.ApplicationPresenter;
import com.gsr.myschool.back.client.web.application.reporting.ReportingPresenter.MyView;
import com.gsr.myschool.back.client.web.application.reporting.ReportingPresenter.MyProxy;
import com.gsr.myschool.back.client.web.application.reporting.widget.ConvocationReportPresenter;
import com.gsr.myschool.back.client.web.application.reporting.widget.MultipleInscriptionsReportPresenter;
import com.gsr.myschool.back.client.web.application.reporting.widget.SummaryReportPresenter;
import com.gsr.myschool.common.client.security.HasRoleGatekeeper;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.GatekeeperParams;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

public class ReportingPresenter extends Presenter<MyView, MyProxy> implements ReportingUiHandlers {
    public interface MyView extends View, HasUiHandlers<ReportingUiHandlers> {
    }

    @ProxyStandard
    @NameToken(NameTokens.reporting)
    @UseGatekeeper(HasRoleGatekeeper.class)
    @GatekeeperParams({GlobalParameters.ROLE_ADMIN, GlobalParameters.ROLE_REPORTER})
    public interface MyProxy extends ProxyPlace<ReportingPresenter> {
    }

    public static final Object TYPE_SetSummaryContent = new Object();
    public static final Object TYPE_SetConvocationsContent = new Object();
    public static final Object TYPE_SetMultipleInscriptionContent = new Object();

    private final SummaryReportPresenter summaryReportPresenter;
    private final ConvocationReportPresenter convocationReportPresenter;
    private final MultipleInscriptionsReportPresenter multipleInscriptionsReportPresenter;

    @Inject
    public ReportingPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                              final SummaryReportPresenter summaryReportPresenter,
                              final ConvocationReportPresenter convocationReportPresenter,
                              final MultipleInscriptionsReportPresenter multipleInscriptionsReportPresenter) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.summaryReportPresenter = summaryReportPresenter;
        this.convocationReportPresenter = convocationReportPresenter;
        this.multipleInscriptionsReportPresenter = multipleInscriptionsReportPresenter;

        getView().setUiHandlers(this);
    }

    @Override
    protected void onReveal() {
        setInSlot(TYPE_SetSummaryContent, summaryReportPresenter);
        setInSlot(TYPE_SetConvocationsContent, convocationReportPresenter);
        setInSlot(TYPE_SetMultipleInscriptionContent, multipleInscriptionsReportPresenter);
    }
}
