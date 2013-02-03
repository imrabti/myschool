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

package com.gsr.myschool.front.client.web.application.inbox;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.InboxProxy;
import com.gsr.myschool.common.client.proxy.ValueListProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.EmptyResult;
import com.gsr.myschool.front.client.web.application.inbox.renderer.InboxCell;

import java.util.ArrayList;
import java.util.List;

public class InboxView extends ViewWithUiHandlers<InboxUiHandlers> implements InboxPresenter.MyView {
    public interface Binder extends UiBinder<Widget, InboxView> {
    }

    @UiField(provided = true)
    CellList inboxTable;

    private final ListDataProvider<InboxProxy> dataProvider;
    private final MultiSelectionModel<InboxProxy> multipleSelectionModel;

    @Inject
    public InboxView(final Binder uiBinder,
                     final UiHandlersStrategy<InboxUiHandlers> uiHandlers,
                     final SharedMessageBundle sharedMessageBundle,
                     final InboxCell inboxCell) {
        super(uiHandlers);

        this.dataProvider = new ListDataProvider<InboxProxy>();
        this.multipleSelectionModel = new MultiSelectionModel<InboxProxy>();
        this.inboxTable = new CellList<InboxProxy>(inboxCell);

        initWidget(uiBinder.createAndBindUi(this));

        dataProvider.addDataDisplay(inboxTable);
        inboxTable.setSelectionModel(multipleSelectionModel);
        inboxTable.setEmptyListWidget(new EmptyResult(sharedMessageBundle.noResultFound(),
                AlertType.INFO));
    }

    @Override
    public void setData(List<InboxProxy> response) {
        dataProvider.getList().clear();
        dataProvider.getList().addAll(response);
        //dataProvider.getList().addAll(new ArrayList<InboxProxy>());
    }
}
