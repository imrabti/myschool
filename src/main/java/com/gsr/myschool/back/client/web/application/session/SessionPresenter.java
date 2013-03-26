package com.gsr.myschool.back.client.web.application.session;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.web.application.ApplicationPresenter;
import com.gsr.myschool.back.client.web.application.session.SessionPresenter.MyView;
import com.gsr.myschool.back.client.web.application.session.SessionPresenter.MyProxy;
import com.gsr.myschool.back.client.web.application.session.popup.EditSessionPresenter;
import com.gsr.myschool.common.client.proxy.SessionExamenProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
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

import java.util.List;

public class SessionPresenter extends Presenter<MyView, MyProxy> implements SessionUiHandlers {
    public interface MyView extends View, HasUiHandlers<SessionUiHandlers> {
        void setData(List<SessionExamenProxy> sessions);
    }

    @ProxyStandard
    @NameToken(NameTokens.session)
    @UseGatekeeper(HasRoleGatekeeper.class)
    @GatekeeperParams({GlobalParameters.ROLE_ADMIN})
    public interface MyProxy extends ProxyPlace<SessionPresenter> {
    }

    private final BackRequestFactory requestFactory;
    private final EditSessionPresenter editSessionPresenter;

    @Inject
    public SessionPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                            final BackRequestFactory requestFactory,
                            final EditSessionPresenter editSessionPresenter) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.requestFactory = requestFactory;
        this.editSessionPresenter = editSessionPresenter;

        getView().setUiHandlers(this);
    }

    @Override
    protected void onReveal() {
        loadSession();
    }

    private void loadSession() {
        requestFactory.sessionService().findAllSessions().fire(new ReceiverImpl<List<SessionExamenProxy>>() {
            @Override
            public void onSuccess(List<SessionExamenProxy> result) {
                getView().setData(result);
            }
        });
    }
}
