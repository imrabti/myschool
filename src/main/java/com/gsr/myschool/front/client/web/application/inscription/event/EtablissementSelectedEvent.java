package com.gsr.myschool.front.client.web.application.inscription.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.gsr.myschool.common.client.proxy.EtablissementScolaireProxy;

public class EtablissementSelectedEvent extends GwtEvent<EtablissementSelectedEvent.EtablissementSelectedHandler> {
    public static Type<EtablissementSelectedHandler> TYPE = new Type<EtablissementSelectedHandler>();

    public interface EtablissementSelectedHandler extends EventHandler {
        void onEtablissementSelected(EtablissementSelectedEvent event);
    }

    private EtablissementScolaireProxy selectedEtablissement;

    public EtablissementSelectedEvent(EtablissementScolaireProxy selectedEtablissement) {
        this.selectedEtablissement = selectedEtablissement;
    }

    public static Type<EtablissementSelectedHandler> getType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, EtablissementScolaireProxy selectedEtablissement) {
        source.fireEvent(new EtablissementSelectedEvent(selectedEtablissement));
    }

    public EtablissementScolaireProxy getSelectedEtablissement() {
        return selectedEtablissement;
    }

    @Override
    public Type<EtablissementSelectedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EtablissementSelectedHandler handler) {
        handler.onEtablissementSelected(this);
    }
}
