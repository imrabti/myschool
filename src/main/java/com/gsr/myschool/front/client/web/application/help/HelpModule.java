package com.gsr.myschool.front.client.web.application.help;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class HelpModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(HelpPresenter.class, HelpPresenter.MyView.class, HelpView.class, HelpPresenter.MyProxy.class);
    }
}
