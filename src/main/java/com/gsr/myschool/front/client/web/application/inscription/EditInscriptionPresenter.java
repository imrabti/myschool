package com.gsr.myschool.front.client.web.application.inscription;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.security.LoggedInGatekeeper;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gsr.myschool.front.client.web.application.ApplicationPresenter;
import com.gsr.myschool.front.client.web.application.inscription.EditInscriptionPresenter.MyView;
import com.gsr.myschool.front.client.web.application.inscription.EditInscriptionPresenter.MyProxy;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

public class EditInscriptionPresenter extends Presenter<MyView, MyProxy>  {
    public interface MyView extends View {
    }

    @ProxyStandard
    @NameToken(NameTokens.editinscription)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface MyProxy extends ProxyPlace<EditInscriptionPresenter> {
    }

    public static final Object TYPE_Step_1_Content = new Object();
    public static final Object TYPE_Step_2_Content = new Object();
    public static final Object TYPE_Step_3_Content = new Object();

    @Inject
    public EditInscriptionPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);
    }
}
