package com.gsr.myschool.back.client.web.application.session.popup;

import com.gsr.myschool.common.client.proxy.SessionExamenProxy;
import com.gwtplatform.mvp.client.UiHandlers;

public interface EditSessionUiHandlers extends UiHandlers {
    void saveSession(SessionExamenProxy sessionExamen);
}
