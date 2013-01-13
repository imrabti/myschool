package com.gsr.myschool.front.client.web.application.widget.sider;

import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.NavWidget;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;

public class MenuView extends ViewWithUiHandlers<MenuUiHandlers> implements MenuPresenter.MyView {
    public interface Binder extends UiBinder<Widget, MenuView> {
    }

    @UiField
    NavLink inscription;
    @UiField
    NavLink convocation;
    @UiField
    NavWidget inbox;

    private MenuItem currentMenu;

    @Inject
    public MenuView(final Binder uiBinder, final UiHandlersStrategy<MenuUiHandlers> uiHandlersStrategy) {
        super(uiHandlersStrategy);
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setSelectedMenu(MenuItem currentMenu) {
        this.currentMenu = currentMenu;
        clearActive();

        switch (currentMenu) {
            case INSCRIPTION:
                inscription.setActive(true);
                break;
            case CONVOCATION:
                convocation.setActive(true);
                break;
            case INBOX:
                inbox.setActive(true);
                break;
        }
    }

    @UiHandler("inscription")
    void onInscriptionClicked(ClickEvent event) {
        if (currentMenu != MenuItem.INSCRIPTION) {
            clearActive();
            inscription.setActive(true);
            currentMenu = MenuItem.INSCRIPTION;
            getUiHandlers().onMenuChanged(currentMenu);
        }
    }

    @UiHandler("convocation")
    void onConvocationClicked(ClickEvent event) {
        if (currentMenu != MenuItem.CONVOCATION) {
            clearActive();
            convocation.setActive(true);
            currentMenu = MenuItem.CONVOCATION;
            getUiHandlers().onMenuChanged(currentMenu);
        }
    }

    @UiHandler("inbox")
    void onInboxClicked(ClickEvent event) {
        if (currentMenu != MenuItem.INBOX) {
            clearActive();
            inbox.setActive(true);
            currentMenu = MenuItem.INBOX;
            getUiHandlers().onMenuChanged(currentMenu);
        }
    }

    private void clearActive() {
        inscription.setActive(false);
        convocation.setActive(false);
        inbox.setActive(false);
    }
}
