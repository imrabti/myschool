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
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.shared.type.DossierStatus;

public class AffectationActionCell extends AbstractCell<DossierProxy> {
    @UiTemplate("AffectationActionCellNotAffected.ui.xml")
    public interface RendererNotAffectedDossier extends UiRenderer {
        void render(SafeHtmlBuilder sb);

        void onBrowserEvent(AffectationActionCell o, NativeEvent e, Element p);
    }

    @UiTemplate("AffectationActionCellAffected.ui.xml")
    public interface RendererAffectedDossier extends UiRenderer {
        void render(SafeHtmlBuilder sb);

        void onBrowserEvent(AffectationActionCell o, NativeEvent e, Element p);
    }

    @UiTemplate("AffectationActionCellInvited.ui.xml")
    public interface RendererInvitedDossier extends UiRenderer {
        void render(SafeHtmlBuilder sb);

        void onBrowserEvent(AffectationActionCell o, NativeEvent e, Element p);
    }

    private final RendererAffectedDossier affectedRenderer;
    private final RendererNotAffectedDossier notAffectedRenderer;
    private final RendererInvitedDossier invitedRenderer;

    private ActionCell.Delegate<DossierProxy> viewDetails;
    private ActionCell.Delegate<DossierProxy> affecter;
    private ActionCell.Delegate<DossierProxy> desaffecter;
    private ActionCell.Delegate<DossierProxy> imprimer;

    private DossierProxy selectedObject;

    @Inject
    public AffectationActionCell(final RendererAffectedDossier affectedRenderer,
                                 final RendererNotAffectedDossier notAffectedRenderer,
                                 final RendererInvitedDossier invitedRenderer,
                                 @Assisted("viewDetails") ActionCell.Delegate<DossierProxy> viewDetails,
                                 @Assisted("affecter") ActionCell.Delegate<DossierProxy> affecter,
                                 @Assisted("desaffecter") ActionCell.Delegate<DossierProxy> desaffecter,
                                 @Assisted("imprimer") ActionCell.Delegate<DossierProxy> imprimer) {
        super(BrowserEvents.CLICK);

        this.affectedRenderer = affectedRenderer;
        this.notAffectedRenderer = notAffectedRenderer;
        this.invitedRenderer = invitedRenderer;
        this.viewDetails = viewDetails;
        this.affecter = affecter;
        this.desaffecter = desaffecter;
        this.imprimer = imprimer;
    }

    @Override
    public void onBrowserEvent(Context context, Element parent, DossierProxy value, NativeEvent event,
                               ValueUpdater<DossierProxy> valueUpdater) {
        selectedObject = value;
        switch (selectedObject.getStatus()) {
            case INVITED_TO_TEST:
                invitedRenderer.onBrowserEvent(this, event, parent);
                break;
            case AFFECTED:
                affectedRenderer.onBrowserEvent(this, event, parent);
                break;
            case ACCEPTED_FOR_TEST:
                notAffectedRenderer.onBrowserEvent(this, event, parent);
                break;
            default:
                notAffectedRenderer.onBrowserEvent(this, event, parent);
                break;
        }
    }

    @Override
    public void render(Context context, DossierProxy value, SafeHtmlBuilder builder) {
        selectedObject = value;
        switch (selectedObject.getStatus()) {
            case INVITED_TO_TEST:
                invitedRenderer.render(builder);
                break;
            case AFFECTED:
                affectedRenderer.render(builder);
                break;
            case ACCEPTED_FOR_TEST:
                notAffectedRenderer.render(builder);
                break;
            default:
                notAffectedRenderer.render(builder);
                break;
        }
    }

    @UiHandler({"viewDetails"})
    void onPreviewClicked(ClickEvent event) {
        viewDetails.execute(selectedObject);
    }

    @UiHandler({"affecter"})
    void onAffecterClicked(ClickEvent event) {
        affecter.execute(selectedObject);
    }

    @UiHandler({"desaffecter"})
    void onDesafecterClicked(ClickEvent event) {
        if (selectedObject.getStatus() == DossierStatus.INVITED_TO_TEST
                || selectedObject.getStatus() == DossierStatus.AFFECTED) {
            desaffecter.execute(selectedObject);
        }
    }

    @UiHandler({"imprimer"})
    void onPrintClicked(ClickEvent event) {
        if (selectedObject.getStatus() == DossierStatus.INVITED_TO_TEST) {
            imprimer.execute(selectedObject);
        }
    }
}
