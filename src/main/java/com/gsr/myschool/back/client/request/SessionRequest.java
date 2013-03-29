package com.gsr.myschool.back.client.request;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gsr.myschool.common.client.proxy.SessionExamenProxy;
import com.gsr.myschool.server.service.impl.SessionServiceImpl;
import com.gsr.myschool.server.util.SpringServiceLocator;

import java.util.List;

@Service(value = SessionServiceImpl.class, locator = SpringServiceLocator.class)
public interface SessionRequest extends RequestContext {
    Request<Void> createNewSession(SessionExamenProxy sessionExamen);

    Request<Void> updateSession(SessionExamenProxy sessionExamen);

    Request<Void> attacheToSession(Long sessionId, Long niveauEtudeId);

    Request<List<SessionExamenProxy>> findAllSessions();

    Request<List<SessionExamenProxy>> findSessionByNE(NiveauEtudeProxy niveau);

    Request<List<SessionExamenProxy>> findAllOpenedSessions();

    Request<Boolean> affecter(DossierProxy dossier, SessionExamenProxy session);

    Request<Boolean> desaffecter(DossierProxy dossier);
}
