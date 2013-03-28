package com.gsr.myschool.back.client.web.application.affectation.popup;

import com.gsr.myschool.common.client.proxy.SessionExamenProxy;
import com.gwtplatform.mvp.client.UiHandlers;

public interface SessionAffectationUiHandlers extends UiHandlers {
    void valueSelected(SessionExamenProxy selectedValue);
}
