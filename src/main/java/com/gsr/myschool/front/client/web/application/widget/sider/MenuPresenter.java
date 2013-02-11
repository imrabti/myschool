package com.gsr.myschool.front.client.web.application.widget.sider;

import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gsr.myschool.front.client.request.FrontRequestFactory;
import com.gsr.myschool.front.client.security.CurrentUserProvider;
import com.gsr.myschool.front.client.web.application.inbox.event.InboxStatusChangedEvent;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class MenuPresenter extends PresenterWidget<MenuPresenter.MyView> implements MenuUiHandlers,
        InboxStatusChangedEvent.InboxStatusChangedHandler {
    public interface MyView extends View, HasUiHandlers<MenuUiHandlers> {
        void setSelectedMenu(MenuItem currentMenu);

        void updateMessageCount(Integer count);
    }

    private final PlaceManager placeManager;
    private final FrontRequestFactory requestFactory;
    private final CurrentUserProvider currentUserProvider;

    private Timer refreshTimer = new Timer() {
        @Override
        public void run() {
            getMessagesCount();
        }
    };

    @Inject
    public MenuPresenter(EventBus eventBus, MyView view, final PlaceManager placeManager,
                         final FrontRequestFactory requestFactory,
                         final CurrentUserProvider currentUserProvider) {
        super(eventBus, view);

        this.placeManager = placeManager;
        this.requestFactory = requestFactory;
        this.currentUserProvider = currentUserProvider;

        initTimer();

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
    public void onInboxStatusChanged(InboxStatusChangedEvent event) {
        getMessagesCount();
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(InboxStatusChangedEvent.TYPE, this);
    }


    @Override
    protected void onReveal() {
        PlaceRequest currentPlace = placeManager.getCurrentPlaceRequest();
        MenuItem currentMenu = MenuItem.INSCRIPTION;

        if (currentPlace.matchesNameToken(NameTokens.getInscription())) {
            currentMenu = MenuItem.INSCRIPTION;
        } else if (currentPlace.matchesNameToken(NameTokens.getConvocation())) {
            currentMenu = MenuItem.CONVOCATION;
        } else if (currentPlace.matchesNameToken(NameTokens.getInbox())) {
            currentMenu = MenuItem.INBOX;
        }

        getView().setSelectedMenu(currentMenu);

        getMessagesCount();
    }

    private void getMessagesCount() {
        requestFactory.inboxService().countAllUnreadInboxMessages(currentUserProvider.get().getId())
                .fire(new ReceiverImpl<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
                        getView().updateMessageCount(integer);
                    }
                });
    }

    private void initTimer() {
        refreshTimer.scheduleRepeating(GlobalParameters.REFRESH_PERIODE);
    }
}
