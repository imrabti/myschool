package com.gsr.myschool.back.client.web.application.settings.widget;

import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gwtplatform.mvp.client.UiHandlers;

public interface SystemScolaireUiHandlers extends UiHandlers {
    void showDetails(NiveauEtudeProxy object);
}
