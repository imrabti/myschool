package com.gsr.myschool.client.web.widget.message;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class MessageModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindSingletonPresenterWidget(MessagePresenter.class, MessagePresenter.MyView.class, MessageView.class);

        install(new GinFactoryModuleBuilder().build(MessageWidgetFactory.class));
    }
}
