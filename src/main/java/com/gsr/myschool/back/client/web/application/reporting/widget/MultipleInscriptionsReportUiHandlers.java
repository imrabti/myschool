package com.gsr.myschool.back.client.web.application.reporting.widget;

import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gwtplatform.mvp.client.UiHandlers;

public interface MultipleInscriptionsReportUiHandlers extends UiHandlers {
    void search(DossierStatus status);

    void export(DossierStatus status);
}
