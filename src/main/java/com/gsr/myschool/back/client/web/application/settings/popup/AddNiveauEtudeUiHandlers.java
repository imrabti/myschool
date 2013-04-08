package com.gsr.myschool.back.client.web.application.settings.popup;

import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gwtplatform.mvp.client.UiHandlers;

public interface AddNiveauEtudeUiHandlers extends UiHandlers {
    void saveNiveauEtude(NiveauEtudeProxy niveauEtude);
}
