package com.gsr.myschool.back.client.web.administration.valueList;

import com.google.gwt.text.shared.AbstractRenderer;
import com.gsr.myschool.back.client.request.proxy.DefLovProxy;

public class DefLovListRenderer extends AbstractRenderer<DefLovProxy> {
    @Override
    public String render(DefLovProxy defLovProxy) {
        if(defLovProxy == null)
            return "Aucun";
        return defLovProxy.getName();
    }
}
