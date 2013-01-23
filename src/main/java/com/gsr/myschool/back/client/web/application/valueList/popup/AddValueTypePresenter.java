package com.gsr.myschool.back.client.web.application.valueList.popup;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.ValueListServiceRequest;
import com.gsr.myschool.back.client.request.ValueTypeServiceRequest;
import com.gsr.myschool.back.client.request.proxy.ValueListProxy;
import com.gsr.myschool.back.client.request.proxy.ValueTypeProxy;
import com.gsr.myschool.common.client.mvp.ValidatedPopupView;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

public class AddValueTypePresenter extends PresenterWidget<AddValueTypePresenter.MyView>
        implements AddValueTypeUiHandlers {
    public interface MyView extends ValidatedPopupView, HasUiHandlers<AddValueTypeUiHandlers> {
        void fillParentList(List<ValueTypeProxy> parents);

        void fillRegexList(List<ValueListProxy> regex);

        TextBox getName();

        ValueListBox<ValueListProxy> getRegex();

        ValueListBox<ValueTypeProxy> getParent();

        CheckBox getSystemDefLov();
    }

    private final BackRequestFactory requestFactory;

    @Inject
    public AddValueTypePresenter(final EventBus eventBus, final MyView view,
                                 final BackRequestFactory requestFactory) {
        super(eventBus, view);

        this.requestFactory = requestFactory;

        getView().setUiHandlers(this);
    }

    public void initDatas() {
        getAllDefLov();
        getRegexes();
    }

    public void getAllDefLov() {
        ValueTypeServiceRequest dlsr = requestFactory.valueTypeServiceRequest();

        dlsr.findAll().fire(new Receiver<List<ValueTypeProxy>>() {
            @Override
            public void onSuccess(List<ValueTypeProxy> response) {
                getView().fillParentList(response);
            }
        });
    }

    public void getRegexes() {
        ValueListServiceRequest lsr = requestFactory.valueListServiceRequest();
        lsr.findByValueTypeName("Regex").fire(new Receiver<List<ValueListProxy>>() {
            @Override
            public void onSuccess(List<ValueListProxy> response) {
                getView().fillRegexList(response);
            }
        });
    }

    @Override
    public void processDefLov() {
        ValueTypeServiceRequest dlsr = requestFactory.valueTypeServiceRequest();
        ValueTypeProxy toAdd = dlsr.create(ValueTypeProxy.class);
        toAdd.setName(getView().getName().getText());
        toAdd.setRegex(getView().getRegex().getValue());
        toAdd.setParent(getView().getParent().getValue());
        toAdd.setSystem(getView().getSystemDefLov().getValue());
        ValueTypeProxy parent = dlsr.create(ValueTypeProxy.class);
        if (getView().getParent().getValue() != null) {
            parent.setId(new Long(getView().getParent().getValue().getId()));
        } else {
            parent = null;
        }
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
                });
    }
}