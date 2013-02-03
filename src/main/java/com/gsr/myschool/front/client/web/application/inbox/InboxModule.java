package com.gsr.myschool.front.client.web.application.inbox;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.TypeLiteral;
import com.gsr.myschool.common.client.mvp.uihandler.SetterUiHandlersStrategy;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.front.client.web.application.inbox.popup.InboxDetailsPresenter;
import com.gsr.myschool.front.client.web.application.inbox.popup.InboxDetailsView;
import com.gsr.myschool.front.client.web.application.inbox.renderer.InboxCellFactory;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class InboxModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<UiHandlersStrategy<InboxUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<InboxUiHandlers>>() {
                });

        bindPresenter(InboxPresenter.class, InboxPresenter.MyView.class, InboxView.class,
                InboxPresenter.MyProxy.class);

        bindPresenterWidget(InboxPresenter.class, InboxDetailsPresenter.MyView.class, InboxDetailsView.class);

        install(new GinFactoryModuleBuilder().build(InboxCellFactory.class));
    }
}
