package com.gsr.myschool.back.client.web.application.valueList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.gsr.myschool.back.client.request.proxy.ValueTypeProxy;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
public class ListDefLovView extends ViewWithUiHandlers<ListDefLovUiHandlers> implements ListDefLovPresenter.MyView {

    public interface Binder extends UiBinder<Widget, ListDefLovView> {
    }

    @UiField
    CellTable<ValueTypeProxy> defLovTable;

    @UiField
    Button delete;

    @UiField
    Button modify;

    public SingleSelectionModel<ValueTypeProxy> defLovSelectionModel;

    @Inject
    public ListDefLovView(final Binder uiBinder, final UiHandlersStrategy<ListDefLovUiHandlers> uiHandlers){
        super(uiHandlers);
        initWidget(uiBinder.createAndBindUi(this));

    }

    @Override
    public CellTable<ValueTypeProxy> getDefLovTable() {
        return defLovTable;
    }

    @Override
    public void buildTable() {
        TextColumn<ValueTypeProxy> nameColumn = new TextColumn<ValueTypeProxy>(){
            @Override
            public String getValue(ValueTypeProxy defLovProxy){
                return defLovProxy.getName();
            }
        };

        TextColumn<ValueTypeProxy> regexColumn = new TextColumn<ValueTypeProxy>() {
            @Override
            public String getValue(ValueTypeProxy defLovProxy) {
                if(defLovProxy.getRegex() == null)
                    return "";
                return defLovProxy.getRegex().getLabel();
            }
        };

        TextColumn<ValueTypeProxy> parentColumn = new TextColumn<ValueTypeProxy>() {
            @Override
            public String getValue(ValueTypeProxy defLovProxy) {
                if(defLovProxy.getParent() != null)
                    return defLovProxy.getParent().getName();
                else
                    return "";
            }
        };

        getDefLovTable().addColumn(nameColumn, "Nom");
        getDefLovTable().addColumn(regexColumn, "Regex");
        getDefLovTable().addColumn(parentColumn, "Parent");
        this.defLovSelectionModel = new SingleSelectionModel<ValueTypeProxy>();
        defLovTable.setSelectionModel(defLovSelectionModel);
    }

    @Override
    public Button getDelete() {
        return delete;
    }

    @Override
    public Button getModify() {
        return modify;
    }

    @UiHandler("modify")
    public void onModifyClick(ClickEvent event){
        getUiHandlers().modify();
    }

    @UiHandler("delete")
    public void OnDeleteClick(ClickEvent event){
        getUiHandlers().delete();
    }
}
