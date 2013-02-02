package com.gsr.myschool.common.client.ui.dossier.renderer;

import com.google.gwt.text.shared.AbstractRenderer;
import com.gsr.myschool.common.client.proxy.EtablissementScolaireProxy;

public class EtablissementRenderer extends AbstractRenderer<EtablissementScolaireProxy> {
    @Override
    public String render(EtablissementScolaireProxy value) {
        if (value == null) {
            return "";
        } else  {
            return value.getNom();
        }
    }
}
