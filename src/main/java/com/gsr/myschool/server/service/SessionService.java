package com.gsr.myschool.server.service;

import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.business.core.NiveauEtude;
import com.gsr.myschool.server.business.core.SessionExamen;

import java.util.List;

public interface SessionService {
    void createNewSession(SessionExamen sessionExamen);

    void updateSession(SessionExamen sessionExamen);

    void attacheToSession(Long sessionId, Long niveauEtudeId);

    List<SessionExamen> findAllSessions();

    List<SessionExamen> findSessionByNE(NiveauEtude niveau);

    Boolean affecter(Dossier dossier, SessionExamen session);

    Boolean desaffecter(Dossier dossier);

    List<SessionExamen> findAllOpenedSessions();
}
