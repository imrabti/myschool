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

package com.gsr.myschool.back.client.web.application.affectation.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.gsr.myschool.common.client.proxy.DossierProxy;

public class DossierAffectedEvent extends GwtEvent<DossierAffectedEvent.DossierAffectedHandler> {
    public interface DossierAffectedHandler extends EventHandler {
        void onDossierAffected(DossierAffectedEvent event);
    }

    public static GwtEvent.Type<DossierAffectedHandler> TYPE = new GwtEvent.Type<DossierAffectedHandler>();

    private DossierProxy dossier;

    public DossierAffectedEvent() {
    }

    public DossierAffectedEvent(DossierProxy dossier) {
        this.dossier = dossier;
    }

    public static void fire(HasHandlers source, DossierProxy value) {
        source.fireEvent(new DossierAffectedEvent(value));
    }

    public static void fire(HasHandlers source) {
        source.fireEvent(new DossierAffectedEvent());
    }

    public DossierProxy getDossier() {
        return dossier;
    }

    @Override
    public GwtEvent.Type<DossierAffectedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DossierAffectedHandler handler) {
        handler.onDossierAffected(this);
    }
}