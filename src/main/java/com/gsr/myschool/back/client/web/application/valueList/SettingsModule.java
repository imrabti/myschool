package com.gsr.myschool.back.client.web.application.valueList;

import com.google.inject.TypeLiteral;
import com.gsr.myschool.common.client.mvp.uihandler.SetterUiHandlersStrategy;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SettingsModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<UiHandlersStrategy<AddDefLovUiHandlers>>() {
        })
                .to(new TypeLiteral<SetterUiHandlersStrategy<AddDefLovUiHandlers>>() {
                });

        bind(new TypeLiteral<UiHandlersStrategy<ListDefLovUiHandlers>>() {
        })
                .to(new TypeLiteral<SetterUiHandlersStrategy<ListDefLovUiHandlers>>() {
                });

        bind(new TypeLiteral<UiHandlersStrategy<AddLovUiHandlers>>() {
        })
                .to(new TypeLiteral<SetterUiHandlersStrategy<AddLovUiHandlers>>() {
                });

        bind(new TypeLiteral<UiHandlersStrategy<ListLOVUiHandlers>>() {
        })
                .to(new TypeLiteral<SetterUiHandlersStrategy<ListLOVUiHandlers>>() {
                });

        bindPresenter(SettingsPagePresenter.class, SettingsPagePresenter.MyView.class, SettingsPageView.class,
                SettingsPagePresenter.MyProxy.class);

        bindPresenter(AddDefLovPresenter.class, AddDefLovPresenter.MyView.class, AddDefLovView.class,
                AddDefLovPresenter.MyProxy.class);

        bindPresenter(ListDefLovPresenter.class, ListDefLovPresenter.MyView.class, ListDefLovView.class,
                ListDefLovPresenter.MyProxy.class);

        bindPresenter(AddLovPresenter.class, AddLovPresenter.MyView.class, AddLovView.class,
                AddLovPresenter.MyProxy.class);

        bindPresenter(ListLOVPresenter.class, ListLOVPresenter.MyView.class, ListLOVView.class,
                ListLOVPresenter.MyProxy.class);
    }
}
