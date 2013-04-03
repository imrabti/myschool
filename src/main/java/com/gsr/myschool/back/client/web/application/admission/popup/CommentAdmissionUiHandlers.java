package com.gsr.myschool.back.client.web.application.admission.popup;

import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gwtplatform.mvp.client.UiHandlers;

public interface CommentAdmissionUiHandlers extends UiHandlers {
    void setAdmissionComment(String comment);

    void loadDossier(DossierProxy dossierProxy, Boolean accepted);
}
