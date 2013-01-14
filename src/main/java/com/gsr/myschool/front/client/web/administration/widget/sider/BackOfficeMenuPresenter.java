package com.gsr.myschool.front.client.web.administration.widget.sider;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class BackOfficeMenuPresenter extends PresenterWidget<BackOfficeMenuPresenter.MyView> implements BackOfficeMenuUiHandlers {
    public interface MyView extends View, HasUiHandlers<BackOfficeMenuUiHandlers> {
        void setSelectedMenu(BackOfficeMenuItem currentMenu);
    }

    private final PlaceManager placeManager;

    @Inject
    public BackOfficeMenuPresenter(EventBus eventBus, MyView view, final PlaceManager placeManager) {
        super(eventBus, view);

        this.placeManager = placeManager;

        getView().setUiHandlers(this);
    }

    @Override
    public void onMenuChanged(BackOfficeMenuItem selectedMenu) {
        switch (selectedMenu) {
            case PRE_INSCRIPTION:
                placeManager.revealPlace(new PlaceRequest(NameTokens.getPreInscriptions()));
                break;
            case RECEPTION:
                placeManager.revealPlace(new PlaceRequest(NameTokens.getReception()));
                break;
            case VALIDATION:
                placeManager.revealPlace(new PlaceRequest(NameTokens.getValidation()));
                break;
        }
    }

    @Override
    protected void onReveal() {
        PlaceRequest currentPlace =  placeManager.getCurrentPlaceRequest();
		BackOfficeMenuItem currentMenu = BackOfficeMenuItem.PRE_INSCRIPTION;

        if (currentPlace.matchesNameToken(NameTokens.getPreInscriptions())) {
            currentMenu = BackOfficeMenuItem.PRE_INSCRIPTION;
        } else if (currentPlace.matchesNameToken(NameTokens.getReception())) {
            currentMenu = BackOfficeMenuItem.RECEPTION;
        } else if (currentPlace.matchesNameToken(NameTokens.getValidation())) {
            currentMenu = BackOfficeMenuItem.VALIDATION;
        }

        getView().setSelectedMenu(currentMenu);
    }
}
