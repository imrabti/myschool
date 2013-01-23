package com.gsr.myschool.back.client.web.application.settings;

import com.google.inject.TypeLiteral;
import com.gsr.myschool.common.client.mvp.uihandler.SetterUiHandlersStrategy;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SettingsModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<UiHandlersStrategy<SettingsUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<SettingsUiHandlers>>() {});

        bindPresenter(SettingsPresenter.class, SettingsPresenter.MyView.class, SettingsView.class,
                SettingsPresenter.MyProxy.class);
    }
}
