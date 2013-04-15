package com.gsr.myschool.back.client.web.application.widget.sider;

import com.github.gwtbootstrap.client.ui.NavHeader;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.security.SecurityUtils;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

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
    NavLink confirmation;
    @UiField
    NavLink admission;
    @UiField
    NavLink userPortal;
    @UiField
    NavLink userGsr;
    @UiField
    NavLink valueList;
    @UiField
    NavLink generalSettings;
    @UiField
    NavLink session;
    @UiField
    NavLink affectation;
    @UiField
    NavHeader settingsHeader;

    private final SecurityUtils securityUtils;

    private MenuItem currentMenu;

    @Inject
    public MenuView(final Binder uiBinder, final SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;

        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setupMenuSecurity() {
        preInscription.setVisible(securityUtils.hasAuthority(GlobalParameters.ROLE_ADMIN));
        reception.setVisible(securityUtils.hasAuthority(GlobalParameters.ROLE_ADMIN, GlobalParameters.ROLE_OPERATOR));
        validation.setVisible(securityUtils.hasAuthority(GlobalParameters.ROLE_ADMIN));
        confirmation.setVisible(securityUtils.hasAuthority(GlobalParameters.ROLE_ADMIN, GlobalParameters.ROLE_OPERATOR));
        admission.setVisible(securityUtils.hasAuthority(GlobalParameters.ROLE_ADMIN, GlobalParameters.ROLE_OPERATOR));
        userPortal.setVisible(securityUtils.hasAuthority(GlobalParameters.ROLE_ADMIN));
        userGsr.setVisible(securityUtils.hasAuthority(GlobalParameters.ROLE_ADMIN));
        valueList.setVisible(securityUtils.hasAuthority(GlobalParameters.ROLE_ADMIN));
        generalSettings.setVisible(securityUtils.hasAuthority(GlobalParameters.ROLE_ADMIN));
        session.setVisible(securityUtils.hasAuthority(GlobalParameters.ROLE_ADMIN));
        affectation.setVisible(securityUtils.hasAuthority(GlobalParameters.ROLE_ADMIN));
        settingsHeader.setVisible(securityUtils.hasAuthority(GlobalParameters.ROLE_ADMIN));
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
            case CONFIRMATION_TEST:
                confirmation.setActive(true);
                break;
            case ADMISSION:
                admission.setActive(true);
                break;
            case USERS_PORTAL:
                userPortal.setActive(true);
                break;
            case USERS_GSR:
                userGsr.setActive(true);
                break;
            case VALUE_LIST:
                valueList.setActive(true);
                break;
            case SESSION:
                session.setActive(true);
                break;
            case AFFECTATION:
                affectation.setActive(true);
                break;
        }
    }

    @UiHandler("preInscription")
    void onInscriptionClicked(ClickEvent event) {
        clearActive();
        preInscription.setActive(true);
        currentMenu = MenuItem.PRE_INSCRIPTION;
        getUiHandlers().onMenuChanged(currentMenu);
    }

    @UiHandler("reception")
    void onConvocationClicked(ClickEvent event) {
        clearActive();
        reception.setActive(true);
        currentMenu = MenuItem.RECEPTION;
        getUiHandlers().onMenuChanged(currentMenu);
    }

    @UiHandler("validation")
    void onInboxClicked(ClickEvent event) {
        clearActive();
        validation.setActive(true);
        currentMenu = MenuItem.VALIDATION;
        getUiHandlers().onMenuChanged(currentMenu);
    }

    @UiHandler("confirmation")
    void onConfirmationClicked(ClickEvent event) {
        clearActive();
        confirmation.setActive(true);
        currentMenu = MenuItem.CONFIRMATION_TEST;
        getUiHandlers().onMenuChanged(currentMenu);
    }

    @UiHandler("admission")
    void onAdmissionClicked(ClickEvent event) {
        clearActive();
        admission.setActive(true);
        currentMenu = MenuItem.ADMISSION;
        getUiHandlers().onMenuChanged(currentMenu);
    }

    @UiHandler("userPortal")
    void onUserPortalClicked(ClickEvent event) {
        clearActive();
        userPortal.setActive(true);
        currentMenu = MenuItem.USERS_PORTAL;
        getUiHandlers().onMenuChanged(currentMenu);
    }

    @UiHandler("userGsr")
    void onUserGsrClicked(ClickEvent event) {
        clearActive();
        userGsr.setActive(true);
        currentMenu = MenuItem.USERS_GSR;
        getUiHandlers().onMenuChanged(currentMenu);
    }

    @UiHandler("valueList")
    void onValueListClicked(ClickEvent event) {
        clearActive();
        valueList.setActive(true);
        currentMenu = MenuItem.VALUE_LIST;
        getUiHandlers().onMenuChanged(currentMenu);
    }

    @UiHandler("generalSettings")
    void onGeneralSettingsClicked(ClickEvent event) {
        clearActive();
        generalSettings.setActive(true);
        currentMenu = MenuItem.GENERAL_SETTINGS;
        getUiHandlers().onMenuChanged(currentMenu);
    }

    @UiHandler("session")
    void onGeneralSessionClicked(ClickEvent event) {
        clearActive();
        session.setActive(true);
        currentMenu = MenuItem.SESSION;
        getUiHandlers().onMenuChanged(currentMenu);
    }

    @UiHandler("affectation")
    void onAffectationClicked(ClickEvent event) {
        clearActive();
        affectation.setActive(true);
        currentMenu = MenuItem.AFFECTATION;
        getUiHandlers().onMenuChanged(currentMenu);
    }

    private void clearActive() {
        preInscription.setActive(false);
        reception.setActive(false);
        validation.setActive(false);
        confirmation.setActive(false);
        admission.setActive(false);
        valueList.setActive(false);
        userGsr.setActive(false);
        userPortal.setActive(false);
        generalSettings.setActive(false);
        session.setActive(false);
        affectation.setActive(false);
    }
}
