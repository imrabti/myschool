package com.gsr.myschool.back.client.web.application.valueList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;

/**
 * Created with IntelliJ IDEA.
 * SuperUser: ridick
 * Date: 26/12/12
 * Time: 13:59
 * To change this template use File | Settings | File Templates.
 */
public class AddLovView extends ViewWithUiHandlers<AddLovUiHandlers> implements AddLovPresenter.MyView {

    @UiField
    ListBox parent;
    @UiField
    ListBox defLov;
    @UiField
    TextBox value;
    @UiField
    TextBox label;

    @Inject
    public AddLovView(final Binder uiBinder, final UiHandlersStrategy<AddLovUiHandlers> uiHandlers) {
        super(uiHandlers);
        initWidget(uiBinder.createAndBindUi(this));
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

    public interface Binder extends UiBinder<Widget, AddLovView> {
    }
}
