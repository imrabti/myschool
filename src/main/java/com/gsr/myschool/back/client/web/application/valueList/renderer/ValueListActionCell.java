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

package com.gsr.myschool.back.client.web.application.valueList.renderer;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiRenderer;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.gsr.myschool.common.client.proxy.ValueListProxy;

public class ValueListActionCell extends AbstractCell<ValueListProxy> {
    public interface Renderer extends UiRenderer {
        void render(SafeHtmlBuilder sb);

        void onBrowserEvent(ValueListActionCell o, NativeEvent e, Element p);
    }

    private final Renderer uiRenderer;
    private Delegate<ValueListProxy> delete;
    private Delegate<ValueListProxy> modify;
    private ValueListProxy selectedObject;

    @Inject
    public ValueListActionCell(final Renderer uiRenderer,
                               @Assisted("delete") Delegate<ValueListProxy> delete,
                               @Assisted("modify") Delegate<ValueListProxy> modify) {
        super(BrowserEvents.CLICK);

        this.uiRenderer = uiRenderer;
        this.delete = delete;
        this.modify = modify;
    }

    @Override
    public void onBrowserEvent(Context context, Element parent, ValueListProxy value, NativeEvent event,
                               ValueUpdater<ValueListProxy> valueUpdater) {
        selectedObject = value;
        uiRenderer.onBrowserEvent(this, event, parent);
    }

    @Override
    public void render(Context context, ValueListProxy valueTypeProxy, SafeHtmlBuilder safeHtmlBuilder) {
        uiRenderer.render(safeHtmlBuilder);
    }

    @UiHandler({"delete"})
    void onDeleteClicked(ClickEvent event) {
        delete.execute(selectedObject);
    }

    @UiHandler({"modify"})
    void onModifyClicked(ClickEvent event) {
        modify.execute(selectedObject);
    }
}

