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

package com.gsr.myschool.back.client.web.application.affectation;

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
import com.gsr.myschool.back.client.web.application.affectation.renderer.AffectationActionCell;
import com.gsr.myschool.back.client.web.application.affectation.renderer.AffectationActionCellFactory;
import com.gsr.myschool.back.client.web.application.affectation.ui.DossierFilterEditor;
import com.gsr.myschool.common.client.proxy.DossierFilterDTOProxy;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.EmptyResult;
import com.gsr.myschool.common.client.widget.LoadingIndicator;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import java.util.List;

public class AffectationView extends ViewWithUiHandlers<AffectationUiHandlers>
        implements AffectationPresenter.MyView {
    public interface Binder extends UiBinder<Widget, AffectationView> {
    }

    @UiField(provided = true)
    DossierFilterEditor dossierFilterEditor;
    @UiField
    CellTable<DossierProxy> preInscriptionsTable;
    @UiField(provided = true)
    SimplePager pager;

    private final DateTimeFormat dateFormat;
    private final AffectationActionCellFactory actionCellFactory;
    private final AsyncDataProvider<DossierProxy> dataProvider;

    @Inject
    public AffectationView(final Binder uiBinder, final SharedMessageBundle sharedMessageBundle,
                           final DossierFilterEditor dossierFilterEditor,
                           final LoadingIndicator loadingIndicator,
                           final SimplePager.Resources pagerResources,
                           final AffectationActionCellFactory actionCellFactory) {
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
        TextColumn<DossierProxy> refColumn = new TextColumn<DossierProxy>() {
            @Override
            public String getValue(DossierProxy object) {
                return object.getGeneratedNumDossier();
            }
        };
        refColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        preInscriptionsTable.addColumn(refColumn, "N° Dossier");
        preInscriptionsTable.setColumnWidth(refColumn, 10, Style.Unit.PCT);

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

        Delegate<DossierProxy> viewDetailsAction = new Delegate<DossierProxy>() {
            @Override
            public void execute(DossierProxy dossier) {
                getUiHandlers().viewDetails(dossier);
            }
        };

        Delegate<DossierProxy> affectAction = new Delegate<DossierProxy>() {
            @Override
            public void execute(DossierProxy inscription) {
                getUiHandlers().affecter(inscription);
            }
        };

        Delegate<DossierProxy> desaffectAction = new Delegate<DossierProxy>() {
            @Override
            public void execute(DossierProxy inscription) {
                getUiHandlers().desaffecter(inscription);
            }
        };

        Delegate<DossierProxy> imprimer = new Delegate<DossierProxy>() {
            @Override
            public void execute(DossierProxy inscription) {
                getUiHandlers().imprimer(inscription);
            }
        };

        AffectationActionCell actionsCell = actionCellFactory.create(viewDetailsAction, affectAction, desaffectAction, imprimer);
        Column<DossierProxy, DossierProxy> actionsColumn = new
                Column<DossierProxy, DossierProxy>(actionsCell) {
                    @Override
                    public DossierProxy getValue(DossierProxy object) {
                        return object;
                    }
                };
        actionsColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        preInscriptionsTable.addColumn(actionsColumn, "Action");
        preInscriptionsTable.setColumnWidth(actionsColumn, 10, Style.Unit.PCT);
    }
}
