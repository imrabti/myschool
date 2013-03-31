package com.gsr.myschool.server.service;

import com.gsr.myschool.common.shared.exception.AffectationClosedException;
import com.gsr.myschool.common.shared.exception.SessionEmptyException;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.business.DossierSession;
import com.gsr.myschool.server.business.core.NiveauEtude;
import com.gsr.myschool.server.business.core.SessionExamen;
import com.gsr.myschool.server.business.core.SessionNiveauEtude;

import java.util.List;

public interface SessionService {
    void createNewSession(SessionExamen sessionExamen);

    void updateSession(SessionExamen sessionExamen);

    void attacheToSession(Long sessionId, Long niveauEtudeId);

    void updateHoraire(List<String> horaires);

    void deleteNiveauEtude(Long niveauEtudeId);

    Boolean openSession(Long sessionId) throws SessionEmptyException;

    void cancelOrDeleteSession(Long sessionId);

    List<SessionNiveauEtude> findAllMatieresByNiveauEtude(Long niveauEtudeId);

    List<SessionNiveauEtude> findAllNiveauEtudeBySession(Long sessionId);

    List<SessionExamen> findAllSessions();

    List<SessionExamen> findSessionByNE(NiveauEtude niveau);

    Boolean affecter(Dossier dossier, SessionExamen session) throws AffectationClosedException;

    Boolean desaffecter(Dossier dossier) throws AffectationClosedException;

    List<SessionExamen> findAllOpenedSessions();

    Boolean launchSession(SessionExamen session, String link);

    DossierSession findByDossier(Dossier dossier);
}
