package com.gsr.myschool.back.client.web.application.reporting;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class ReportingView extends ViewWithUiHandlers<ReportingUiHandlers> implements ReportingPresenter.MyView {
    public interface Binder extends UiBinder<Widget, ReportingView> {
    }

    @Inject
    public ReportingView(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }
}