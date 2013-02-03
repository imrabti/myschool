package com.gsr.myschool.front.client.web.application.inbox;

import com.google.inject.TypeLiteral;
import com.gsr.myschool.common.client.mvp.uihandler.SetterUiHandlersStrategy;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class InboxModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<UiHandlersStrategy<InboxUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<InboxUiHandlers>>() {
                });

        bindPresenter(InboxPresenter.class, InboxPresenter.MyView.class, InboxView.class,
                InboxPresenter.MyProxy.class);
    }
}
