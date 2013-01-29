package com.gsr.myschool.common.client.ui.dossier.renderer;

import com.google.gwt.text.shared.AbstractRenderer;
import com.gsr.myschool.common.client.proxy.FiliereProxy;

public class FiliereRenderer extends AbstractRenderer<FiliereProxy> {
    @Override
    public String render(FiliereProxy value) {
        if (value == null) {
            return "";
        } else  {
            return value.getNom();
        }
    }
}
