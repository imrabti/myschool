package com.gsr.myschool.back.client.web.application.session;

import com.gsr.myschool.common.client.proxy.SessionExamenProxy;
import com.gsr.myschool.common.shared.dto.NiveauEtudeNode;
import com.gwtplatform.mvp.client.UiHandlers;

public interface SessionUiHandlers extends UiHandlers {
    void sessionSelected(SessionExamenProxy session);

    void newSession();

    void attachNiveauEtude();

    void openSession(SessionExamenProxy session);

    void updateSession(SessionExamenProxy session);

    void closeSession(SessionExamenProxy session);

    void cancelSession(SessionExamenProxy session);

    void showNiveauEtudeDetail(NiveauEtudeNode niveauEtudeNode);

    void deleteNiveauEtude(NiveauEtudeNode niveauEtudeNode);

    void printConvocation(NiveauEtudeNode niveauEtudeNode);
}
