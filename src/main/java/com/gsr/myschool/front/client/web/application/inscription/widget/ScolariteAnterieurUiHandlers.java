package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.gsr.myschool.common.client.proxy.ScolariteAnterieurDTOProxy;
import com.gsr.myschool.common.client.proxy.ScolariteAnterieurProxy;
import com.gwtplatform.mvp.client.UiHandlers;

public interface ScolariteAnterieurUiHandlers extends UiHandlers {
    void addScolariteAnterieur(ScolariteAnterieurDTOProxy scolariteAnterieur);

    void deleteScolariteAnterieur(ScolariteAnterieurProxy scolariteAnterieur);
}
