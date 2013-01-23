package com.gsr.myschool.back.client.web.application.valueList;

import com.google.inject.TypeLiteral;
import com.gsr.myschool.back.client.web.application.valueList.popup.*;
import com.gsr.myschool.back.client.web.application.valueList.widget.ListDefLovPresenter;
import com.gsr.myschool.back.client.web.application.valueList.widget.ListDefLovUiHandlers;
import com.gsr.myschool.back.client.web.application.valueList.widget.ListDefLovView;
import com.gsr.myschool.common.client.mvp.uihandler.SetterUiHandlersStrategy;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ValueListModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<UiHandlersStrategy<AddValueTypeUiHandlers>>() {
        })
                .to(new TypeLiteral<SetterUiHandlersStrategy<AddValueTypeUiHandlers>>() {
                });

        bind(new TypeLiteral<UiHandlersStrategy<ListDefLovUiHandlers>>() {
        })
                .to(new TypeLiteral<SetterUiHandlersStrategy<ListDefLovUiHandlers>>() {
                });

        bind(new TypeLiteral<UiHandlersStrategy<AddLovUiHandlers>>() {
        })
                .to(new TypeLiteral<SetterUiHandlersStrategy<AddLovUiHandlers>>() {
                });

        bind(new TypeLiteral<UiHandlersStrategy<ValueListUiHandlers>>(){
        }).to(new TypeLiteral<SetterUiHandlersStrategy<ValueListUiHandlers>>(){});

        bindPresenter(ValueListPresenter.class, ValueListPresenter.MyView.class, ValueListView.class,
                ValueListPresenter.MyProxy.class);

        bindPresenterWidget(ListDefLovPresenter.class, ListDefLovPresenter.MyView.class, ListDefLovView.class);

        bindPresenterWidget(AddLovPresenter.class, AddLovPresenter.MyView.class, AddLovView.class);

        bindPresenterWidget(AddValueTypePresenter.class,AddValueTypePresenter.MyView.class,AddValueTypeView.class);
    }
}
