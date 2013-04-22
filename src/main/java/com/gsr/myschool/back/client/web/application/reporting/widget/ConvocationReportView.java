package com.gsr.myschool.back.client.web.application.reporting.widget;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class ConvocationReportView extends ViewWithUiHandlers<ConvocationReportUiHandlers>
        implements ConvocationReportPresenter.MyView {
    public interface Binder extends UiBinder<Widget, ConvocationReportView> {
    }

    @Inject
    public ConvocationReportView(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }
}