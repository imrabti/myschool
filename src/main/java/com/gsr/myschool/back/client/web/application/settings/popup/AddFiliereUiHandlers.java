package com.gsr.myschool.back.client.web.application.settings.popup;

import com.gsr.myschool.common.client.proxy.FiliereProxy;
import com.gwtplatform.mvp.client.UiHandlers;

public interface AddFiliereUiHandlers extends UiHandlers {
    void saveFiliere(FiliereProxy filiere);
}
