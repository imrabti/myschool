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

package com.gsr.myschool.back.client.web.application.user;

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
import com.gsr.myschool.back.client.web.application.user.renderer.UserAccountActionCell;
import com.gsr.myschool.back.client.web.application.user.renderer.UserAccountActionCellFactory;
import com.gsr.myschool.back.client.web.application.user.ui.UserFilterEditor;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.DossierFilterDTOProxy;
import com.gsr.myschool.common.client.proxy.UserFilterDTOProxy;
import com.gsr.myschool.common.client.proxy.UserProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.EmptyResult;

import java.util.List;

public class UserAccountView extends ViewWithUiHandlers<UserAccountUiHandlers>
        implements UserAccountPresenter.MyView {
    public interface Binder extends UiBinder<Widget, UserAccountView> {
    }

    @UiField(provided = true)
    UserFilterEditor userFilterEditor;
    @UiField
    CellTable<UserProxy> userPortalTable;

    private final DateTimeFormat dateFormat;
    private final ListDataProvider<UserProxy> dataProvider;
    private final UserAccountActionCellFactory actionCellFactory;

    private Delegate<UserProxy> editAccount;

    @Inject
    public UserAccountView(final Binder uiBinder,
                           final UiHandlersStrategy<UserAccountUiHandlers> uiHandlers,
                           final UserFilterEditor userFilterEditor,
                           final SharedMessageBundle sharedMessageBundle,
                           final UserAccountActionCellFactory actionCellFactory) {
        super(uiHandlers);

        this.actionCellFactory = actionCellFactory;
        this.userFilterEditor = userFilterEditor;

        initWidget(uiBinder.createAndBindUi(this));
        initActions();
        initDataGrid();

        dataProvider = new ListDataProvider<UserProxy>();
        dataProvider.addDataDisplay(userPortalTable);
        dateFormat = DateTimeFormat.getFormat("LLL d yyyy");

        userPortalTable.setEmptyTableWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.INFO));
    }

    @Override
    public void setData(List<UserProxy> data) {
        dataProvider.getList().clear();
        dataProvider.getList().addAll(data);
    }

    @Override
    public void editUserFilter(UserFilterDTOProxy userFilter) {
        userFilterEditor.edit(userFilter);
    }

    @UiHandler("search")
    void onSearch(ClickEvent event) {
        getUiHandlers().searchWithFilter(userFilterEditor.get());
    }

    private void initActions() {
        editAccount = new Delegate<UserProxy>() {
            @Override
            public void execute(UserProxy userProxy) {
                getUiHandlers().update(userProxy);
            }
        };
    }

    private void initDataGrid() {
        TextColumn<UserProxy> refColumn = new TextColumn<UserProxy>() {
            @Override
            public String getValue(UserProxy object) {
                return object.getFirstName() + " " + object.getLastName();
            }
        };
        refColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        userPortalTable.addColumn(refColumn, "Nom");
        userPortalTable.setColumnWidth(refColumn, 20, Style.Unit.PCT);

        TextColumn<UserProxy> emailColumn = new TextColumn<UserProxy>() {
            @Override
            public String getValue(UserProxy object) {
                return object.getEmail();
            }
        };
        emailColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        userPortalTable.addColumn(emailColumn, "Email");
        userPortalTable.setColumnWidth(emailColumn, 40, Style.Unit.PCT);

        TextColumn<UserProxy> submittedColumn = new TextColumn<UserProxy>() {
            @Override
            public String getValue(UserProxy object) {
                if(object.getCreated() == null) return "";
                return dateFormat.format(object.getCreated());
            }
        };
        submittedColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        userPortalTable.addColumn(submittedColumn, "Date de création");
        userPortalTable.setColumnWidth(submittedColumn, 30, Style.Unit.PCT);

        UserAccountActionCell actionsCell = actionCellFactory.create(editAccount);
        Column<UserProxy, UserProxy> actionsColumn = new
                Column<UserProxy, UserProxy>(actionsCell) {
                    @Override
                    public UserProxy getValue(UserProxy object) {
                        return object;
                    }
                };
        actionsColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        userPortalTable.addColumn(actionsColumn, "Actions");
        userPortalTable.setColumnWidth(actionsColumn, 10, Style.Unit.PCT);
    }
}
