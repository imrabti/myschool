package com.gsr.myschool.back.client.web.application.valueList.popup;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.proxy.ValueListProxy;
import com.gsr.myschool.back.client.request.proxy.ValueTypeProxy;
import com.gsr.myschool.back.client.web.application.valueList.renderer.ValueTypeRenderer;
import com.gsr.myschool.back.client.web.application.valueList.renderer.ValueListRenderer;
import com.gsr.myschool.common.client.mvp.ValidatedPopupViewImplWithUiHandlers;
import com.gsr.myschool.common.client.mvp.ValidationErrorPopup;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.widget.ModalHeader;

import java.util.List;


public class AddValueTypeView extends ValidatedPopupViewImplWithUiHandlers<AddValueTypeUiHandlers> implements AddValueTypePresenter.MyView {
    interface Binder extends UiBinder<PopupPanel, AddValueTypeView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;
    @UiField
    TextBox name;
    @UiField(provided = true)
    ValueListBox<ValueListProxy> regex;
    @UiField(provided = true)
    ValueListBox<ValueTypeProxy> parent;
    @UiField
    CheckBox systemDefLov;
    @UiField
    Button save;

    @Inject
    public AddValueTypeView(final EventBus eventBus, final Binder uiBinder,
                            final UiHandlersStrategy<AddValueTypeUiHandlers> uiHandlers,
                            final ValidationErrorPopup errorPopup,
                            final ModalHeader modalHeader) {
        super(eventBus, errorPopup, uiHandlers);

        this.modalHeader = modalHeader;
        this.parent = new ValueListBox<ValueTypeProxy>(new ValueTypeRenderer());
        this.regex = new ValueListBox<ValueListProxy>(new ValueListRenderer());

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

    @Override
    public void fillParentList(List<ValueTypeProxy> defLovProxies) {
        parent.setValue(null);
        parent.setAcceptableValues(defLovProxies);
    }

    @Override
    public void fillRegexList(List<ValueListProxy> regexes) {
        regex.setValue(null);
        regex.setAcceptableValues(regexes);
    }

    @Override
    public TextBox getName() {
        return name;
    }

    @Override
    public ValueListBox<ValueListProxy> getRegex() {
        return regex;
    }

    @Override
    public ValueListBox<ValueTypeProxy> getParent() {
        return parent;
    }

    @Override
    public CheckBox getSystemDefLov() {
        return systemDefLov;
    }

    @UiHandler("save")
    void onSaveClick(ClickEvent event) {
        getUiHandlers().processDefLov();
    }
}
