package com.gsr.myschool.back.client.web.application.reporting.widget;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class SummaryReportView extends ViewWithUiHandlers<SummaryReportUiHandlers>
        implements SummaryReportPresenter.MyView {
    public interface Binder extends UiBinder<Widget, SummaryReportView> {
    }

    @Inject
    public SummaryReportView(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }
}