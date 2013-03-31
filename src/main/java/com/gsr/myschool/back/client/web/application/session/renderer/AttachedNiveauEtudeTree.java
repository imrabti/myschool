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

package com.gsr.myschool.back.client.web.application.session.renderer;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.TreeViewModel;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.gsr.myschool.common.shared.dto.NiveauEtudeNode;
import com.gsr.myschool.common.shared.dto.SessionTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttachedNiveauEtudeTree implements TreeViewModel {
    private final List<SessionTree> treeModel;
    private final ListDataProvider<SessionTree> rootDataProvider;

    private Boolean readOnly;
    private Delegate<NiveauEtudeNode> detail;
    private Delegate<NiveauEtudeNode> delete;
    private Delegate<NiveauEtudeNode> print;
    private NiveauEtudeNodeCellFactory niveauEtudeNodeCellFactory;

    @Inject
    public AttachedNiveauEtudeTree(NiveauEtudeNodeCellFactory niveauEtudeNodeCellFactory,
                                   @Assisted("readyOnly") Boolean readOnly,
                                   @Assisted("detail") Delegate<NiveauEtudeNode> detail,
                                   @Assisted("delete") Delegate<NiveauEtudeNode> delete,
                                   @Assisted("print") Delegate<NiveauEtudeNode> print) {
        this.niveauEtudeNodeCellFactory = niveauEtudeNodeCellFactory;
        this.treeModel = new ArrayList<SessionTree>();
        this.rootDataProvider = new ListDataProvider<SessionTree>();
        this.readOnly = readOnly;
        this.detail = detail;
        this.delete = delete;
        this.print = print;
    }

    @Override
    public <T> NodeInfo<?> getNodeInfo(T t) {
        if (t == null) {
            rootDataProvider.setList(treeModel);

            Cell<SessionTree> myCell = new AbstractCell<SessionTree>() {
                @Override
                public void render(Context context, SessionTree value, SafeHtmlBuilder sb) {
                    if (value != null) {
                        sb.appendEscaped(value.getName());
                    }
                }
            };
            return new DefaultNodeInfo<SessionTree>(rootDataProvider, myCell);
        }

        if (t instanceof SessionTree) {
            SessionTree rootNode = (SessionTree) t;
            ListDataProvider<NiveauEtudeNode> dataProvider = new ListDataProvider<NiveauEtudeNode>();
            ArrayList<NiveauEtudeNode> nodes = new ArrayList<NiveauEtudeNode>(rootNode.getNiveauEtudeNodes().values());
            Collections.sort(nodes);
            dataProvider.setList(nodes);

            NiveauEtudeNodeCell niveauEtudeCell = niveauEtudeNodeCellFactory.create(readOnly, detail, delete, print);
            return new DefaultNodeInfo<NiveauEtudeNode>(dataProvider, niveauEtudeCell);
        }
        return null;
    }

    @Override
    public boolean isLeaf(Object o) {
        return o instanceof NiveauEtudeNode;
    }

    public void refreshData(List<SessionTree> data, Boolean readOnly) {
        this.readOnly = readOnly;

        treeModel.clear();
        treeModel.addAll(data);

        rootDataProvider.setList(treeModel);
    }
}
