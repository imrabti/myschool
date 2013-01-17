package com.gsr.myschool.common.client;

import com.google.inject.Singleton;
import com.gsr.myschool.common.client.resource.SharedResources;
import com.gsr.myschool.common.client.widget.messages.MessageModule;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class CommonModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new MessageModule());

        bind(SharedResources.class).in(Singleton.class);
    }
}
