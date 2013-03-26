/**
 * Copyright 2012 Nuvola Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gsr.myschool.back.client.web.application.preinscription;

import com.github.gwtbootstrap.client.ui.CellTable;
import com.github.gwtbootstrap.client.ui.SimplePager;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.inject.Inject;
import com.gsr.myschool.back.client.web.application.preinscription.renderer.PreInscriptionActionCell;
import com.gsr.myschool.back.client.web.application.preinscription.renderer.PreInscriptionActionCellFactory;
import com.gsr.myschool.back.client.web.application.preinscription.ui.DossierFilterEditor;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.DossierFilterDTOProxy;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.EmptyResult;
import com.gsr.myschool.common.client.widget.LoadingIndicator;
import com.gsr.myschool.common.shared.constants.GlobalParameters;

import java.util.List;

public class PreInscriptionView extends ViewWithUiHandlers<PreInscriptionUiHandlers>
        implements PreInscriptionPresenter.MyView {
    public interface Binder extends UiBinder<Widget, PreInscriptionView> {
    }

    @UiField(provided = true)
    DossierFilterEditor dossierFilterEditor;
    @UiField
    CellTable<DossierProxy> preInscriptionsTable;
    @UiField(provided = true)
    SimplePager pager;

    private final DateTimeFormat dateFormat;
    private final PreInscriptionActionCellFactory actionCellFactory;
    private final AsyncDataProvider<DossierProxy> dataProvider;

    @Inject
    public PreInscriptionView(final Binder uiBinder, final SharedMessageBundle sharedMessageBundle,
                              final DossierFilterEditor dossierFilterEditor,
                              final LoadingIndicator loadingIndicator,
                              final SimplePager.Resources pagerResources,
                              final UiHandlersStrategy<PreInscriptionUiHandlers> uiHandlers,
                              final PreInscriptionActionCellFactory actionCellFactory) {
        super(uiHandlers);

        this.dossierFilterEditor = dossierFilterEditor;
        this.actionCellFactory = actionCellFactory;
        this.dataProvider = setupDataProvider();
        this.pager = new SimplePager(SimplePager.TextLocation.RIGHT, pagerResources, false, 0, true);
        
        initWidget(uiBinder.createAndBindUi(this));
        initDataGrid();

        dataProvider.addDataDisplay(preInscriptionsTable);
        pager.setDisplay(preInscriptionsTable);
        pager.setPageSize(GlobalParameters.PAGE_SIZE);

        dateFormat = DateTimeFormat.getFormat(GlobalParameters.DATE_FORMAT);
        preInscriptionsTable.setLoadingIndicator(loadingIndicator);
        preInscriptionsTable.setEmptyTableWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.WARNING));
    }

    @Override
    public void reloadData() {
        preInscriptionsTable.setVisibleRangeAndClearData(preInscriptionsTable.getVisibleRange(), true);
    }

    @Override
    public void setDossierCount(Integer result) {
        dataProvider.updateRowCount(result, true);
    }

    @Override
    public void displayDossiers(Integer offset, List<DossierProxy> cars) {
        dataProvider.updateRowData(offset, cars);
    }

    @Override
    public void editDossierFilter(DossierFilterDTOProxy dossierFilter) {
        dossierFilterEditor.edit(dossierFilter);
    }

    @UiHandler("search")
    void onSearch(ClickEvent event) {
        getUiHandlers().searchWithFilter(dossierFilterEditor.get());
    }

    @UiHandler("export")
    void onExport(ClickEvent event) {
        getUiHandlers().export(dossierFilterEditor.get());
    }

    @UiHandler("initialize")
    void onInitialize(ClickEvent event) {
        getUiHandlers().init();
    }

    private AsyncDataProvider<DossierProxy> setupDataProvider() {
        return new AsyncDataProvider<DossierProxy>() {
            @Override
            protected void onRangeChanged(HasData<DossierProxy> display) {
                Range range = display.getVisibleRange();
                if (getUiHandlers() != null) {
                    getUiHandlers().fetchData(range.getStart(), range.getLength());
                }
            }
        };
    }

    private void initDataGrid() {
        TextColumn<DossierProxy> nomColumn = new TextColumn<DossierProxy>() {
            @Override
            public String getValue(DossierProxy object) {
                if (object.getCandidat() == null) return "";
                return object.getCandidat().getLastname() + " " + object.getCandidat().getFirstname();
            }
        };
        nomColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        preInscriptionsTable.addColumn(nomColumn, "Nom prénom");
        preInscriptionsTable.setColumnWidth(nomColumn, 15, Style.Unit.PCT);

        TextColumn<DossierProxy> dateColumn = new TextColumn<DossierProxy>() {
            @Override
            public String getValue(DossierProxy object) {
                if (object.getCandidat().getBirthDate() == null) return "";
                return dateFormat.format(object.getCandidat().getBirthDate());
            }
        };
        dateColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        preInscriptionsTable.addColumn(dateColumn, "Date de naissance");
        preInscriptionsTable.setColumnWidth(dateColumn, 10, Style.Unit.PCT);

        TextColumn<DossierProxy> refColumn = new TextColumn<DossierProxy>() {
            @Override
            public String getValue(DossierProxy object) {
                return object.getGeneratedNumDossier();
            }
        };
        refColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        preInscriptionsTable.addColumn(refColumn, "N° Dossier");
        preInscriptionsTable.setColumnWidth(refColumn, 10, Style.Unit.PCT);

        TextColumn<DossierProxy> statusColumn = new TextColumn<DossierProxy>() {
            @Override
            public String getValue(DossierProxy object) {
                if (object.getStatus() == null) return "";
                return object.getStatus().toString();
            }
        };
        statusColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        preInscriptionsTable.addColumn(statusColumn, "Statut");
        preInscriptionsTable.setColumnWidth(statusColumn, 10, Style.Unit.PCT);

        TextColumn<DossierProxy> anneeScolaireColumn = new TextColumn<DossierProxy>() {
            @Override
            public String getValue(DossierProxy object) {
                if (object.getAnneeScolaire() == null) return "";
                return object.getAnneeScolaire().getLabel();
            }
        };
        statusColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        preInscriptionsTable.addColumn(anneeScolaireColumn, "Année scolaire");
        preInscriptionsTable.setColumnWidth(anneeScolaireColumn, 10, Style.Unit.PCT);

        TextColumn<DossierProxy> createdColumn = new TextColumn<DossierProxy>() {
            @Override
            public String getValue(DossierProxy object) {
                if (object.getCreateDate() == null) return "";
                return dateFormat.format(object.getCreateDate());
            }
        };
        createdColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        preInscriptionsTable.addColumn(createdColumn, "Date de création");
        preInscriptionsTable.setColumnWidth(createdColumn, 10, Style.Unit.PCT);

        TextColumn<DossierProxy> cFiliereColumn = new TextColumn<DossierProxy>() {
            @Override
            public String getValue(DossierProxy object) {
                if (object.getFiliere() == null) {
                    return "";
                } else {
                    return object.getFiliere().getNom();
                }
            }
        };
        cFiliereColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        preInscriptionsTable.addColumn(cFiliereColumn, "Formation");
        preInscriptionsTable.setColumnWidth(cFiliereColumn, 10, Style.Unit.PCT);

        TextColumn<DossierProxy> cLevelColumn = new TextColumn<DossierProxy>() {
            @Override
            public String getValue(DossierProxy object) {
                if (object.getNiveauEtude() == null) {
                    return "";
                } else {
                    return object.getNiveauEtude().getNom();
                }
            }
        };
        cLevelColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        preInscriptionsTable.addColumn(cLevelColumn, "Niveau demandé");
        preInscriptionsTable.setColumnWidth(cLevelColumn, 15, Style.Unit.PCT);

        Delegate<DossierProxy> viewDetailsAction = new Delegate<DossierProxy>() {
            @Override
            public void execute(DossierProxy dossier) {
                getUiHandlers().viewDetails(dossier);
            }
        };
        PreInscriptionActionCell actionsCell = actionCellFactory.create(viewDetailsAction);
        Column<DossierProxy, DossierProxy> actionsColumn = new
                Column<DossierProxy, DossierProxy>(actionsCell) {
            @Override
            public DossierProxy getValue(DossierProxy object) {
                return object;
            }
        };
        actionsColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        preInscriptionsTable.addColumn(actionsColumn, "Détails");
        preInscriptionsTable.setColumnWidth(actionsColumn, 10, Style.Unit.PCT);
    }
}
