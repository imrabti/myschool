package com.gsr.myschool.back.client.web.application.reporting.widget;

import com.gsr.myschool.common.client.proxy.DossierFilterDTOProxy;
import com.gwtplatform.mvp.client.UiHandlers;

public interface ConvocationReportUiHandlers extends UiHandlers {
    void fetchData(Integer offset, Integer limit);

    void searchWithFilter(DossierFilterDTOProxy dossierFilter);

    void export(DossierFilterDTOProxy dossierFilter);

    void printRapport(DossierFilterDTOProxy dossierFilter);

    void init();
}
