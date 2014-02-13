package com.gsr.myschool.back.client.request;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.gsr.myschool.common.client.proxy.*;
import com.gsr.myschool.common.shared.type.SessionStatus;
import com.gsr.myschool.server.service.impl.SessionServiceImpl;
import com.gsr.myschool.server.util.SpringServiceLocator;

import java.util.List;

@Service(value = SessionServiceImpl.class, locator = SpringServiceLocator.class)
public interface SessionRequest extends RequestContext {
    Request<Void> createNewSession(SessionExamenProxy sessionExamen);

    Request<Void> updateSession(SessionExamenProxy sessionExamen);

    Request<Void> attacheToSession(Long sessionId, Long niveauEtudeId);

    Request<Void> updateHoraire(List<String> horaires);

    Request<Void> deleteNiveauEtude(Long sessionId, Long niveauEtudeId);

    Request<Boolean> openSession(Long sessionId);

    Request<Void> cancelOrDeleteSession(Long sessionId);

    Request<Void> copySession(Long sessionId);

    Request<List<SessionNiveauEtudeProxy>> findAllMatieresByNiveauEtude(Long sessionId, Long niveauEtudeId);

    Request<List<SessionNiveauEtudeProxy>> findAllNiveauEtudeBySession(Long sessionId);

    Request<List<SessionExamenProxy>> findAllSessions();

    Request<List<SessionExamenProxy>> findSessionByNE(NiveauEtudeProxy niveau);

    Request<List<SessionExamenProxy>> findAllSessionsWithStatus(SessionStatus sessionStatus);

    Request<List<SessionExamenProxy>> findAllSessionsWithStatusAndAnneeScolaire(SessionStatus sessionStatus,
                                                                                ValueListProxy anneeScolaire);

    Request<List<SessionExamenProxy>> findAllSessions(List<Integer> sessionIdList);

    Request<Boolean> affecter(DossierProxy dossier, SessionExamenProxy session);

    Request<Boolean> desaffecter(DossierProxy dossier);

    Request<Boolean> launchSession(SessionExamenProxy session, String link);

    Request<DossierSessionProxy> findByDossier(DossierProxy dossier);

    Request<Void> sendEmailConvocation(DossierSessionProxy session, String link);
}
