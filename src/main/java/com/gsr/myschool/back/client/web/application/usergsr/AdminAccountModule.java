package com.gsr.myschool.back.client.web.application.usergsr;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.TypeLiteral;
import com.gsr.myschool.back.client.web.application.usergsr.popup.AdminUserAccountEditPresenter;
import com.gsr.myschool.back.client.web.application.usergsr.popup.AdminUserAccountEditUiHandlers;
import com.gsr.myschool.back.client.web.application.usergsr.popup.AdminUserAccountEditView;
import com.gsr.myschool.back.client.web.application.usergsr.renderer.AdminUserActionCellFactory;
import com.gsr.myschool.common.client.mvp.uihandler.SetterUiHandlersStrategy;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class AdminAccountModule  extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<UiHandlersStrategy<AdminUserAccountUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<AdminUserAccountUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<AdminUserAccountEditUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<AdminUserAccountEditUiHandlers>>() {});

        bindSingletonPresenterWidget(AdminUserAccountEditPresenter.class, AdminUserAccountEditPresenter.MyView.class,
                AdminUserAccountEditView.class);

        bindPresenter(AdminUserAccountPresenter.class, AdminUserAccountPresenter.MyView.class, AdminUserAccountView.class,
                AdminUserAccountPresenter.MyProxy.class);

        install(new GinFactoryModuleBuilder().build(AdminUserActionCellFactory.class));
    }
}
