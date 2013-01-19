package com.gsr.myschool.back.client.web.application.valueList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.gsr.myschool.back.client.request.proxy.ValueListProxy;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;

/**
 * Created with IntelliJ IDEA.
 * SuperUser: houssam
 * Date: 31/12/12
 * Time: 18:07
 * To change this template use File | Settings | File Templates.
 */
public class ListLOVView extends ViewWithUiHandlers<ListLOVUiHandlers> implements ListLOVPresenter.MyView {
    public interface Binder extends UiBinder<Widget, ListLOVView> {
    }

    @UiField
    CellTable LovTable;
    @UiField
    ListBox parent;
    @UiField
    ListBox defLov;
    @UiField
    Button delete;
    @UiField
    Button modify;
    SingleSelectionModel<ValueListProxy> lovSelectionModel;

    @Inject
    public ListLOVView(final Binder uiBinder, final UiHandlersStrategy<ListLOVUiHandlers> uiHandlers) {
        super(uiHandlers);

        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public CellTable<ValueListProxy> getLovTable() {
        return LovTable;
    }

    @Override
    public ListBox getParent() {
        return parent;
    }

    @Override
    public void initTable() {
        TextColumn<ValueListProxy> valueColumn = new TextColumn<ValueListProxy>() {
            @Override
            public String getValue(ValueListProxy object) {
                return object.getValue();
            }
        };

        TextColumn<ValueListProxy> parentColumn = new TextColumn<ValueListProxy>() {
            @Override
            public String getValue(ValueListProxy object) {
                if (object.getParent() != null) {
                    return object.getParent().getValue();
                }
                return "";
            }
        };

        TextColumn<ValueListProxy> defLovColumn = new TextColumn<ValueListProxy>() {
            @Override
            public String getValue(ValueListProxy object) {
                return object.getValueType().getName();
            }
        };

        getLovTable().addColumn(valueColumn, "Value");
        getLovTable().addColumn(parentColumn, "Parent");
        getLovTable().addColumn(defLovColumn, "Définition");
        this.lovSelectionModel = new SingleSelectionModel<ValueListProxy>();
        LovTable.setSelectionModel(this.lovSelectionModel);
    }

    @Override
    public ListBox getDefLov() {
        return defLov;
    }

    @UiHandler("defLov")
    void onDefLovChanged(ChangeEvent event) {
        getUiHandlers().getParent();
    }

    @UiHandler("delete")
    void onDeleteClick(ClickEvent event) {
        getUiHandlers().delete();
    }

    @UiHandler("modify")
    void onModifyClick(ClickEvent event) {
    }
}
