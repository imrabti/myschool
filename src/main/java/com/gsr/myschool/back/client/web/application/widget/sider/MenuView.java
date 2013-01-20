package com.gsr.myschool.back.client.web.application.widget.sider;

import com.github.gwtbootstrap.client.ui.NavLink;
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
    NavLink preInscription;
    @UiField
    NavLink reception;
    @UiField
    NavLink validation;
    @UiField
    NavLink genSettings;

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
            case PRE_INSCRIPTION:
                preInscription.setActive(true);
                break;
            case RECEPTION:
                reception.setActive(true);
                break;
            case VALIDATION:
                validation.setActive(true);
                break;
            case GENERAL_SETTINGS:
                genSettings.setActive(true);
                break;
        }
    }

    @UiHandler("preInscription")
    void onPreInscriptionClicked(ClickEvent event) {
        if (currentMenu != MenuItem.PRE_INSCRIPTION) {
            clearActive();
            preInscription.setActive(true);
            currentMenu = MenuItem.PRE_INSCRIPTION;
            getUiHandlers().onMenuChanged(currentMenu);
        }
    }

    @UiHandler("reception")
    void onReceptionClicked(ClickEvent event) {
        if (currentMenu != MenuItem.RECEPTION) {
            clearActive();
            reception.setActive(true);
            currentMenu = MenuItem.RECEPTION;
            getUiHandlers().onMenuChanged(currentMenu);
        }
    }

    @UiHandler("validation")
    void onValidationClicked(ClickEvent event) {
        if (currentMenu != MenuItem.VALIDATION) {
            clearActive();
            validation.setActive(true);
            currentMenu = MenuItem.VALIDATION;
            getUiHandlers().onMenuChanged(currentMenu);
        }
    }

    @UiHandler("genSettings")
    void onGenSettingsClicked(ClickEvent event) {
        if (currentMenu != MenuItem.GENERAL_SETTINGS) {
            clearActive();
            genSettings.setActive(true);
            currentMenu = MenuItem.GENERAL_SETTINGS;
            getUiHandlers().onMenuChanged(currentMenu);
        }
    }

    private void clearActive() {
        preInscription.setActive(false);
        reception.setActive(false);
        validation.setActive(false);
        genSettings.setActive(false);
    }
}
