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

package com.gsr.myschool.back.client.web.application.settings.renderer;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.TreeViewModel;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.gsr.myschool.common.client.proxy.FiliereProxy;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gsr.myschool.common.client.util.ValueList;

public class NiveauEtudeInfosTree implements TreeViewModel {
    private final ValueList valueList;
    private Delegate<NiveauEtudeProxy> showDetails;

    @Inject
    public NiveauEtudeInfosTree(final ValueList valueList,
                                @Assisted Delegate<NiveauEtudeProxy> showDetails) {
        this.valueList = valueList;
        this.showDetails = showDetails;
    }

    @Override
    public <T> NodeInfo<?> getNodeInfo(T t) {
        if (t == null) {
            ListDataProvider<FiliereProxy> dataProvider = new ListDataProvider<FiliereProxy>();
            dataProvider.setList(valueList.getFiliereList());
            Cell<FiliereProxy> myCell = new AbstractCell<FiliereProxy>() {
                @Override
                public void render(Context context, FiliereProxy value, SafeHtmlBuilder sb) {
                    if (value != null) {
                        sb.appendEscaped(value.getNom());
                    }
                }
            };
            return new DefaultNodeInfo<FiliereProxy>(dataProvider, myCell);
        }
        if (t instanceof FiliereProxy) {
            ListDataProvider<NiveauEtudeProxy> dataProvider = new ListDataProvider<NiveauEtudeProxy>();
            dataProvider.setList(valueList.getNiveauEtudeList(((FiliereProxy) t).getNom()));
            Cell<NiveauEtudeProxy> myCell = new AbstractCell<NiveauEtudeProxy>(BrowserEvents.DBLCLICK) {
                @Override
                public void render(Context context, NiveauEtudeProxy value, SafeHtmlBuilder sb) {
                    if (value != null) {
                        sb.appendEscaped(value.getNom());
                    }
                }

                @Override
                public void onBrowserEvent(Context context, Element parent, NiveauEtudeProxy value,
                                           NativeEvent event, ValueUpdater<NiveauEtudeProxy> valueUpdater) {
                    super.onBrowserEvent(context, parent, value, event, valueUpdater);
                    if (value == null) {
                        return;
                    }
                    if (BrowserEvents.DBLCLICK.equals(event.getType())) {
                        showDetails.execute(value);
                    }
                }
            };
            return new DefaultNodeInfo<NiveauEtudeProxy>(dataProvider, myCell);
        }
        return null;
    }

    @Override
    public boolean isLeaf(Object o) {
        return o instanceof NiveauEtudeProxy;
    }
}
