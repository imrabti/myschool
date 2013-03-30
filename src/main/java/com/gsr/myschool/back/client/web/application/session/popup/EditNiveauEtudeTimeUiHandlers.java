package com.gsr.myschool.back.client.web.application.session.popup;

import com.gsr.myschool.common.client.proxy.SessionNiveauEtudeProxy;
import com.gwtplatform.mvp.client.UiHandlers;

import java.util.List;

public interface EditNiveauEtudeTimeUiHandlers extends UiHandlers {
    void saveHoraires(List<SessionNiveauEtudeProxy> matieres);

}
