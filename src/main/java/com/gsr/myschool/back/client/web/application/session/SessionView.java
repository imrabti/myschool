package com.gsr.myschool.back.client.web.application.session;

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
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.gsr.myschool.back.client.resource.style.CellTableStyle;
import com.gsr.myschool.back.client.web.application.session.renderer.SessionActionCell;
import com.gsr.myschool.back.client.web.application.session.renderer.SessionActionCellFactory;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.SessionExamenProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.EmptyResult;
import com.gsr.myschool.common.shared.constants.GlobalParameters;

import java.util.List;

public class SessionView extends ViewWithUiHandlers<SessionUiHandlers> implements SessionPresenter.MyView {
    public interface Binder extends UiBinder<Widget, SessionView> {
    }

    @UiField(provided = true)
    CellTable<SessionExamenProxy> sessionsTable;

    private final ListDataProvider<SessionExamenProxy> dataProvider;
    private final SessionActionCellFactory sessionActionCellFactory;
    private final DateTimeFormat dateFormat;
    private final SingleSelectionModel<SessionExamenProxy> selectionModel;

    private Delegate<SessionExamenProxy> openAction;
    private Delegate<SessionExamenProxy> updateAction;
    private Delegate<SessionExamenProxy> closeAction;
    private Delegate<SessionExamenProxy> cancelAction;

    private SessionActionCell actionsCell;

    @Inject
    public SessionView(final Binder uiBinder,
                       final SharedMessageBundle sharedMessageBundle,
                       final SessionActionCellFactory sessionActionCellFactory,
                       final UiHandlersStrategy<SessionUiHandlers> uiHandlers,
                       final CellTableStyle cellTableStyle) {
        super(uiHandlers);

        this.sessionActionCellFactory = sessionActionCellFactory;
        this.dataProvider =  new ListDataProvider<SessionExamenProxy>();
        this.dateFormat = DateTimeFormat.getFormat(GlobalParameters.DATE_FORMAT);
        this.selectionModel = new SingleSelectionModel<SessionExamenProxy>();
        this.sessionsTable = new CellTable<SessionExamenProxy>(15, cellTableStyle);

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

    @UiHandler("newSession")
    void onNewSessionClicked(ClickEvent event) {
        getUiHandlers().newSession();
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
    }

    private void initDataGrid() {
        TextColumn<SessionExamenProxy> nomColumn = new TextColumn<SessionExamenProxy>() {
            @Override
            public String getValue(SessionExamenProxy object) {
                return object.getNom();
            }
        };
        sessionsTable.addColumn(nomColumn, "Nom");
        sessionsTable.setColumnWidth(nomColumn, 20, Style.Unit.PCT);

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
        sessionsTable.setColumnWidth(dateColumn, 20, Style.Unit.PCT);

        TextColumn<SessionExamenProxy> statutColumn = new TextColumn<SessionExamenProxy>() {
            @Override
            public String getValue(SessionExamenProxy object) {
                return object.getStatus().toString();
            }
        };
        sessionsTable.addColumn(statutColumn, "Statut");
        sessionsTable.setColumnWidth(statutColumn, 10, Style.Unit.PCT);

        actionsCell = sessionActionCellFactory.create(openAction, updateAction, closeAction, cancelAction);
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
}
