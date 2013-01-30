package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.server.business.valuelist.ValueList;

@ProxyFor(ValueList.class)
public interface ValueListProxy extends ValueProxy {
    Long getId();

    void setId(Long id);

    String getValue();

    void setValue(String value);

    ValueListProxy getParent();

    void setParent(ValueListProxy valueList);

    String getLabel();

    void setLabel(String label);

    ValueTypeProxy getValueType();

    void setValueType(ValueTypeProxy valueType);
}
