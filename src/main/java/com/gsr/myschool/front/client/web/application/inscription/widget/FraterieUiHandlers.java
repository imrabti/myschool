package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.gsr.myschool.common.client.proxy.FraterieProxy;
import com.gwtplatform.mvp.client.UiHandlers;

public interface FraterieUiHandlers extends UiHandlers {
    void addFraterie(FraterieProxy fraterie);

    void deleteFraterie(FraterieProxy fraterie);
}
