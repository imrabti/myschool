package com.gsr.myschool.back.client.web.application.reporting.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.common.client.proxy.DossierMultipleProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gsr.myschool.back.client.web.application.reporting.widget.MultipleInscriptionsReportPresenter.MyView;

import java.util.List;

public class MultipleInscriptionsReportPresenter extends PresenterWidget<MyView>
        implements MultipleInscriptionsReportUiHandlers  {
    public interface MyView extends View, HasUiHandlers<MultipleInscriptionsReportUiHandlers> {
        void setData(List<DossierMultipleProxy> data);
    }

    private BackRequestFactory requestFactory;

    @Inject
    public MultipleInscriptionsReportPresenter(final EventBus eventBus, final MyView view,
                                               final BackRequestFactory requestFactory) {
        super(eventBus, view);

        this.requestFactory = requestFactory;

        getView().setUiHandlers(this);
    }

    @Override
    public void search(DossierStatus status) {
        requestFactory.dossierService().findMultipleDossierByStatus(status).fire(
                new ReceiverImpl<List<DossierMultipleProxy>>() {
            @Override
            public void onSuccess(List<DossierMultipleProxy> response) {
                getView().setData(response);
            }
        });
    }

    @Override
    public void export(DossierStatus status) {
        if (GWT.isScript()) {
            if (status != null) {
                Window.open("/preinscription/resource/excel/multidossier?q=" + status.name(), "_blank", "");
            } else {
                Window.open("/preinscription/resource/excel/multidossier?q=", "_blank", "");
            }
        } else {
            if (status != null) {
                Window.open("/resource/excel/multidossier?q=" + status.name(), "_blank", "");
            } else {
                Window.open("/resource/excel/multidossier?q=", "_blank", "");
            }
        }
    }
}
