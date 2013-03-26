package com.gsr.myschool.back.client.web.application.session;

import com.github.gwtbootstrap.client.ui.CellTable;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;
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

    @UiField
    CellTable<SessionExamenProxy> sessionsTable;

    private final ListDataProvider<SessionExamenProxy> dataProvider;
    private final DateTimeFormat dateFormat;

    @Inject
    public SessionView(final Binder uiBinder,
                       final SharedMessageBundle sharedMessageBundle,
                       final UiHandlersStrategy<SessionUiHandlers> uiHandlers) {
        super(uiHandlers);

        dataProvider =  new ListDataProvider<SessionExamenProxy>();
        dateFormat = DateTimeFormat.getFormat(GlobalParameters.DATE_FORMAT);

        initWidget(uiBinder.createAndBindUi(this));

        dataProvider.addDataDisplay(sessionsTable);
        sessionsTable.setEmptyTableWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.WARNING));
    }

    @Override
    public void setData(List<SessionExamenProxy> sessions) {
        sessionsTable.setPageSize(sessions.size());
        dataProvider.getList().clear();
        dataProvider.getList().addAll(sessions);
    }
}
