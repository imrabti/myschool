package com.gsr.myschool.back.client.web.application.valueList;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.ValueTypeServiceRequest;
import com.gsr.myschool.back.client.request.proxy.ValueTypeProxy;
import com.gsr.myschool.back.client.resource.message.MessageBundle;
import com.gsr.myschool.back.client.web.application.ApplicationPresenter;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import java.util.List;

public class ListDefLovPresenter extends Presenter<ListDefLovPresenter.MyView, ListDefLovPresenter.MyProxy>
        implements ListDefLovUiHandlers {
    public interface MyView extends View, HasUiHandlers<ListDefLovUiHandlers> {
        CellTable<ValueTypeProxy> getDefLovTable();

        void buildTable();

        Button getDelete();

        Button getModify();
    }

    @ProxyStandard
    @NameToken(NameTokens.listDefLov)
    public interface MyProxy extends ProxyPlace<ListDefLovPresenter> {
    }

    public final BackRequestFactory requestFactory;
    public final MessageBundle messageBundle;

    @Inject
    public ListDefLovPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy
            , final BackRequestFactory requestFactory, final MessageBundle messageBundle) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);
        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;
        getView().setUiHandlers(this);
    }

    @Override
    public void onBind() {
        buildWidget();
    }

    @Override
    protected void revealInParent() {
        super.revealInParent();
        initWidget();
        initDatas();
    }

    public void buildWidget() {
        getView().buildTable();
    }

    public void initDatas() {
        fillTable();
    }

    public void initWidget() {
    }

    public void fillTable() {
        ValueTypeServiceRequest dlsr = requestFactory.valueTypeServiceRequest();
        dlsr.findAll().fire(new Receiver<List<ValueTypeProxy>>() {
            @Override
            public void onSuccess(List<ValueTypeProxy> response) {
                getView().getDefLovTable().setRowCount(response.size());
                getView().getDefLovTable().setRowData(0, response);
            }
        });
    }

    @Override
    public void selectionChanged() {
        if (((SingleSelectionModel<?>) getView().getDefLovTable().getSelectionModel()).getSelectedObject() != null) {
            return;
        } else {
            return;
        }
    }

    @Override
    public void modify() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete() {
        ValueTypeProxy toRemove = ((SingleSelectionModel<ValueTypeProxy>) getView().getDefLovTable().getSelectionModel())
                .getSelectedObject();
        if (toRemove == null) {
            Window.alert("Veuillez sélectionner un élement à supprimer");
        } else {
            ValueTypeServiceRequest dlsr = requestFactory.valueTypeServiceRequest();
            dlsr.delete(toRemove).fire(new Receiver<Void>() {
                @Override
                public void onSuccess(Void response) {
                    Window.alert("L'élement a été supprimé");
                    fillTable();
                }

                @Override
                public void onFailure(ServerFailure error) {
                    Window.alert(error.getMessage());
                }
            });
        }
    }
}
