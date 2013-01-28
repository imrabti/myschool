package com.gsr.myschool.common.client.ui.dossier.renderer;

import com.google.gwt.text.shared.AbstractRenderer;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;

public class NiveauEtudeRenderer extends AbstractRenderer<NiveauEtudeProxy> {
    @Override
    public String render(NiveauEtudeProxy value) {
        if (value == null) {
            return "";
        } else  {
            return value.getNom();
        }
    }
}
