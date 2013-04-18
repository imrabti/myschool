package com.gsr.myschool.back.client.web.application.usergsr;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.gsr.myschool.back.client.web.application.usergsr.popup.AdminUserAccountEditPresenter;
import com.gsr.myschool.back.client.web.application.usergsr.popup.AdminUserAccountEditUiHandlers;
import com.gsr.myschool.back.client.web.application.usergsr.popup.AdminUserAccountEditView;
import com.gsr.myschool.back.client.web.application.usergsr.renderer.AdminUserActionCellFactory;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class AdminAccountModule  extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(AdminUserAccountUiHandlers.class).to(AdminUserAccountPresenter.class);
        bind(AdminUserAccountEditUiHandlers.class).to(AdminUserAccountEditPresenter.class);

        bindSingletonPresenterWidget(AdminUserAccountEditPresenter.class, AdminUserAccountEditPresenter.MyView.class,
                AdminUserAccountEditView.class);

        bindPresenter(AdminUserAccountPresenter.class, AdminUserAccountPresenter.MyView.class, AdminUserAccountView.class,
                AdminUserAccountPresenter.MyProxy.class);

        install(new GinFactoryModuleBuilder().build(AdminUserActionCellFactory.class));
    }
}
