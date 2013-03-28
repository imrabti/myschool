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

package com.gsr.myschool.back.client.web.application.affectation.renderer;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ActionCell;
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
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.shared.type.DossierStatus;

public class AffectationActionCell extends AbstractCell<DossierProxy> {
    public interface Renderer extends UiRenderer {
        void render(SafeHtmlBuilder sb);

        void onBrowserEvent(AffectationActionCell o, NativeEvent e, Element p);
    }

    private final Renderer uiRenderer;

    private ActionCell.Delegate<DossierProxy> viewDetails;
    private ActionCell.Delegate<DossierProxy> print;

    private DossierProxy selectedObject;

    @Inject
    public AffectationActionCell(final Renderer uiRenderer,
                                    @Assisted("viewDetails") ActionCell.Delegate<DossierProxy> viewDetails,
                                    @Assisted("print") ActionCell.Delegate<DossierProxy> print) {
        super(BrowserEvents.CLICK);

        this.uiRenderer = uiRenderer;
        this.viewDetails = viewDetails;
        this.print = print;
    }

    @Override
    public void onBrowserEvent(Context context, Element parent, DossierProxy value, NativeEvent event,
                               ValueUpdater<DossierProxy> valueUpdater) {
        selectedObject = value;
        uiRenderer.onBrowserEvent(this, event, parent);
    }

    @Override
    public void render(Context context, DossierProxy value, SafeHtmlBuilder builder) {
        uiRenderer.render(builder);
    }

    @UiHandler({"viewDetails"})
    void onPreviewClicked(ClickEvent event) {
        viewDetails.execute(selectedObject);
    }

    @UiHandler({"print"})
    void onPrintClicked(ClickEvent event) {
        if (!selectedObject.getStatus().equals(DossierStatus.CREATED)) {
            print.execute(selectedObject);
        }
    }
}
