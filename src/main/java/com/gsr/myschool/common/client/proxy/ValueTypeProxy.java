package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.common.shared.type.ValueTypeCode;
import com.gsr.myschool.server.business.valuelist.ValueType;

@ProxyFor(ValueType.class)
public interface ValueTypeProxy extends ValueProxy {
    Long getId();

    void setId(Long id);

    ValueTypeCode getCode();

    void setCode(ValueTypeCode code);

    ValueListProxy getRegex();

    void setRegex(ValueListProxy regex);

    ValueTypeProxy getParent();

    void setParent(ValueTypeProxy parent);

    Boolean getSystem();

    void setSystem(Boolean system);
}
