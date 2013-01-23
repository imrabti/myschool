package com.gsr.myschool.back.client.web.application.valueList.popup;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedPopupViewImplWithUiHandlers;
import com.gsr.myschool.common.client.mvp.ValidationErrorPopup;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.widget.ModalHeader;

/**
 * Created with IntelliJ IDEA.
 * SuperUser: ridick
 * Date: 26/12/12
 * Time: 13:59
 * To change this template use File | Settings | File Templates.
 */
public class AddLovView extends ValidatedPopupViewImplWithUiHandlers<AddLovUiHandlers> implements AddLovPresenter.MyView {
    public interface Binder extends UiBinder<PopupPanel, AddLovView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;
    @UiField
    ListBox parent;
    @UiField
    ListBox defLov;
    @UiField
    TextBox value;
    @UiField
    TextBox label;

    @Inject
    public AddLovView(final EventBus eventBus, final Binder uiBinder,
                      final UiHandlersStrategy<AddLovUiHandlers> uiHandlers,
                      final ValidationErrorPopup errorPopup,
                      final ModalHeader modalHeader) {
        super(eventBus, errorPopup, uiHandlers);

        this.modalHeader = modalHeader;

        initWidget(uiBinder.createAndBindUi(this));

        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                hide();
            }
        });
    }

    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event) {
        hide();
    }

    public ListBox getParent() {
        return parent;
    }

    public ListBox getDefLov() {
        return defLov;
    }

    @Override
    public TextBox getValue() {
        return value;
    }

    @Override
    public TextBox getLabel() {
        return label;
    }

    @UiHandler("defLov")
    void onDefLovChanged(ChangeEvent event) {
        getUiHandlers().getParent();
    }

    @UiHandler("save")
    void onSaveClicked(ClickEvent event) {
        getUiHandlers().processLov();
    }
}
