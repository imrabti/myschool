package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.gsr.myschool.common.client.proxy.ScolariteActuelleDTOProxy;
import com.gsr.myschool.common.client.proxy.ScolariteActuelleProxy;
import com.gwtplatform.mvp.client.UiHandlers;

public interface ScolariteAnterieurUiHandlers extends UiHandlers {
    void addScolariteAnterieur(ScolariteActuelleDTOProxy scolariteAnterieur);

    void deleteScolariteAnterieur(ScolariteActuelleProxy scolariteAnterieur);
}
