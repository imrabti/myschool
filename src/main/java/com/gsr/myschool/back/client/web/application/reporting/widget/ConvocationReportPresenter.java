package com.gsr.myschool.back.client.web.application.reporting.widget;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.DossierRequest;
import com.gsr.myschool.back.client.web.application.reporting.widget.ConvocationReportPresenter.MyView;
import com.gsr.myschool.common.client.proxy.DossierConvocationDTOProxy;
import com.gsr.myschool.common.client.proxy.DossierFilterDTOProxy;
import com.gsr.myschool.common.client.proxy.PagedDossiersProxy;
import com.gsr.myschool.common.client.proxy.SessionExamenProxy;
import com.gsr.myschool.common.client.request.ExcelRequestBuilder;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.request.ReportRequestBuilder;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import java.util.ArrayList;
import java.util.List;

public class ConvocationReportPresenter extends PresenterWidget<MyView> implements ConvocationReportUiHandlers {
    public interface MyView extends View, HasUiHandlers<ConvocationReportUiHandlers> {
        void editDossierFilter(DossierFilterDTOProxy dossierFilter);

        void setDossierCount(Integer result);

        void reloadData();

        void displayDossiers(Integer offset, List<DossierConvocationDTOProxy> cars);
    }

    private final BackRequestFactory requestFactory;

    private DossierRequest currentContext;
    private DossierFilterDTOProxy dossierFilter;

    @Inject
    public ConvocationReportPresenter(final EventBus eventBus, final MyView view,
            final BackRequestFactory requestFactory) {
        super(eventBus, view);

        this.requestFactory = requestFactory;

        getView().setUiHandlers(this);
    }

    @Override
    public void fetchData(final Integer offset, Integer limit) {
        Integer pageNumber = (offset / limit) + (offset % limit);
        currentContext.findAllDossiersBySessionAndCriteria(dossierFilter, pageNumber, limit)
                .fire(new ReceiverImpl<PagedDossiersProxy>() {
                    @Override
                    public void onSuccess(PagedDossiersProxy result) {
                        currentContext = requestFactory.dossierService();
                        dossierFilter = currentContext.edit(dossierFilter);

                        getView().displayDossiers(offset, result.getDossierConvocationDTOs());
                        getView().editDossierFilter(dossierFilter);
                    }
                });
    }

    @Override
    public void searchWithFilter(DossierFilterDTOProxy filer) {
        dossierFilter.setFiliere(dossierFilter.getFiliere() != null ?
                currentContext.edit(dossierFilter.getFiliere()) : null);
        dossierFilter.setNiveauEtude(dossierFilter.getNiveauEtude() != null ?
                currentContext.edit(dossierFilter.getNiveauEtude()) : null);
        List<SessionExamenProxy> sessions = new ArrayList<SessionExamenProxy>();
        if (dossierFilter.getSession() != null) {
            sessions.add(dossierFilter.getSession());
        }
        dossierFilter.setSessionList(sessions);

        loadDossiersCounts();
    }

    @Override
    public void printRapport(DossierFilterDTOProxy dossierFilter) {
        ReportRequestBuilder requestBuilder = new ReportRequestBuilder();
        // requestBuilder.buildData(dossier.getId().toString());
        requestBuilder.sendRequest();
    }

    @Override
    public void init() {
        currentContext = requestFactory.dossierService();
        dossierFilter = currentContext.create(DossierFilterDTOProxy.class);
        dossierFilter.setStatus(DossierStatus.AFFECTED);

        getView().editDossierFilter(dossierFilter);
        loadDossiersCounts();
    }

    @Override
    public void export(DossierFilterDTOProxy dossierFilter) {
        ExcelRequestBuilder request = new ExcelRequestBuilder("resource/dossierconvoques");
        request.sendRequestReportConvocation(dossierFilter);
    }

    @Override
    protected void onReveal() {
        currentContext = requestFactory.dossierService();
        dossierFilter = currentContext.create(DossierFilterDTOProxy.class);
        dossierFilter.setStatus(DossierStatus.AFFECTED);

        getView().editDossierFilter(dossierFilter);
        loadDossiersCounts();
    }

    private void loadDossiersCounts() {
        currentContext.findAllDossiersBySessionAndCriteria(dossierFilter, 0, GlobalParameters.PAGE_SIZE)
                .fire(new ReceiverImpl<PagedDossiersProxy>() {
                    @Override
                    public void onSuccess(PagedDossiersProxy result) {
                        currentContext = requestFactory.dossierService();
                        dossierFilter = currentContext.edit(dossierFilter);

                        getView().setDossierCount(result.getTotalElements());
                        getView().editDossierFilter(dossierFilter);
                        getView().reloadData();
                    }
                });
    }
}
