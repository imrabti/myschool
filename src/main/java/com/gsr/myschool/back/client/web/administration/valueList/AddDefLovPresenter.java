package com.gsr.myschool.back.client.web.administration.valueList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
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

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

public class AddDefLovPresenter extends Presenter<AddDefLovPresenter.MyView, AddDefLovPresenter.MyProxy>
        implements AddDefLovUiHandlers {

    private BackRequestFactory requestFactory;

    @Inject
    public AddDefLovPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                              BackRequestFactory requestFactory) {
        super(eventBus, view, proxy, AdministrationPresenter.TYPE_SetMainContent);
        this.requestFactory = requestFactory;
        getView().setUiHandlers(this);
    }

    @Override
    protected void revealInParent() {
        initDatas();
        super.revealInParent();

    }

    @Override
    protected void onBind() {
        buildWidget();
        super.onBind();
    }

    public void buildWidget() {
    }

    public void initDatas() {
        getAllDefLov();
        getRegexes();
    }

    public void getAllDefLov() {
        DefLovServiceRequest dlsr = requestFactory.getDefLovServiceRequest();

        dlsr.findAll().fire(new Receiver<List<DefLovProxy>>() {
            @Override
            public void onSuccess(List<DefLovProxy> response) {
                getView().fillParentList(response);
            }
        });

    }

    public void getRegexes() {
        LOVServiceRequest lsr = requestFactory.getLovServiceRequest();
        lsr.findByDefLovName("Regex").fire(new Receiver<List<LOVProxy>>() {
            @Override
            public void onSuccess(List<LOVProxy> response) {
                getView().fillRegexList(response);
            }
        });
    }

    @Override
    public void processDefLov() {
        DefLovServiceRequest dlsr = requestFactory.getDefLovServiceRequest();
        DefLovProxy toAdd = dlsr.create(DefLovProxy.class);
        toAdd.setName(getView().getName().getText());
        Window.alert(getView().getRegex().getValue().getValue() + "");
        toAdd.setRegex(getView().getRegex().getValue());
        toAdd.setParent(getView().getParent().getValue());
        toAdd.setSystem(getView().getSystemDefLov().getValue());
        DefLovProxy parent = dlsr.create(DefLovProxy.class);
        if (getView().getParent().getValue() != null) {
            Window.alert(getView().getParent().getValue() + "");
            parent.setId(new Long(getView().getParent().getValue().getId()));
        } else
            parent = null;
        toAdd.setParent(parent);
        toAdd.setSystem(getView().getSystemDefLov().getValue());
        dlsr.add(toAdd)
                .fire(new Receiver<Void>() {
                    @Override
                    public void onSuccess(Void response) {
                        Window.alert("added");
                    }

                    @Override
                    public void onConstraintViolation(Set<ConstraintViolation<?>> violations) {
                        for (ConstraintViolation violation : violations) {
                            Window.alert(violation.getMessage() + " " + violation.getInvalidValue());
                        }
                    }

                    /*@Override
                    public void onFailure(ServerFailure error){
                        System.out.println(error.getMessage());
                    } */


                });
    }

    public interface MyView extends View, HasUiHandlers<AddDefLovUiHandlers> {
        public void fillParentList(List<DefLovProxy> parents);

        public void fillRegexList(List<LOVProxy> regex);

        public TextBox getName();

        public ValueListBox<LOVProxy> getRegex();

        public ValueListBox<DefLovProxy> getParent();

        public CheckBox getSystemDefLov();
    }

    @ProxyStandard
    @NameToken(NameTokens.addDefLov)
    public interface MyProxy extends ProxyPlace<AddDefLovPresenter> {
    }
}
