package com.gsr.myschool.back.client.web.application.reporting.widget;

import com.gsr.myschool.common.shared.type.BilanType;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gwtplatform.mvp.client.UiHandlers;

public interface SummaryReportUiHandlers extends UiHandlers {
    void generateReport(DossierStatus status, BilanType type);
}
