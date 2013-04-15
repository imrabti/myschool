package com.gsr.myschool.front.client.web.application.widget.sider;

import com.github.gwtbootstrap.client.ui.Badge;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.NavWidget;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class MenuView extends ViewWithUiHandlers<MenuUiHandlers> implements MenuPresenter.MyView {
    public interface Binder extends UiBinder<Widget, MenuView> {
    }

    @UiField
    NavLink inscription;
    @UiField
    NavLink convocation;
    @UiField
    NavLink help;
    @UiField
    NavWidget inbox;
    @UiField
    Badge messagesCount;

    private MenuItem currentMenu;

    @Inject
    public MenuView(final Binder uiBinder) {
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
            case HELP:
                help.setActive(true);
                break;
        }
    }

    public void updateMessageCount(Integer count) {
        if (count == 0) {
            messagesCount.setVisible(false);
        } else {
            messagesCount.setVisible(true);
            messagesCount.setText(count.toString());
        }
    }

    @UiHandler("inscription")
    void onInscriptionClicked(ClickEvent event) {
        clearActive();
        inscription.setActive(true);
        currentMenu = MenuItem.INSCRIPTION;
        getUiHandlers().onMenuChanged(currentMenu);
    }

    @UiHandler("convocation")
    void onConvocationClicked(ClickEvent event) {
        clearActive();
        convocation.setActive(true);
        currentMenu = MenuItem.CONVOCATION;
        getUiHandlers().onMenuChanged(currentMenu);
    }

    @UiHandler("inbox")
    void onInboxClicked(ClickEvent event) {
        clearActive();
        inbox.setActive(true);
        currentMenu = MenuItem.INBOX;
        getUiHandlers().onMenuChanged(currentMenu);
    }

    @UiHandler("help")
    void onHelpClicked(ClickEvent event) {
        clearActive();
        help.setActive(true);
        currentMenu = MenuItem.HELP;
        getUiHandlers().onMenuChanged(currentMenu);
    }

    private void clearActive() {
        inscription.setActive(false);
        convocation.setActive(false);
        inbox.setActive(false);
        help.setActive(false);
    }
}
