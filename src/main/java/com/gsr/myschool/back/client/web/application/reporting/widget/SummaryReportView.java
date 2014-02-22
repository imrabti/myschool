package com.gsr.myschool.back.client.web.application.reporting.widget;

import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.ValueListProxy;
import com.gsr.myschool.common.client.util.ValueList;
import com.gsr.myschool.common.client.widget.renderer.EnumRenderer;
import com.gsr.myschool.common.client.widget.renderer.ValueListRendererFactory;
import com.gsr.myschool.common.shared.type.BilanType;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.common.shared.type.ValueTypeCode;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import java.util.Arrays;
import java.util.List;

public class SummaryReportView extends ViewWithUiHandlers<SummaryReportUiHandlers>
        implements SummaryReportPresenter.MyView {
    public interface Binder extends UiBinder<Widget, SummaryReportView> {
    }

    @UiField(provided = true)
    ValueListBox<DossierStatus> statusList;
    @UiField(provided = true)
    ValueListBox<BilanType> reportTypeList;
    @UiField(provided = true)
    ValueListBox<ValueListProxy> anneeScolaire;
    @UiField
    CheckBox historic;

    @Inject
    public SummaryReportView(final Binder uiBinder,
                             final ValueListRendererFactory valueListRendererFactory, final ValueList valueList) {
        statusList = new ValueListBox<DossierStatus>(new EnumRenderer<DossierStatus>());
        statusList.setAcceptableValues(Arrays.asList(DossierStatus.values()));


        reportTypeList = new ValueListBox<BilanType>(new EnumRenderer<BilanType>());
        reportTypeList.setAcceptableValues(Arrays.asList(BilanType.values()));
        reportTypeList.setValue(BilanType.CYCLE);

        anneeScolaire = new ValueListBox<ValueListProxy>(valueListRendererFactory.create("Année Scolaire"));

        initWidget(uiBinder.createAndBindUi(this));

        setAnneeScolaireValues(valueList);
    }

    @UiHandler("generate")
    void onGenerateClicked(ClickEvent event) {
        getUiHandlers().generateReport(statusList.getValue(), reportTypeList.getValue(),
                anneeScolaire.getValue() != null ? anneeScolaire.getValue().getValue() : null, historic.getValue());
    }

    @UiHandler("generateSummary")
    void onGenerateSummaryClicked(ClickEvent event) {
        getUiHandlers().generateSummary(anneeScolaire.getValue() != null ? anneeScolaire.getValue().getValue() : null);
    }

    private void setAnneeScolaireValues(ValueList valueList) {
        List<ValueListProxy> anneeScolaireList = valueList.getValueListByCode(ValueTypeCode.SCHOOL_YEAR, false);
        if (anneeScolaire.getValue() == null) {
            anneeScolaire.setValue(anneeScolaireList.get(0));
        }
        anneeScolaire.setAcceptableValues(anneeScolaireList);
    }
}