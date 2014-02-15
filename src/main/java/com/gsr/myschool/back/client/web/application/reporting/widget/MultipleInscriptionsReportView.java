package com.gsr.myschool.back.client.web.application.reporting.widget;

import com.github.gwtbootstrap.client.ui.CellTable;
import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.DossierMultipleProxy;
import com.gsr.myschool.common.client.proxy.ValueListProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.util.ValueList;
import com.gsr.myschool.common.client.widget.EmptyResult;
import com.gsr.myschool.common.client.widget.renderer.EnumRenderer;
import com.gsr.myschool.common.client.widget.renderer.ValueListRendererFactory;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.common.shared.type.ValueTypeCode;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import java.util.Arrays;
import java.util.List;

public class MultipleInscriptionsReportView extends ViewWithUiHandlers<MultipleInscriptionsReportUiHandlers>
        implements MultipleInscriptionsReportPresenter.MyView {
    public interface Binder extends UiBinder<Widget, MultipleInscriptionsReportView> {
    }

    @UiField(provided = true)
    ValueListBox<DossierStatus> status;
    @UiField(provided = true)
    ValueListBox<ValueListProxy> anneeScolaire;
    @UiField
    CellTable<DossierMultipleProxy> dossierTable;

    private final ListDataProvider<DossierMultipleProxy> dataProvider;

    @Inject
    public MultipleInscriptionsReportView(final Binder uiBinder, final SharedMessageBundle sharedMessageBundle,
                                          final ValueList valueList,
                                          final ValueListRendererFactory valueListRendererFactory) {
        this.status = new ValueListBox<DossierStatus>(new EnumRenderer<DossierStatus>());
        anneeScolaire = new ValueListBox<ValueListProxy>(valueListRendererFactory.create("Année Scolaire"));

        initWidget(uiBinder.createAndBindUi(this));
        initDataGrid();

        dataProvider = new ListDataProvider<DossierMultipleProxy>();
        dataProvider.addDataDisplay(dossierTable);

        status.setAcceptableValues(Arrays.asList(DossierStatus.values()));
        dossierTable.setEmptyTableWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.WARNING));

        anneeScolaire.setAcceptableValues(valueList.getValueListByCode(ValueTypeCode.SCHOOL_YEAR));
    }

    @Override
    public void setData(List<DossierMultipleProxy> data) {
        dossierTable.setPageSize(data.size());
        dataProvider.getList().clear();
        dataProvider.getList().addAll(data);
    }

    @UiHandler("search")
    void onSearchClicked(ClickEvent event) {
        getUiHandlers().search(status.getValue(),
                anneeScolaire.getValue() != null ? anneeScolaire.getValue().getValue() : null);
    }

    @UiHandler("export")
    void onExportClicked(ClickEvent event) {
        getUiHandlers().export(status.getValue(),
                anneeScolaire.getValue() != null ? anneeScolaire.getValue().getValue() : null);
    }

    private void initDataGrid() {
        TextColumn<DossierMultipleProxy> compteColumn = new TextColumn<DossierMultipleProxy>() {
            @Override
            public String getValue(DossierMultipleProxy object) {
                return object.getCompte();
            }
        };
        compteColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        dossierTable.addColumn(compteColumn, "Compte");
        dossierTable.setColumnWidth(compteColumn, 10, Style.Unit.PCT);

        TextColumn<DossierMultipleProxy> numDossierColumn = new TextColumn<DossierMultipleProxy>() {
            @Override
            public String getValue(DossierMultipleProxy object) {
                return object.getNumDossier();
            }
        };
        compteColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        dossierTable.addColumn(numDossierColumn, "Num dossier");
        dossierTable.setColumnWidth(numDossierColumn, 10, Style.Unit.PCT);

        TextColumn<DossierMultipleProxy> candidatColumn = new TextColumn<DossierMultipleProxy>() {
            @Override
            public String getValue(DossierMultipleProxy object) {
                return object.getCandidat();
            }
        };
        compteColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        dossierTable.addColumn(candidatColumn, "Candidat");
        dossierTable.setColumnWidth(candidatColumn, 12, Style.Unit.PCT);

        TextColumn<DossierMultipleProxy> etablissementColumn = new TextColumn<DossierMultipleProxy>() {
            @Override
            public String getValue(DossierMultipleProxy object) {
                return object.getEtablissement();
            }
        };
        compteColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        dossierTable.addColumn(etablissementColumn, "Etablissement");
        dossierTable.setColumnWidth(etablissementColumn, 12, Style.Unit.PCT);

        TextColumn<DossierMultipleProxy> pereColumn = new TextColumn<DossierMultipleProxy>() {
            @Override
            public String getValue(DossierMultipleProxy object) {
                return object.getPere();
            }
        };
        compteColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        dossierTable.addColumn(pereColumn, "Père");
        dossierTable.setColumnWidth(pereColumn, 12, Style.Unit.PCT);

        TextColumn<DossierMultipleProxy> mereColumn = new TextColumn<DossierMultipleProxy>() {
            @Override
            public String getValue(DossierMultipleProxy object) {
                return object.getMere();
            }
        };
        compteColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        dossierTable.addColumn(mereColumn, "Mère");
        dossierTable.setColumnWidth(mereColumn, 12, Style.Unit.PCT);

        TextColumn<DossierMultipleProxy> tuteurColumn = new TextColumn<DossierMultipleProxy>() {
            @Override
            public String getValue(DossierMultipleProxy object) {
                return object.getTuteur();
            }
        };
        compteColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        dossierTable.addColumn(tuteurColumn, "Tuteur");
        dossierTable.setColumnWidth(tuteurColumn, 12, Style.Unit.PCT);

        TextColumn<DossierMultipleProxy> filiereColumn = new TextColumn<DossierMultipleProxy>() {
            @Override
            public String getValue(DossierMultipleProxy object) {
                return object.getFiliere();
            }
        };
        compteColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        dossierTable.addColumn(filiereColumn, "Filière");
        dossierTable.setColumnWidth(filiereColumn, 12, Style.Unit.PCT);

        TextColumn<DossierMultipleProxy> niveauEtudeColumn = new TextColumn<DossierMultipleProxy>() {
            @Override
            public String getValue(DossierMultipleProxy object) {
                return object.getNiveauEtude();
            }
        };
        compteColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        dossierTable.addColumn(niveauEtudeColumn, "Niveau d'étude");
        dossierTable.setColumnWidth(niveauEtudeColumn, 14, Style.Unit.PCT);
    }
}
