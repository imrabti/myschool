package com.gsr.myschool.common.client;

import com.google.inject.Singleton;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.common.client.resource.SharedResources;
import com.gsr.myschool.common.client.security.HasRoleGatekeeper;
import com.gsr.myschool.common.client.security.LoggedInGatekeeper;
import com.gsr.myschool.common.client.widget.messages.MessageModule;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class CommonModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new MessageModule());

        bind(SharedResources.class).in(Singleton.class);
        bind(LoggedInGatekeeper.class).in(Singleton.class);
        bind(HasRoleGatekeeper.class).in(Singleton.class);

        requestStaticInjection(ReceiverImpl.class);
        requestStaticInjection(ValidatedReceiverImpl.class);
    }
}
