package com.gsr.myschool.back.client.web.application.valueList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.gsr.myschool.back.client.request.ValueListServiceRequest;
import com.gsr.myschool.back.client.request.ValueTypeServiceRequest;
import com.gsr.myschool.back.client.request.proxy.ValueListProxy;
import com.gsr.myschool.back.client.request.proxy.ValueTypeProxy;
import com.gsr.myschool.back.client.place.NameTokens;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.web.application.ApplicationPresenter;
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
    public List<ValueTypeProxy> defLovs = new ArrayList<ValueTypeProxy>();
    public List<ValueListProxy> parents = new ArrayList<ValueListProxy>();

    @Inject
    public AddLovPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy
            , BackRequestFactory requestFactory, PlaceManager placeManager) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);
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
        ValueTypeServiceRequest dlsr = requestFactory.getDefLovServiceRequest();
        dlsr.findAll().fire(new Receiver<List<ValueTypeProxy>>() {
            @Override
            public void onSuccess(List<ValueTypeProxy> response) {
                for (ValueTypeProxy defLovProxy : response) {
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
        ValueListServiceRequest lsr = requestFactory.getLovServiceRequest();
        lsr.findByValueTypeParentName(getView().getDefLov().getItemText(getView().getDefLov().getSelectedIndex()))
                .fire(new Receiver<List<ValueListProxy>>() {
                    @Override
                    public void onSuccess(List<ValueListProxy> response) {
                        getView().getParent().addItem("Aucun", "0");
                        parents.clear();
                        for (ValueListProxy lovProxy : response) {
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
        ValueListServiceRequest lsr = requestFactory.getLovServiceRequest();
        ValueListProxy lp = lsr.create(ValueListProxy.class);
        lp.setValueType(defLovs.get(getView().getDefLov().getSelectedIndex()));
        getView().getParent().getSelectedIndex();
        lp.getValueType().getId();
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
