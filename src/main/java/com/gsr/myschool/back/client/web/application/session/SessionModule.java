package com.gsr.myschool.back.client.web.application.session;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.gsr.myschool.back.client.web.application.session.popup.EditNiveauEtudeTimePresenter;
import com.gsr.myschool.back.client.web.application.session.popup.EditNiveauEtudeTimeUiHandlers;
import com.gsr.myschool.back.client.web.application.session.popup.EditNiveauEtudeTimeView;
import com.gsr.myschool.back.client.web.application.session.popup.EditSessionPresenter;
import com.gsr.myschool.back.client.web.application.session.popup.EditSessionUiHandlers;
import com.gsr.myschool.back.client.web.application.session.popup.EditSessionView;
import com.gsr.myschool.back.client.web.application.session.popup.NewNiveauEtudePresenter;
import com.gsr.myschool.back.client.web.application.session.popup.NewNiveauEtudeUiHandlers;
import com.gsr.myschool.back.client.web.application.session.popup.NewNiveauEtudeView;
import com.gsr.myschool.back.client.web.application.session.popup.ui.MatiereHoraireEditorFactory;
import com.gsr.myschool.back.client.web.application.session.renderer.AttachedNiveauEtudeTreeFactory;
import com.gsr.myschool.back.client.web.application.session.renderer.NiveauEtudeNodeCellFactory;
import com.gsr.myschool.back.client.web.application.session.renderer.SessionActionCellFactory;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SessionModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(SessionUiHandlers.class).to(SessionPresenter.class);
        bind(EditSessionUiHandlers.class).to(EditSessionPresenter.class);
        bind(EditNiveauEtudeTimeUiHandlers.class).to(EditNiveauEtudeTimePresenter.class);
        bind(NewNiveauEtudeUiHandlers.class).to(NewNiveauEtudePresenter.class);

        bindPresenter(SessionPresenter.class, SessionPresenter.MyView.class, SessionView.class,
                SessionPresenter.MyProxy.class);

        bindSingletonPresenterWidget(EditSessionPresenter.class, EditSessionPresenter.MyView.class,
                EditSessionView.class);
        bindSingletonPresenterWidget(EditNiveauEtudeTimePresenter.class, EditNiveauEtudeTimePresenter.MyView.class,
                EditNiveauEtudeTimeView.class);
        bindSingletonPresenterWidget(NewNiveauEtudePresenter.class, NewNiveauEtudePresenter.MyView.class,
                NewNiveauEtudeView.class);

        install(new GinFactoryModuleBuilder().build(SessionActionCellFactory.class));
        install(new GinFactoryModuleBuilder().build(NiveauEtudeNodeCellFactory.class));
        install(new GinFactoryModuleBuilder().build(AttachedNiveauEtudeTreeFactory.class));
        install(new GinFactoryModuleBuilder().build(MatiereHoraireEditorFactory.class));
    }
}
