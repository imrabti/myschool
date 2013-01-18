package com.gsr.myschool.back.client.web.application.valueList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.gsr.myschool.back.client.request.proxy.ValueListProxy;
import com.gsr.myschool.back.client.request.proxy.ValueTypeProxy;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;

import java.util.List;


public class AddDefLovView extends ViewWithUiHandlers<AddDefLovUiHandlers> implements AddDefLovPresenter.MyView {

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
    public AddDefLovView(final Binder uiBinder, final UiHandlersStrategy<AddDefLovUiHandlers> uiHandlers) {
        super(uiHandlers);
        parent = new ValueListBox<ValueTypeProxy>(new DefLovListRenderer());
        regex = new ValueListBox<ValueListProxy>(new LOVListRenderer());
        initWidget(uiBinder.createAndBindUi(this));

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
    public void onSaveClick(ClickEvent event) {
        getUiHandlers().processDefLov();
    }

    interface Binder extends UiBinder<Widget, AddDefLovView> {
    }
}