package com.gsr.myschool.back.client.web.application.settings.popup;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gsr.myschool.common.client.util.EditorView;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

public class AddNiveauEtudePresenter extends PresenterWidget<AddNiveauEtudePresenter.MyView>
        implements AddNiveauEtudeUiHandlers  {
    public interface MyView extends PopupView, HasUiHandlers<AddNiveauEtudeUiHandlers>, EditorView<NiveauEtudeProxy> {
    }

    @Inject
    public AddNiveauEtudePresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);

        getView().setUiHandlers(this);
    }

    @Override
    public void saveNiveauEtude(NiveauEtudeProxy niveauEtude) {
    }
}
