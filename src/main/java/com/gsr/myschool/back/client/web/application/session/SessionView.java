package com.gsr.myschool.back.client.web.application.session;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.CellTable;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.gsr.myschool.back.client.resource.style.CellTableStyle;
import com.gsr.myschool.back.client.web.application.session.renderer.AttachedNiveauEtudeTree;
import com.gsr.myschool.back.client.web.application.session.renderer.AttachedNiveauEtudeTreeFactory;
import com.gsr.myschool.back.client.web.application.session.renderer.SessionActionCell;
import com.gsr.myschool.back.client.web.application.session.renderer.SessionActionCellFactory;
import com.gsr.myschool.common.client.proxy.SessionExamenProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.EmptyResult;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.common.shared.dto.NiveauEtudeNode;
import com.gsr.myschool.common.shared.dto.SessionTree;
import com.gsr.myschool.common.shared.type.SessionStatus;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import java.util.List;

public class SessionView extends ViewWithUiHandlers<SessionUiHandlers> implements SessionPresenter.MyView {
    public interface Binder extends UiBinder<Widget, SessionView> {
    }

    @UiField(provided = true)
    CellTable<SessionExamenProxy> sessionsTable;
    @UiField(provided = true)
    CellTree attachedNiveau;
    @UiField
    Button attachNiveauEtude;

    private final ListDataProvider<SessionExamenProxy> dataProvider;
    private final AttachedNiveauEtudeTree treeModel;
    private final SessionActionCellFactory sessionActionCellFactory;
    private final DateTimeFormat dateFormat;
    private final SingleSelectionModel<SessionExamenProxy> selectionModel;

    private Delegate<SessionExamenProxy> openAction;
    private Delegate<SessionExamenProxy> updateAction;
    private Delegate<SessionExamenProxy> closeAction;
    private Delegate<SessionExamenProxy> cancelAction;
    private Delegate<SessionExamenProxy> copyAction;

    private SessionActionCell actionsCell;

