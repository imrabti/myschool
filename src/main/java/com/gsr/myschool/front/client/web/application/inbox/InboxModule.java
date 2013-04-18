package com.gsr.myschool.front.client.web.application.inbox;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.gsr.myschool.front.client.web.application.inbox.popup.InboxDetailsPresenter;
import com.gsr.myschool.front.client.web.application.inbox.popup.InboxDetailsUiHandlers;
import com.gsr.myschool.front.client.web.application.inbox.popup.InboxDetailsView;
import com.gsr.myschool.front.client.web.application.inbox.renderer.InboxCellFactory;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class InboxModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(InboxUiHandlers.class).to(InboxPresenter.class);
        bind(InboxDetailsUiHandlers.class).to(InboxDetailsPresenter.class);

        bindPresenter(InboxPresenter.class, InboxPresenter.MyView.class, InboxView.class,
                InboxPresenter.MyProxy.class);

        bindPresenterWidget(InboxPresenter.class, InboxDetailsPresenter.MyView.class, InboxDetailsView.class);

        install(new GinFactoryModuleBuilder().build(InboxCellFactory.class));
    }
}
