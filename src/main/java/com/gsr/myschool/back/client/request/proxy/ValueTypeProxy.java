package com.gsr.myschool.back.client.request.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.server.business.lov.ValueType;

@ProxyFor(ValueType.class)
public interface ValueTypeProxy extends ValueProxy {
    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    ValueListProxy getRegex();

    void setRegex(ValueListProxy regex);

    ValueTypeProxy getParent();

    void setParent(ValueTypeProxy parent);

    Boolean getSystem();

    void setSystem(Boolean system);
}
