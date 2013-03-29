package com.gsr.myschool.back.client.web.application.session;

import com.gsr.myschool.common.client.proxy.SessionExamenProxy;
import com.gwtplatform.mvp.client.UiHandlers;

public interface SessionUiHandlers extends UiHandlers {
    void sessionSelected(SessionExamenProxy session);

    void newSession();

    void openSession(SessionExamenProxy session);

    void updateSession(SessionExamenProxy session);

    void closeSession(SessionExamenProxy session);

    void cancelSession(SessionExamenProxy session);
}
