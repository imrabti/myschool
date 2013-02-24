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

package com.gsr.myschool.back.client.web.application.usergsr;

import com.github.gwtbootstrap.client.ui.CellTable;
import com.google.common.base.Strings;
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
import com.gsr.myschool.back.client.web.application.usergsr.renderer.AdminUserActionCell;
import com.gsr.myschool.back.client.web.application.usergsr.renderer.AdminUserActionCellFactory;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.AdminUserProxy;
import com.gsr.myschool.common.shared.constants.GlobalParameters;

import java.util.List;

public class AdminUserAccountView extends ViewWithUiHandlers<AdminUserAccountUiHandlers>
        implements AdminUserAccountPresenter.MyView {
    public interface Binder extends UiBinder<Widget, AdminUserAccountView> {
    }

    @UiField
    CellTable<AdminUserProxy> userGsrTable;

    private final DateTimeFormat dateFormat;
    private final ListDataProvider<AdminUserProxy> dataProvider;
    private final AdminUserActionCellFactory actionCellFactoryAdmin;

    private Delegate<AdminUserProxy> editAccount;
    private Delegate<AdminUserProxy> delete;

    @Inject
    public AdminUserAccountView(final Binder uiBinder,
			final UiHandlersStrategy<AdminUserAccountUiHandlers> uiHandlers,
			final AdminUserActionCellFactory actionCellFactoryAdmin) {
        super(uiHandlers);

        this.actionCellFactoryAdmin = actionCellFactoryAdmin;

        initWidget(uiBinder.createAndBindUi(this));
        initActions();
        initDataGrid();

        dataProvider = new ListDataProvider<AdminUserProxy>();
        dataProvider.addDataDisplay(userGsrTable);
        dateFormat = DateTimeFormat.getFormat(GlobalParameters.DATE_FORMAT);
    }

    @Override
    public void setData(List<AdminUserProxy> data) {
        userGsrTable.setPageSize(data.size());
        dataProvider.getList().clear();
        dataProvider.getList().addAll(data);
    }

    @UiHandler("add")
    void onAddClicked(ClickEvent event) {
        getUiHandlers().addUser();
    }

    private void initActions() {
        editAccount = new Delegate<AdminUserProxy>() {
            @Override
            public void execute(AdminUserProxy user) {
                getUiHandlers().update(user);
            }
        };
        delete = new Delegate<AdminUserProxy>() {
            @Override
            public void execute(AdminUserProxy user) {
                getUiHandlers().delete(user);
            }
        };
    }

    private void initDataGrid() {
        TextColumn<AdminUserProxy> loginColumn = new TextColumn<AdminUserProxy>() {
            @Override
            public String getValue(AdminUserProxy object) {
                return object.getEmail();
            }
        };
        loginColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        userGsrTable.addColumn(loginColumn, "Email");
        userGsrTable.setColumnWidth(loginColumn, 20, Style.Unit.PCT);

		TextColumn<AdminUserProxy> fNameColumn = new TextColumn<AdminUserProxy>() {
			@Override
			public String getValue(AdminUserProxy object) {
				return object.getFirstName();
			}
		};
		fNameColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		userGsrTable.addColumn(fNameColumn, "Prénom");
		userGsrTable.setColumnWidth(fNameColumn, 20, Style.Unit.PCT);

		TextColumn<AdminUserProxy> lNameColumn = new TextColumn<AdminUserProxy>() {
            @Override
            public String getValue(AdminUserProxy object) {
                return object.getLastName();
            }
        };
		lNameColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        userGsrTable.addColumn(lNameColumn, "Nom");
        userGsrTable.setColumnWidth(lNameColumn, 20, Style.Unit.PCT);

        TextColumn<AdminUserProxy> roleColumn = new TextColumn<AdminUserProxy>() {
            @Override
            public String getValue(AdminUserProxy object) {
                if(object.getAuthority() == null) return "";
                return object.getAuthority().toString();
            }
        };
        roleColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        userGsrTable.addColumn(roleColumn, "Rôle");
        userGsrTable.setColumnWidth(roleColumn, 20, Style.Unit.PCT);

        TextColumn<AdminUserProxy> statusColumn = new TextColumn<AdminUserProxy>() {
            @Override
            public String getValue(AdminUserProxy object) {
                if(object.getStatus() == null) return "";
                return object.getStatus().toString();
            }
        };
        statusColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        userGsrTable.addColumn(statusColumn, "Statut");
        userGsrTable.setColumnWidth(statusColumn, 20, Style.Unit.PCT);

        TextColumn<AdminUserProxy> submittedColumn = new TextColumn<AdminUserProxy>() {
            @Override
            public String getValue(AdminUserProxy object) {
                if(object.getCreated() == null) return "";
                return dateFormat.format(object.getCreated());
            }
        };
        submittedColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        userGsrTable.addColumn(submittedColumn, "Date de Création");
        userGsrTable.setColumnWidth(submittedColumn, 30, Style.Unit.PCT);

        AdminUserActionCell actionsCellAdmin = actionCellFactoryAdmin.create(editAccount, delete);
        Column<AdminUserProxy, AdminUserProxy> actionsColumn = new
                Column<AdminUserProxy, AdminUserProxy>(actionsCellAdmin) {
            @Override
            public AdminUserProxy getValue(AdminUserProxy object) {
                return object;
            }
        };
        actionsColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        userGsrTable.addColumn(actionsColumn, "Actions");
        userGsrTable.setColumnWidth(actionsColumn, 10, Style.Unit.PCT);
    }
}
