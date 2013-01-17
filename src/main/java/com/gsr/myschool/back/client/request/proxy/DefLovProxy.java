package com.gsr.myschool.back.client.request.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.server.business.DefLov;

@ProxyFor(DefLov.class)
public interface DefLovProxy extends ValueProxy {

    Long getId();
    void setId(Long id);

    String getName();
    void setName(String name);

    LOVProxy getRegex();
    void setRegex(LOVProxy regex);

    DefLovProxy getParent();
    void setParent(DefLovProxy parent);

    Boolean getSystem();
    void setSystem(Boolean system);

}
