package com.gsr.myschool.back.client.request.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.server.business.lov.ValueList;

@ProxyFor(ValueList.class)
public interface ValueListProxy extends ValueProxy {
    public Long getId();

    public void setId(Long id);

    public String getValue();

    public void setValue(String value);

    public ValueListProxy getParent();

    public void setParent(ValueListProxy valueList);

    public String getLabel();

    public void setLabel(String label);

    public ValueTypeProxy getValueType();

    public void setValueType(ValueTypeProxy valueType);
}
