package com.gsr.myschool.front.client.web.application.inscription.renderer;

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
