package com.gsr.myschool.front.client.web.application.widget.sider;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.front.client.place.NameTokens;
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
            case INSCRIPTION:
                placeManager.revealPlace(new PlaceRequest(NameTokens.getInscription()));
                break;
            case CONVOCATION:
                placeManager.revealPlace(new PlaceRequest(NameTokens.getConvocation()));
                break;
            case INBOX:
                placeManager.revealPlace(new PlaceRequest(NameTokens.getInbox()));
                break;
        }
    }

    @Override
    protected void onReveal() {
        PlaceRequest currentPlace =  placeManager.getCurrentPlaceRequest();
        MenuItem currentMenu = MenuItem.INSCRIPTION;

        if (currentPlace.matchesNameToken(NameTokens.getInscription())) {
            currentMenu = MenuItem.INSCRIPTION;
        } else if (currentPlace.matchesNameToken(NameTokens.getConvocation())) {
            currentMenu = MenuItem.CONVOCATION;
        } else if (currentPlace.matchesNameToken(NameTokens.getInbox())) {
            currentMenu = MenuItem.INBOX;
        }

        getView().setSelectedMenu(currentMenu);
    }
}
