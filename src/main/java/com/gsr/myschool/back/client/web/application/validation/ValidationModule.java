package com.gsr.myschool.back.client.web.application.validation;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.gsr.myschool.back.client.web.application.validation.popup.PiecesJustifcatifView;
import com.gsr.myschool.back.client.web.application.validation.popup.PiecesJustificatifPresenter;
import com.gsr.myschool.back.client.web.application.validation.popup.PiecesJustificatifUiHandlers;
import com.gsr.myschool.back.client.web.application.validation.popup.ui.PieceJustificatifEditorFactory;
import com.gsr.myschool.back.client.web.application.validation.renderer.ValidationActionCellFactory;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ValidationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(ValidationUiHandlers.class).to(ValidationPresenter.class);
        bind(PiecesJustificatifUiHandlers.class).to(PiecesJustificatifPresenter.class);

        bindPresenter(ValidationPresenter.class, ValidationPresenter.MyView.class, ValidationView.class,
                ValidationPresenter.MyProxy.class);

        bindSingletonPresenterWidget(PiecesJustificatifPresenter.class, PiecesJustificatifPresenter.MyView.class,
                PiecesJustifcatifView.class);

        install(new GinFactoryModuleBuilder().build(ValidationActionCellFactory.class));
        install(new GinFactoryModuleBuilder().build(PieceJustificatifEditorFactory.class));
    }
}
