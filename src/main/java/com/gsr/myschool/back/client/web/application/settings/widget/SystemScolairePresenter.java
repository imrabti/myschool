package com.gsr.myschool.back.client.web.application.settings.widget;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.web.application.settings.event.SystemScolaireChangedEvent;
import com.gsr.myschool.back.client.web.application.settings.popup.NiveauEtudeInfosPresenter;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gsr.myschool.back.client.web.application.settings.widget.SystemScolairePresenter.MyView;

public class SystemScolairePresenter extends PresenterWidget<MyView> implements SystemScolaireUiHandlers,
        SystemScolaireChangedEvent.SystemScolaireChangedHandler {
    public interface MyView extends View, HasUiHandlers<SystemScolaireUiHandlers> {
    }

    private final NiveauEtudeInfosPresenter niveauEtudeInfosPresenter;

    @Inject
    public SystemScolairePresenter(final EventBus eventBus, final MyView view,
                                   final NiveauEtudeInfosPresenter niveauEtudeInfosPresenter) {
        super(eventBus, view);

        this.niveauEtudeInfosPresenter = niveauEtudeInfosPresenter;

        getView().setUiHandlers(this);
    }

    @Override
    public void onSystemScolaireChange(SystemScolaireChangedEvent event) {
        // TODO : Rebuild the tree
    }

    @Override
    public void showDetails(NiveauEtudeProxy value) {
        niveauEtudeInfosPresenter.setCurrentNiveauEtude(value);
        addToPopupSlot(niveauEtudeInfosPresenter);
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(SystemScolaireChangedEvent.getType(), this);
    }
}
