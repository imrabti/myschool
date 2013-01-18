package com.gsr.myschool.back.client.web.application.valueList;

import com.google.gwt.text.shared.AbstractRenderer;
import com.gsr.myschool.back.client.request.proxy.ValueListProxy;

public class LOVListRenderer extends AbstractRenderer<ValueListProxy> {
    @Override
    public String render(ValueListProxy lovProxy) {
        if(lovProxy == null)
            return "Aucun";
        return lovProxy.getLabel();
    }
}
