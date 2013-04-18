package com.gsr.myschool.back.client.web.application.reception;

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
import com.gsr.myschool.back.client.web.application.reception.renderer.ReceptionActionCell;
import com.gsr.myschool.back.client.web.application.reception.renderer.ReceptionActionCellFactory;
import com.gsr.myschool.back.client.web.application.reception.ui.DossierFilterEditor;
import com.gsr.myschool.common.client.proxy.DossierFilterDTOProxy;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.EmptyResult;
import com.gsr.myschool.common.client.widget.LoadingIndicator;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import java.util.List;

public class ReceptionView extends ViewWithUiHandlers<ReceptionUiHandlers> implements ReceptionPresenter.MyView {
    public interface Binder extends UiBinder<Widget, ReceptionView> {
    }

    @UiField(provided = true)
    DossierFilterEditor dossierFilterEditor;
    @UiField
    CellTable<DossierProxy> inscriptionsTable;
    @UiField(provided = true)
    SimplePager pager;

    private final DateTimeFormat dateFormat;
    private final ReceptionActionCellFactory actionCellFactory;
    private final AsyncDataProvider<DossierProxy> dataProvider;

    @Inject
    public ReceptionView(final Binder uiBinder, final SharedMessageBundle sharedMessageBundle,
                         final DossierFilterEditor dossierProxyEditor,
                         final LoadingIndicator loadingIndicator,
                         final SimplePager.Resources pagerResources,
                         final ReceptionActionCellFactory actionCellFactory) {
        this.actionCellFactory = actionCellFactory;
        this.dossierFilterEditor = dossierProxyEditor;
        this.dataProvider = setupDataProvider();
        this.pager = new SimplePager(SimplePager.TextLocation.RIGHT, pagerResources, false, 0, true);

        initWidget(uiBinder.createAndBindUi(this));
        initDataGrid();

        dataProvider.addDataDisplay(inscriptionsTable);
        pager.setDisplay(inscriptionsTable);
        pager.setPageSize(GlobalParameters.PAGE_SIZE);

        dateFormat = DateTimeFormat.getFormat(GlobalParameters.DATE_FORMAT);
        inscriptionsTable.setLoadingIndicator(loadingIndicator);
        inscriptionsTable.setEmptyTableWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.WARNING));
    }

    @Override
    public void reloadData() {
        inscriptionsTable.setVisibleRangeAndClearData(inscriptionsTable.getVisibleRange(), true);
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
        inscriptionsTable.addColumn(refColumn, "N° Dossier");
        inscriptionsTable.setColumnWidth(refColumn, 10, Style.Unit.PCT);

        TextColumn<DossierProxy> cNameColumn = new TextColumn<DossierProxy>() {
            @Override
            public String getValue(DossierProxy object) {
                return object.getCandidat().getFirstname() + " " + object.getCandidat().getLastname();
            }
        };
        refColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        inscriptionsTable.addColumn(cNameColumn, "Nom Candidat");
        inscriptionsTable.setColumnWidth(cNameColumn, 15, Style.Unit.PCT);

        TextColumn<DossierProxy> cBirthColumn = new TextColumn<DossierProxy>() {
            @Override
            public String getValue(DossierProxy object) {
                if (object.getCandidat().getBirthDate() == null) return "";
                return dateFormat.format(object.getCandidat().getBirthDate());
            }
        };
        cBirthColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        inscriptionsTable.addColumn(cBirthColumn, "Date Naissance");
        inscriptionsTable.setColumnWidth(cBirthColumn, 15, Style.Unit.PCT);

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
        inscriptionsTable.addColumn(cFiliereColumn, "Formation");
        inscriptionsTable.setColumnWidth(cFiliereColumn, 15, Style.Unit.PCT);

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
        inscriptionsTable.addColumn(cLevelColumn, "Niveau demandé");
        inscriptionsTable.setColumnWidth(cLevelColumn, 15, Style.Unit.PCT);

        TextColumn<DossierProxy> createdColumn = new TextColumn<DossierProxy>() {
            @Override
            public String getValue(DossierProxy object) {
                if (object.getCreateDate() == null) return "";
                return dateFormat.format(object.getCreateDate());
            }
        };
        createdColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        inscriptionsTable.addColumn(createdColumn, "Date du dossier");
        inscriptionsTable.setColumnWidth(createdColumn, 20, Style.Unit.PCT);

        TextColumn<DossierProxy> statusColumn = new TextColumn<DossierProxy>() {
            @Override
            public String getValue(DossierProxy object) {
                if (object.getStatus() == null) return "";
                return object.getStatus().toString();
            }
        };
        statusColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        inscriptionsTable.addColumn(statusColumn, "Statut");
        inscriptionsTable.setColumnWidth(statusColumn, 10, Style.Unit.PCT);

        Delegate<DossierProxy> receiveAction = new Delegate<DossierProxy>() {
            @Override
            public void execute(DossierProxy dossier) {
                getUiHandlers().receive(dossier);
            }
        };
        Delegate<DossierProxy> previewAction = new Delegate<DossierProxy>() {
            @Override
            public void execute(DossierProxy dossier) {
                getUiHandlers().viewDetails(dossier);
            }
        };

        ReceptionActionCell actionsCell = actionCellFactory.create(receiveAction, previewAction);
        Column<DossierProxy, DossierProxy> actionsColumn = new
                Column<DossierProxy, DossierProxy>(actionsCell) {
                    @Override
                    public DossierProxy getValue(DossierProxy object) {
                        return object;
                    }
                };
        actionsColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        inscriptionsTable.addColumn(actionsColumn, "Action");
        inscriptionsTable.setColumnWidth(actionsColumn, 10, Style.Unit.PCT);
    }
}
