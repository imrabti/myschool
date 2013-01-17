package com.gsr.myschool.back.client.web.administration.valueList;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.DefLovServiceRequest;
import com.gsr.myschool.back.client.request.proxy.DefLovProxy;
import com.gsr.myschool.back.client.resource.message.ErrorText;
import com.gsr.myschool.back.client.resource.message.LabelText;
import com.gsr.myschool.back.client.web.administration.AdministrationPresenter;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

import java.util.List;

public class ListDefLovPresenter extends Presenter<ListDefLovPresenter.MyView, ListDefLovPresenter.MyProxy>
        implements ListDefLovUiHandlers {

    public interface MyView extends View, HasUiHandlers<ListDefLovUiHandlers> {
        public CellTable<DefLovProxy> getDefLovTable();
        public void buildTable();
        public Button getDelete();
        public Button getModify();
    }

    @ProxyStandard
    @NameToken(NameTokens.listDefLov)
    public interface MyProxy extends ProxyPlace<ListDefLovPresenter>{

    }

    public BackRequestFactory requestFactory;
    public LabelText labels;
    public ErrorText errors;

    @Inject
    public ListDefLovPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy
            , BackRequestFactory requestFactory, LabelText labels, ErrorText errors){
        super(eventBus, view, proxy, AdministrationPresenter.TYPE_SetMainContent);
        this.requestFactory = requestFactory;
        this.labels = labels;
        this.errors = errors;
        getView().setUiHandlers(this);
    }

    @Override
    public void onBind(){
        buildWidget();
    }

    @Override
    protected void revealInParent()
    {
        super.revealInParent();
        initWidget();
        initDatas();
    }

    public void buildWidget(){
        getView().buildTable();
    }

    public void initDatas(){
        fillTable();
    }

    public void initWidget(){
        //getView().getDelete().setEnabled(false);
        //getView().getModify().setEnabled(false);
    }

    public void fillTable(){
        DefLovServiceRequest dlsr = requestFactory.getDefLovServiceRequest();
        dlsr.findAll().fire(new Receiver<List<DefLovProxy>>() {
            @Override
            public void onSuccess(List<DefLovProxy> response) {
                getView().getDefLovTable().setRowCount(response.size());
                getView().getDefLovTable().setRowData(0, response);
            }
        });
    }

    @Override
    public void selectionChanged() {
        if(((SingleSelectionModel<?>)getView().getDefLovTable().getSelectionModel()).getSelectedObject() != null)
            return;
        else
            return;
    }

    @Override
    public void modify() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete() {
        DefLovProxy toRemove = ((SingleSelectionModel<DefLovProxy>)getView().getDefLovTable().getSelectionModel())
                                                .getSelectedObject();
        if(toRemove == null){
            Window.alert("Veuillez sélectionner un élement à supprimer");
        }
        else{
            DefLovServiceRequest dlsr = requestFactory.getDefLovServiceRequest();
            dlsr.delete(toRemove).fire(new Receiver<Void>() {
                @Override
                public void onSuccess(Void response) {
                    Window.alert("L'élement a été supprimé");
                    fillTable();
                }
                @Override
                public void onFailure(ServerFailure error){
                    Window.alert(error.getMessage());
                }
            });
        }
    }

}
