package com.gsr.myschool.front.client.web.application.inscription.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.gsr.myschool.common.client.proxy.EtablissementScolaireProxy;
import com.gsr.myschool.front.client.web.application.inscription.WizardStep;

public class EtablissementSelectedEvent extends GwtEvent<EtablissementSelectedEvent.EtablissementSelectedHandler> {
    public static Type<EtablissementSelectedHandler> TYPE = new Type<EtablissementSelectedHandler>();

    public interface EtablissementSelectedHandler extends EventHandler {
        void onEtablissementSelected(EtablissementSelectedEvent event);
    }

    private EtablissementScolaireProxy selectedEtablissement;
    private WizardStep step;

    public EtablissementSelectedEvent(EtablissementScolaireProxy selectedEtablissement, WizardStep step) {
        this.selectedEtablissement = selectedEtablissement;
        this.step = step;
    }

    public static Type<EtablissementSelectedHandler> getType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, EtablissementScolaireProxy selectedEtablissement, WizardStep step) {
        source.fireEvent(new EtablissementSelectedEvent(selectedEtablissement, step));
    }

    public EtablissementScolaireProxy getSelectedEtablissement() {
        return selectedEtablissement;
    }

    public WizardStep getStep() {
        return step;
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
