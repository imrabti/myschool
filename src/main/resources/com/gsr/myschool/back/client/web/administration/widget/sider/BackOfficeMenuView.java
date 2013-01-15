package com.gsr.myschool.back.client.web.administration.widget.sider;

import com.github.gwtbootstrap.client.ui.NavLink;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;

public class BackOfficeMenuView extends ViewWithUiHandlers<BackOfficeMenuUiHandlers> implements BackOfficeMenuPresenter.MyView {
    public interface Binder extends UiBinder<Widget, BackOfficeMenuView> {
    }

    @UiField
    NavLink preInscription;
    @UiField
    NavLink reception;
    @UiField
	NavLink validation;

    private BackOfficeMenuItem currentMenu;

    @Inject
    public BackOfficeMenuView(final Binder uiBinder, final UiHandlersStrategy<BackOfficeMenuUiHandlers> uiHandlersStrategy) {
        super(uiHandlersStrategy);
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setSelectedMenu(BackOfficeMenuItem currentMenu) {
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
        }
    }

    @UiHandler("preInscription")
    void onInscriptionClicked(ClickEvent event) {
        if (currentMenu != BackOfficeMenuItem.PRE_INSCRIPTION) {
            clearActive();
            preInscription.setActive(true);
            currentMenu = BackOfficeMenuItem.PRE_INSCRIPTION;
            getUiHandlers().onMenuChanged(currentMenu);
        }
    }

    @UiHandler("reception")
    void onConvocationClicked(ClickEvent event) {
        if (currentMenu != BackOfficeMenuItem.RECEPTION) {
            clearActive();
            reception.setActive(true);
            currentMenu = BackOfficeMenuItem.RECEPTION;
            getUiHandlers().onMenuChanged(currentMenu);
        }
    }

    @UiHandler("validation")
    void onInboxClicked(ClickEvent event) {
        if (currentMenu != BackOfficeMenuItem.VALIDATION) {
            clearActive();
            validation.setActive(true);
            currentMenu = BackOfficeMenuItem.VALIDATION;
            getUiHandlers().onMenuChanged(currentMenu);
        }
    }

    private void clearActive() {
        preInscription.setActive(false);
        reception.setActive(false);
        validation.setActive(false);
    }
}
