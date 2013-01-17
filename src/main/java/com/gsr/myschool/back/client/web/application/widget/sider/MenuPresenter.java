package com.gsr.myschool.back.client.web.application.widget.sider;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class MenuPresenter extends PresenterWidget<MenuPresenter.MyView> implements MenuUiHandlers {
    public interface MyView extends View, HasUiHandlers<MenuUiHandlers> {
        void setSelectedMenu(MenuItem currentMenu);
    }

    private final PlaceManager placeManager;

    @Inject
    public MenuPresenter(EventBus eventBus, MyView view, final PlaceManager placeManager) {
        super(eventBus, view);

        this.placeManager = placeManager;

        getView().setUiHandlers(this);
    }

    @Override
    public void onMenuChanged(MenuItem selectedMenu) {
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
        MenuItem currentMenu = MenuItem.PRE_INSCRIPTION;

        if (currentPlace.matchesNameToken(NameTokens.getPreInscriptions())) {
            currentMenu = MenuItem.PRE_INSCRIPTION;
        } else if (currentPlace.matchesNameToken(NameTokens.getReception())) {
            currentMenu = MenuItem.RECEPTION;
        } else if (currentPlace.matchesNameToken(NameTokens.getValidation())) {
            currentMenu = MenuItem.VALIDATION;
        }

        getView().setSelectedMenu(currentMenu);
    }
}