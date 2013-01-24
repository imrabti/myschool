package com.gsr.myschool.back.client.web.application.preinscription.popup;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedPopupView;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

public class PreInscriptionDetailsPresenter extends PresenterWidget<PreInscriptionDetailsPresenter.MyView>
        implements PreInscriptionDetailsUiHandlers {
    public interface MyView extends ValidatedPopupView, HasUiHandlers<PreInscriptionDetailsUiHandlers> {
    }

    public interface ParentEditorDriver extends SimpleBeanEditorDriver<DossierProxy, Editor<DossierProxy>> {
    }

    public interface StudentEditorDriver extends SimpleBeanEditorDriver<DossierProxy, Editor<DossierProxy>> {
    }

    public interface LevelEditorDriver extends SimpleBeanEditorDriver<DossierProxy, Editor<DossierProxy>> {
    }

    private final ParentEditorDriver parentEditorDriver;
    private final StudentEditorDriver studentEditorDriver;
    private final LevelEditorDriver levelEditorDriver;

    @Inject
    public PreInscriptionDetailsPresenter(final EventBus eventBus, final MyView view,
            final ParentEditorDriver parentEditorDriver, final StudentEditorDriver studentEditorDriver,
            final LevelEditorDriver levelEditorDriver) {
        super(eventBus, view);

        this.parentEditorDriver = parentEditorDriver;
        this.studentEditorDriver = studentEditorDriver;
        this.levelEditorDriver = levelEditorDriver;

        getView().setUiHandlers(this);
    }

    public void editDrivers(DossierProxy dossier) {
    }
}
