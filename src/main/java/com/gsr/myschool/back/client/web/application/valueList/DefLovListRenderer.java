package com.gsr.myschool.back.client.web.application.valueList;

import com.google.gwt.text.shared.AbstractRenderer;
import com.gsr.myschool.back.client.request.proxy.ValueTypeProxy;

public class DefLovListRenderer extends AbstractRenderer<ValueTypeProxy> {
    @Override
    public String render(ValueTypeProxy defLovProxy) {
        if(defLovProxy == null)
            return "Aucun";
        return defLovProxy.getName();
    }
}
