package com.gsr.myschool.back.client.web.application.reporting;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.web.application.ApplicationPresenter;
import com.gsr.myschool.back.client.web.application.reporting.ReportingPresenter.MyView;
import com.gsr.myschool.back.client.web.application.reporting.ReportingPresenter.MyProxy;
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
    @GatekeeperParams({GlobalParameters.ROLE_ADMIN})
    public interface MyProxy extends ProxyPlace<ReportingPresenter> {
    }

    @Inject
    public ReportingPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        getView().setUiHandlers(this);
    }
}
