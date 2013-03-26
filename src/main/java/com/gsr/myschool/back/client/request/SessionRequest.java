package com.gsr.myschool.back.client.request;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.gsr.myschool.common.client.proxy.SessionExamenProxy;
import com.gsr.myschool.server.service.impl.SessionServiceImpl;
import com.gsr.myschool.server.util.SpringServiceLocator;

import java.util.List;

@Service(value = SessionServiceImpl.class, locator = SpringServiceLocator.class)
public interface SessionRequest extends RequestContext {
    Request<Void> createNewSession(SessionExamenProxy sessionExamen);

    Request<Void> updateSession(SessionExamenProxy sessionExamen);

    Request<List<SessionExamenProxy>> findAllSessions();
}
