package com.gsr.myschool.front.client.web.application.inscription.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.gsr.myschool.front.client.web.application.inscription.InscriptionDetailPresenter;

public class DossierSubmitEvent extends GwtEvent<DossierSubmitEvent.DossierSubmitHandler> {
    public static Type<DossierSubmitHandler> TYPE = new Type<DossierSubmitHandler>();
    public static Type<DossierSubmitHandler> SECOND_TYPE = new Type<DossierSubmitHandler>();

    public interface DossierSubmitHandler extends EventHandler {
        void onDossierSubmit(DossierSubmitEvent event);
    }

    private Boolean agreement;

    public DossierSubmitEvent(Boolean agreement) {
        this.agreement = agreement;
    }

    public static Type<DossierSubmitHandler> getType() {
        return TYPE;
    }

    public static Type<DossierSubmitHandler> getSecondType() {
        return SECOND_TYPE;
    }

    public static void fire(HasHandlers source, Boolean agreement) {
        source.fireEvent(new DossierSubmitEvent(agreement));
    }

    public Boolean getAgreement() {
        return agreement;
    }

    @Override
    public Type<DossierSubmitHandler> getAssociatedType() {
        if (getSource() instanceof InscriptionDetailPresenter) {
            return SECOND_TYPE;
        } else {
            return TYPE;
        }
    }

    @Override
    protected void dispatch(DossierSubmitHandler handler) {
        handler.onDossierSubmit(this);
    }
}
