package com.gsr.myschool.back.client.web.application.confirmationTest.popup;

import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gwtplatform.mvp.client.UiHandlers;

public interface DenyForTestUiHandlers extends UiHandlers {
    void saveReason(String reason);

    void loadDossier(DossierProxy dossierProxy);
}
