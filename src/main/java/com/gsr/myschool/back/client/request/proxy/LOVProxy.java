package com.gsr.myschool.back.client.request.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.server.business.LOV;

@ProxyFor(LOV.class)
public interface LOVProxy extends ValueProxy {

    public Long getId();
    public void setId(Long id);

    public String getValue();
    public void setValue(String value);

    public String getLabel();
    public void setLabel(String label);

    public LOVProxy getParent();
    public void setParent(LOVProxy parent);

    public DefLovProxy getDefLov();
    public void setDefLov(DefLovProxy defLov);
}
