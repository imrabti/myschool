package com.gsr.myschool.back.client.web.administration.valueList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.gsr.myschool.back.client.request.DefLovServiceRequest;
import com.gsr.myschool.back.client.request.LOVServiceRequest;
import com.gsr.myschool.back.client.request.proxy.DefLovProxy;
import com.gsr.myschool.back.client.request.proxy.LOVProxy;
import com.gsr.myschool.back.client.web.administration.AdministrationPresenter;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import java.util.ArrayList;
import java.util.List;

public class AddLovPresenter extends Presenter<AddLovPresenter.MyView, AddLovPresenter.MyProxy>
        implements AddLovUiHandlers {

    public BackRequestFactory requestFactory;
    public PlaceManager placeManager;
    public List<DefLovProxy> defLovs = new ArrayList<DefLovProxy>();
    public List<LOVProxy> parents = new ArrayList<LOVProxy>();

    @Inject
    public AddLovPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy
            , BackRequestFactory requestFactory, PlaceManager placeManager) {
        super(eventBus, view, proxy, AdministrationPresenter.TYPE_SetMainContent);
        this.requestFactory = requestFactory;
        this.placeManager = placeManager;
        getView().setUiHandlers(this);
    }

    @Override
    public void onBind() {
        super.onBind();
        initWidget();
    }

    @Override
    protected void revealInParent() {
        super.revealInParent();
        initDatas();
    }

    public void initWidget() {

    }

    public void initDatas() {
        fillDef();
    }

    public void fillDef() {
        final int selectedDef = getView().getDefLov().getSelectedIndex();
        getView().getDefLov().clear();
        DefLovServiceRequest dlsr = requestFactory.getDefLovServiceRequest();
        dlsr.findAll().fire(new Receiver<List<DefLovProxy>>() {
            @Override
            public void onSuccess(List<DefLovProxy> response) {
                for (DefLovProxy defLovProxy : response) {
                    getView().getDefLov().addItem(defLovProxy.getName(), defLovProxy.getId().toString());
                    defLovs.add(defLovProxy);
                }
                if (selectedDef < response.size() && selectedDef >= 0)
                    getView().getDefLov().setSelectedIndex(selectedDef);
                fillParent();
            }
        });
    }

    public void fillParent() {
        getView().getParent().clear();
        LOVServiceRequest lsr = requestFactory.getLovServiceRequest();
        lsr.findByDefLovParentName(getView().getDefLov().getItemText(getView().getDefLov().getSelectedIndex()))
                .fire(new Receiver<List<LOVProxy>>() {
                    @Override
                    public void onSuccess(List<LOVProxy> response) {
                        getView().getParent().addItem("Aucun", "0");
                        parents.clear();
                        for (LOVProxy lovProxy : response) {
                            getView().getParent().addItem(lovProxy.getValue(), lovProxy.getId().toString());
                            parents.add(lovProxy);
                        }
                    }
                });
    }

    @Override
    public void getParent() {
        fillParent();
    }

    @Override
    public void processLov() {
        LOVServiceRequest lsr = requestFactory.getLovServiceRequest();
        LOVProxy lp = lsr.create(LOVProxy.class);
        lp.setDefLov(defLovs.get(getView().getDefLov().getSelectedIndex()));
        getView().getParent().getSelectedIndex();
        lp.getDefLov().getId();
        if ("".equals(getView().getLabel().getText()))
            lp.setLabel(getView().getValue().getText());
        else
            lp.setLabel(getView().getLabel().getText());
        if (getView().getParent().getSelectedIndex() != 0) {
            lp.setParent(parents.get(getView().getParent().getSelectedIndex() - 1));
            lp.getParent().getId();
        }
        lp.setValue(getView().getValue().getText());
        lp.getValue();
        lsr.add(lp).fire(new Receiver<Void>() {
            @Override
            public void onSuccess(Void response) {
                Window.alert("added");
            }
        });

    }

    public interface MyView extends View, HasUiHandlers<AddLovUiHandlers> {
        public ListBox getParent();

        public ListBox getDefLov();

        public TextBox getValue();

        public TextBox getLabel();
    }

    @ProxyStandard
    @NameToken(NameTokens.addLov)
    public interface MyProxy extends ProxyPlace<AddLovPresenter> {

    }
}
