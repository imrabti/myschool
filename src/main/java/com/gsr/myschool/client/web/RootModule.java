package com.gsr.myschool.client.web;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gsr.myschool.client.web.application.ApplicationModule;
import com.gsr.myschool.client.web.welcome.WelcomeModule;
import com.gsr.myschool.client.web.widget.message.MessageModule;

public class RootModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new ApplicationModule());
        install(new WelcomeModule());
        install(new MessageModule());

        bindPresenter(RootPresenter.class, RootPresenter.MyView.class, RootView.class,
                RootPresenter.MyProxy.class);
    }
}
