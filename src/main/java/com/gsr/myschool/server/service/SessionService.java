package com.gsr.myschool.server.service;

import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.business.core.NiveauEtude;
import com.gsr.myschool.server.business.core.SessionExamen;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SessionService {
    void createNewSession(SessionExamen sessionExamen);

    void updateSession(SessionExamen sessionExamen);

    List<SessionExamen> findAllSessions();

    @Transactional(readOnly = true)
    List<SessionExamen> findSessionByNE(NiveauEtude niveau);

    Boolean affecter(Dossier dossier, SessionExamen session);
}
