package com.gsr.myschool.back.client.web.application.reporting;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ValuePicker;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.resource.style.TabsListStyle;
import com.gsr.myschool.common.client.widget.renderer.EnumCell;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import java.util.Arrays;

public class ReportingView extends ViewWithUiHandlers<ReportingUiHandlers> implements ReportingPresenter.MyView {
    public interface Binder extends UiBinder<Widget, ReportingView> {
    }

    public enum ReportingType {
        SUMMARY("Bilan des pré-inscriptions"),
        CONVOCATION("Bilan des convocations"),
        MULTIPLE_INSCRIPTIONS("Comptes multi-dossiers");

        private String label;

        private ReportingType(String label) {
            this.label = label;
        }

        public String toString() {
            return label;
        }
    }

    @UiField(provided = true)
    ValuePicker<ReportingType> tabs;
    @UiField
    DeckPanel reportingIndexedPanel;
    @UiField
    SimplePanel summaryReportPanel;
    @UiField
    SimplePanel convocationReportPanel;
    @UiField
    SimplePanel multipleInscriptionReportPanel;

    @Inject
    public ReportingView(final Binder uiBinder, final TabsListStyle listStyle) {
        CellList<ReportingType> tabsCell = new CellList<ReportingType>(new EnumCell<ReportingType>(), listStyle);
        tabs = new ValuePicker<ReportingType>(tabsCell);

        initWidget(uiBinder.createAndBindUi(this));

        tabs.setAcceptableValues(Arrays.asList(ReportingType.values()));
        tabs.setValue(ReportingType.SUMMARY, false);
        reportingIndexedPanel.showWidget(ReportingType.SUMMARY.ordinal());
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (content != null) {
            if (slot == ReportingPresenter.TYPE_SetSummaryContent) {
                summaryReportPanel.setWidget(content);
            } else if (slot == ReportingPresenter.TYPE_SetConvocationsContent) {
                convocationReportPanel.setWidget(content);
            } else if (slot == ReportingPresenter.TYPE_SetMultipleInscriptionContent) {
                multipleInscriptionReportPanel.setWidget(content);
            }
        }
    }

    @UiHandler("tabs")
    void onTabsChanged(ValueChangeEvent<ReportingType> event) {
        reportingIndexedPanel.showWidget(tabs.getValue().ordinal());
    }
}