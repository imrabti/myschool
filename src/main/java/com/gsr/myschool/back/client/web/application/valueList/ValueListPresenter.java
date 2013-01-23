package com.gsr.myschool.back.client.web.application.valueList;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.ValueListServiceRequest;
import com.gsr.myschool.back.client.request.ValueTypeServiceRequest;
import com.gsr.myschool.back.client.request.proxy.ValueListProxy;
import com.gsr.myschool.back.client.request.proxy.ValueTypeProxy;
import com.gsr.myschool.back.client.web.application.ApplicationPresenter;
import com.gsr.myschool.back.client.web.application.valueList.popup.AddLovPresenter;
import com.gsr.myschool.back.client.web.application.valueList.popup.AddValueTypePresenter;
import com.gsr.myschool.back.client.web.application.valueList.widget.ListDefLovPresenter;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import java.util.List;

public class ValueListPresenter extends Presenter<ValueListPresenter.MyView, ValueListPresenter.MyProxy> implements ValueListUiHandlers {
    public interface MyView extends View,HasUiHandlers<ValueListUiHandlers> {
        CellTable<ValueListProxy> getLovTable();

        ListBox getParent();

        ListBox getDefLov();

        void initTable();
    }

    @ProxyStandard
    @NameToken(value = NameTokens.valueList)
    public interface MyProxy extends ProxyPlace<ValueListPresenter> {
    }

    public static final Object TYPE_SetValueTypeContent = new Object();

    private final AddLovPresenter addLovPresenter;
    private final ListDefLovPresenter listDefLovPresenter;
    public final BackRequestFactory backRequestFactory;

    @Inject
    public ValueListPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,final AddValueTypePresenter addValueTypePresenter,final AddLovPresenter addLovPresenter,final ListDefLovPresenter listDefLovPresenter,final BackRequestFactory backRequestFactory) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);

        this.addLovPresenter = addLovPresenter;
        this.listDefLovPresenter = listDefLovPresenter;
        this.backRequestFactory = backRequestFactory;

        getView().setUiHandlers(this);
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        setInSlot(TYPE_SetValueTypeContent,listDefLovPresenter);
        fillTable();
    }

    @Override
    public void addValueList() {
        addToPopupSlot(addLovPresenter);
    }

    @Override
    public void getParent() {
        fillParent();
    }

    @Override
    public void refreshList() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete() {
        ValueListProxy toDelete = ((SingleSelectionModel<ValueListProxy>) getView().getLovTable().getSelectionModel())
                .getSelectedObject();
        ValueListServiceRequest lsr = backRequestFactory.valueListServiceRequest();
        lsr.delete(toDelete.getId()).fire(new Receiver<Void>() {
            @Override
            public void onSuccess(Void response) {
            }
        });
    }

    @Override
    public void modify() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void fillTable() {
        ValueListServiceRequest lsr = backRequestFactory.valueListServiceRequest();
        lsr.findAll().fire(new Receiver<List<ValueListProxy>>() {
            @Override
            public void onSuccess(List<ValueListProxy> response) {
                getView().getLovTable().setRowCount(response.size());
                getView().getLovTable().setVisibleRange(0, response.size());
                getView().getLovTable().setRowData(0, response);
                getView().getLovTable().setPageSize(response.size());
            }
        });

        getView().initTable();
    }

    public void fillDef() {
        getView().getDefLov().clear();
        ValueTypeServiceRequest dlsr = backRequestFactory.valueTypeServiceRequest();
        dlsr.findAll().fire(new Receiver<List<ValueTypeProxy>>() {
            @Override
            public void onSuccess(List<ValueTypeProxy> response) {
                for (ValueTypeProxy defLovProxy : response) {
                    getView().getDefLov().addItem(defLovProxy.getName(), defLovProxy.getId().toString());
                }
                fillParent();
            }
        });
    }

    public void fillParent() {
        getView().getParent().clear();
        ValueListServiceRequest lsr = backRequestFactory.valueListServiceRequest();
        lsr.findByValueTypeName(getView().getDefLov().getItemText(getView().getDefLov().getSelectedIndex()))
                .fire(new Receiver<List<ValueListProxy>>() {
                    @Override
                    public void onSuccess(List<ValueListProxy> response) {
                        getView().getParent().addItem("Aucun", "0");
                        for (ValueListProxy lovProxy : response) {
                            getView().getParent().addItem(lovProxy.getValue(), lovProxy.getId().toString());
                        }
                    }
                });
    }
}
