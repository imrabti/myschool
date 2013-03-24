package com.gsr.myschool.back.client.web.application.session;

import com.google.inject.TypeLiteral;
import com.gsr.myschool.back.client.web.application.session.popup.EditNiveauEtudeTimePresenter;
import com.gsr.myschool.back.client.web.application.session.popup.EditNiveauEtudeTimeUiHandlers;
import com.gsr.myschool.back.client.web.application.session.popup.EditNiveauEtudeTimeView;
import com.gsr.myschool.back.client.web.application.session.popup.EditSessionPresenter;
import com.gsr.myschool.back.client.web.application.session.popup.EditSessionUiHandlers;
import com.gsr.myschool.back.client.web.application.session.popup.EditSessionView;
import com.gsr.myschool.common.client.mvp.uihandler.SetterUiHandlersStrategy;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SessionModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<UiHandlersStrategy<SessionUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<SessionUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<EditSessionUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<EditSessionUiHandlers>>() {});
        bind(new TypeLiteral<UiHandlersStrategy<EditNiveauEtudeTimeUiHandlers>>() {})
                .to(new TypeLiteral<SetterUiHandlersStrategy<EditNiveauEtudeTimeUiHandlers>>() {});

        bindPresenter(SessionPresenter.class, SessionPresenter.MyView.class, SessionView.class,
                SessionPresenter.MyProxy.class);

        bindSingletonPresenterWidget(EditSessionPresenter.class, EditSessionPresenter.MyView.class,
                EditSessionView.class);
        bindSingletonPresenterWidget(EditNiveauEtudeTimePresenter.class, EditNiveauEtudeTimePresenter.MyView.class,
                EditNiveauEtudeTimeView.class);
    }
}
