package com.gsr.myschool.back.client.web.administration.valueList;

import com.google.gwt.text.shared.AbstractRenderer;
import com.gsr.myschool.back.client.request.proxy.LOVProxy;

public class LOVListRenderer extends AbstractRenderer<LOVProxy> {
    @Override
    public String render(LOVProxy lovProxy) {
        if(lovProxy == null)
            return "Aucun";
        return lovProxy.getLabel();
    }
}
