package com.gsr.myschool.back.client.web.application.reception;

import com.github.gwtbootstrap.client.ui.CellTable;
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
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;
import com.gsr.myschool.back.client.web.application.reception.renderer.ReceptionActionCell;
import com.gsr.myschool.back.client.web.application.reception.renderer.ReceptionActionCellFactory;
import com.gsr.myschool.back.client.web.application.reception.ui.DossierFilterEditor;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.DossierFilterDTOProxy;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.EmptyResult;
import com.gsr.myschool.common.shared.constants.GlobalParameters;

import java.util.List;

public class ReceptionView extends ViewWithUiHandlers<ReceptionUiHandlers>
        implements ReceptionPresenter.MyView {
    public interface Binder extends UiBinder<Widget, ReceptionView> {
    }

    @UiField(provided = true)
    DossierFilterEditor dossierFilterEditor;
    @UiField
    CellTable<DossierProxy> inscriptionsTable;

    private final DateTimeFormat dateFormat;
    private final ListDataProvider<DossierProxy> dataProvider;
    private final ReceptionActionCellFactory actionCellFactory;

    @Inject
    public ReceptionView(final Binder uiBinder, final SharedMessageBundle sharedMessageBundle,
                         final DossierFilterEditor dossierProxyEditor,
                         final UiHandlersStrategy<ReceptionUiHandlers> uiHandlers,
                         final ReceptionActionCellFactory actionCellFactory) {
        super(uiHandlers);

        this.actionCellFactory = actionCellFactory;
        this.dossierFilterEditor = dossierProxyEditor;

        initWidget(uiBinder.createAndBindUi(this));
        initDataGrid();

        dataProvider = new ListDataProvider<DossierProxy>();
        dataProvider.addDataDisplay(inscriptionsTable);

        dateFormat = DateTimeFormat.getFormat(GlobalParameters.DATE_FORMAT);
        inscriptionsTable.setEmptyTableWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.INFO));
    }

    @Override
    public void setData(List<DossierProxy> data) {
        inscriptionsTable.setPageSize(data.size());
        dataProvider.getList().clear();
        dataProvider.getList().addAll(data);
    }

    @Override
    public void editDossierFilter(DossierFilterDTOProxy dossierFilter) {
        dossierFilterEditor.edit(dossierFilter);
    }

    @UiHandler("search")
    void onSearch(ClickEvent event) {
        getUiHandlers().searchWithFilter(dossierFilterEditor.get());
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
        inscriptionsTable.addColumn(cFiliereColumn, "Filiere");
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
        inscriptionsTable.addColumn(cLevelColumn, "Niveau");
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
