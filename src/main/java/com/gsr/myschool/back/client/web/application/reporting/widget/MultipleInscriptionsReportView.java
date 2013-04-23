package com.gsr.myschool.back.client.web.application.reporting.widget;

import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.widget.renderer.EnumRenderer;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import java.util.Arrays;

public class MultipleInscriptionsReportView extends ViewWithUiHandlers<MultipleInscriptionsReportUiHandlers>
        implements MultipleInscriptionsReportPresenter.MyView {
    public interface Binder extends UiBinder<Widget, MultipleInscriptionsReportView> {
    }

    @UiField(provided = true)
    ValueListBox<DossierStatus> status;

    @Inject
    public MultipleInscriptionsReportView(final Binder uiBinder) {
        this.status = new ValueListBox<DossierStatus>(new EnumRenderer<DossierStatus>());

        initWidget(uiBinder.createAndBindUi(this));

        status.setValue(DossierStatus.SUBMITTED);
        status.setAcceptableValues(Arrays.asList(DossierStatus.values()));
    }
}