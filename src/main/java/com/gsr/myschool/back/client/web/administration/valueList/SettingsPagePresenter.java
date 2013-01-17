package com.gsr.myschool.back.client.web.administration.valueList;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.web.administration.AdministrationPresenter;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Proxy;

public class SettingsPagePresenter extends Presenter<SettingsPagePresenter.MyView, SettingsPagePresenter.MyProxy> {
    @Inject
    public SettingsPagePresenter(final EventBus eventBus, final MyView view, final MyProxy proxy) {
        super(eventBus, view, proxy, AdministrationPresenter.TYPE_SetMainContent);
        System.out.println("construction du presenter valueList");
    }

    public interface MyView extends View {
    }

    @ProxyStandard
    @NameToken(value = NameTokens.settings)
    public interface MyProxy extends Proxy<SettingsPagePresenter> {
    }
}