    @Inject
    public SessionView(final Binder uiBinder,
                       final SharedMessageBundle sharedMessageBundle,
                       final SessionActionCellFactory sessionActionCellFactory,
                       final AttachedNiveauEtudeTreeFactory attachedNiveauEtudeTreeFactory,
                       final CellTableStyle cellTableStyle) {
        this.sessionActionCellFactory = sessionActionCellFactory;
        this.dataProvider =  new ListDataProvider<SessionExamenProxy>();
        this.treeModel = attachedNiveauEtudeTreeFactory.create(false, setupDetails(), setupDelete(), setupPrint());
        this.dateFormat = DateTimeFormat.getFormat(GlobalParameters.DATE_FORMAT);
        this.selectionModel = new SingleSelectionModel<SessionExamenProxy>();
        this.sessionsTable = new CellTable<SessionExamenProxy>(15, cellTableStyle);
        this.attachedNiveau = new CellTree(treeModel, null);

        initWidget(uiBinder.createAndBindUi(this));
        initActions();
        initDataGrid();

        dataProvider.addDataDisplay(sessionsTable);
        sessionsTable.setEmptyTableWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.WARNING));
        sessionsTable.setSelectionModel(selectionModel);
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent selectionChangeEvent) {
                getUiHandlers().sessionSelected(selectionModel.getSelectedObject());
            }
        });
    }

    @Override
    public void setData(List<SessionExamenProxy> sessions) {
        sessionsTable.setPageSize(sessions.size());
        dataProvider.getList().clear();
        dataProvider.getList().addAll(sessions);
    }

    @Override
    public void setAttachedNiveau(List<SessionTree> data, SessionStatus status) {
        attachNiveauEtude.setVisible(status == SessionStatus.CREATED);
        treeModel.refreshData(data, status != SessionStatus.CREATED);

        for (int i = 0; i < attachedNiveau.getRootTreeNode().getChildCount(); i++) {
            attachedNiveau.getRootTreeNode().setChildOpen(i, true);
        }
    }

    @UiHandler("newSession")
    void onNewSessionClicked(ClickEvent event) {
        getUiHandlers().newSession();
    }

    @UiHandler("attachNiveauEtude")
    void onAttacheNiveauEtude(ClickEvent event) {
        getUiHandlers().attachNiveauEtude();
    }

    private void initActions() {
        openAction = new Delegate<SessionExamenProxy>() {
            @Override
            public void execute(SessionExamenProxy session) {
                getUiHandlers().openSession(session);
            }
        };

        updateAction = new Delegate<SessionExamenProxy>() {
            @Override
            public void execute(SessionExamenProxy session) {
                getUiHandlers().updateSession(session);
            }
        };

        closeAction = new Delegate<SessionExamenProxy>() {
            @Override
            public void execute(SessionExamenProxy session) {
                getUiHandlers().closeSession(session);
            }
        };

        cancelAction = new Delegate<SessionExamenProxy>() {
            @Override
            public void execute(SessionExamenProxy session) {
                getUiHandlers().cancelSession(session);
            }
        };

        copyAction = new Delegate<SessionExamenProxy>() {
            @Override
            public void execute(SessionExamenProxy session) {
                getUiHandlers().copySession(session);
            }
        };
    }

    private void initDataGrid() {
        TextColumn<SessionExamenProxy> nomColumn = new TextColumn<SessionExamenProxy>() {
            @Override
            public String getValue(SessionExamenProxy object) {
                return object.getNom();
            }
        };
        sessionsTable.addColumn(nomColumn, "Nom");
        sessionsTable.setColumnWidth(nomColumn, 25, Style.Unit.PCT);

        TextColumn<SessionExamenProxy> dateColumn = new TextColumn<SessionExamenProxy>() {
            @Override
            public String getValue(SessionExamenProxy object) {
                if (object.getDateSession() == null) {
                    return "";
                } else {
                    return dateFormat.format(object.getDateSession());
                }
            }
        };
        sessionsTable.addColumn(dateColumn, "Date session");
        sessionsTable.setColumnWidth(dateColumn, 15, Style.Unit.PCT);

        TextColumn<SessionExamenProxy> statutColumn = new TextColumn<SessionExamenProxy>() {
            @Override
            public String getValue(SessionExamenProxy object) {
                return object.getStatus().toString();
            }
        };
        sessionsTable.addColumn(statutColumn, "Statut");
        sessionsTable.setColumnWidth(statutColumn, 10, Style.Unit.PCT);

        actionsCell = sessionActionCellFactory.create(openAction, updateAction, closeAction, cancelAction, copyAction);
        Column<SessionExamenProxy, SessionExamenProxy> actionsColumn =
                new Column<SessionExamenProxy, SessionExamenProxy>(actionsCell) {
            @Override
            public SessionExamenProxy getValue(SessionExamenProxy object) {
                return object;
            }
        };
        actionsColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        sessionsTable.addColumn(actionsColumn, "Actions");
        sessionsTable.setColumnWidth(actionsColumn, 15, Style.Unit.PCT);
    }

    private ActionCell.Delegate<NiveauEtudeNode> setupDetails(){
        return new ActionCell.Delegate<NiveauEtudeNode>() {
            @Override
            public void execute(NiveauEtudeNode object) {
                getUiHandlers().showNiveauEtudeDetail(object);
            }
        };
    }

    private ActionCell.Delegate<NiveauEtudeNode> setupDelete(){
        return new ActionCell.Delegate<NiveauEtudeNode>() {
            @Override
            public void execute(NiveauEtudeNode object) {
                getUiHandlers().deleteNiveauEtude(object);
            }
        };
    }

    private ActionCell.Delegate<NiveauEtudeNode> setupPrint(){
        return new ActionCell.Delegate<NiveauEtudeNode>() {
            @Override
            public void execute(NiveauEtudeNode object) {
                getUiHandlers().printConvocation(object);
            }
        };
    }
}
