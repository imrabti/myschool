package com.gsr.myschool.back.client.web.administration.valueList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.gsr.myschool.back.client.request.proxy.DefLovProxy;
import com.gsr.myschool.back.client.request.proxy.LOVProxy;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;

import java.util.List;


public class AddDefLovView extends ViewWithUiHandlers<AddDefLovUiHandlers> implements AddDefLovPresenter.MyView {

    @UiField
    TextBox name;
    @UiField(provided = true)
    ValueListBox<LOVProxy> regex;
    @UiField(provided = true)
    ValueListBox<DefLovProxy> parent;
    @UiField
    CheckBox systemDefLov;
    @UiField
    Button save;

    @Inject
    public AddDefLovView(final Binder uiBinder, final UiHandlersStrategy<AddDefLovUiHandlers> uiHandlers) {
        super(uiHandlers);
        parent = new ValueListBox<DefLovProxy>(new DefLovListRenderer());
        regex = new ValueListBox<LOVProxy>(new LOVListRenderer());
        initWidget(uiBinder.createAndBindUi(this));

    }

    @Override
    public void fillParentList(List<DefLovProxy> defLovProxies) {
        parent.setValue(null);
        parent.setAcceptableValues(defLovProxies);
    }

    @Override
    public void fillRegexList(List<LOVProxy> regexes) {
        regex.setValue(null);
        regex.setAcceptableValues(regexes);
    }

    @Override
    public TextBox getName() {
        return name;
    }

    @Override
    public ValueListBox<LOVProxy> getRegex() {
        return regex;
    }

    @Override
    public ValueListBox<DefLovProxy> getParent() {
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