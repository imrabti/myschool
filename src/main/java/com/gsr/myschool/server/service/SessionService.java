package com.gsr.myschool.server.service;

import com.gsr.myschool.server.business.core.SessionExamen;

import java.util.List;

public interface SessionService {
    void createNewSession(SessionExamen sessionExamen);

    void updateSession(SessionExamen sessionExamen);

    List<SessionExamen> findAllSessions();
}
