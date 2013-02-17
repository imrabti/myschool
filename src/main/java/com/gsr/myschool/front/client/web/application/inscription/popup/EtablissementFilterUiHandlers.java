package com.gsr.myschool.front.client.web.application.inscription.popup;

import com.gsr.myschool.common.client.proxy.EtablissementFilterDTOProxy;
import com.gsr.myschool.common.client.proxy.EtablissementScolaireProxy;
import com.gwtplatform.mvp.client.UiHandlers;

public interface EtablissementFilterUiHandlers extends UiHandlers {
    void search(EtablissementFilterDTOProxy etablissementFilter);

    void valueSelected(EtablissementScolaireProxy selectedValue);
}
