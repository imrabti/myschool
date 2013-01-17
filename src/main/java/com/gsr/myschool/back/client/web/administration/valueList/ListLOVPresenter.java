package com.gsr.myschool.back.client.web.administration.valueList;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.DefLovServiceRequest;
import com.gsr.myschool.back.client.request.LOVServiceRequest;
import com.gsr.myschool.back.client.request.proxy.DefLovProxy;
import com.gsr.myschool.back.client.request.proxy.LOVProxy;
import com.gsr.myschool.back.client.web.administration.AdministrationPresenter;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * SuperUser: houssam
 * Date: 31/12/12
 * Time: 18:07
 * To change this template use File | Settings | File Templates.
 */
public class ListLOVPresenter extends Presenter<ListLOVPresenter.MyView, ListLOVPresenter.MyProxy>
        implements ListLOVUiHandlers {


    public BackRequestFactory requestFactory;

    @Inject
    public ListLOVPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy
            , BackRequestFactory requestFactory) {
        super(eventBus, view, proxy, AdministrationPresenter.TYPE_SetMainContent);
        this.requestFactory = requestFactory;
        getView().setUiHandlers(this);

    }

    @Override
    protected void onBind() {
        buildWidget();
    }

    @Override
    protected void revealInParent() {
        super.revealInParent();
        initWidget();
        initDatas();
    }

    public void initWidget() {

    }

    public void initDatas() {
        fillDef();
        fillTable();
    }

    public void buildWidget() {
        getView().initTable();
    }

    public void fillTable() {
        LOVServiceRequest lsr = requestFactory.getLovServiceRequest();
        lsr.findAll().fire(new Receiver<List<LOVProxy>>() {
            @Override
            public void onSuccess(List<LOVProxy> response) {
                getView().getLovTable().setRowCount(response.size());
                getView().getLovTable().setVisibleRange(0, response.size());
                getView().getLovTable().setRowData(0, response);
                getView().getLovTable().setPageSize(response.size());
            }
        });
    }

    public void fillDef() {
        getView().getDefLov().clear();
        DefLovServiceRequest dlsr = requestFactory.getDefLovServiceRequest();
        dlsr.findAll().fire(new Receiver<List<DefLovProxy>>() {
            @Override
            public void onSuccess(List<DefLovProxy> response) {
                for (DefLovProxy defLovProxy : response) {
                    getView().getDefLov().addItem(defLovProxy.getName(), defLovProxy.getId().toString());
                }
                fillParent();
            }
        });
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
        LOVProxy toDelete = ((SingleSelectionModel<LOVProxy>) getView().getLovTable().getSelectionModel())
                .getSelectedObject();
        LOVServiceRequest lsr = requestFactory.getLovServiceRequest();
        lsr.delete(toDelete.getId()).fire(new Receiver<Void>() {
            @Override
            public void onSuccess(Void response) {
                Window.alert("Deleted");
                initDatas();
            }
        });
    }

    @Override
    public void modify() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void fillParent() {
        getView().getParent().clear();
        LOVServiceRequest lsr = requestFactory.getLovServiceRequest();
        lsr.findByDefLovName(getView().getDefLov().getItemText(getView().getDefLov().getSelectedIndex()))
                .fire(new Receiver<List<LOVProxy>>() {
                    @Override
                    public void onSuccess(List<LOVProxy> response) {
                        getView().getParent().addItem("Aucun", "0");
                        for (LOVProxy lovProxy : response) {
                            getView().getParent().addItem(lovProxy.getValue(), lovProxy.getId().toString());
                        }
                    }
                });
    }

    public interface MyView extends View, HasUiHandlers<ListLOVUiHandlers> {
        public CellTable<LOVProxy> getLovTable();

        public ListBox getParent();

        public ListBox getDefLov();

        public void initTable();
    }

    @NameToken(NameTokens.listLov)
    @ProxyStandard
    public interface MyProxy extends ProxyPlace<ListLOVPresenter> {

    }


}
