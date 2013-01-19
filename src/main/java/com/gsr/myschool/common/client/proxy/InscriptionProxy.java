package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.server.business.Inscription;

import java.util.Date;

@ProxyFor(Inscription.class)
public interface InscriptionProxy extends ValueProxy {
    Long getId();

    void setId(Long id);

    DossierStatus getStatus();

    void setStatus(DossierStatus status);

    UserProxy getUser();

    void setUser(UserProxy user);

    Date getCreated();

    void setCreated(Date created);

    Date getUpdated();

    void setUpdated(Date updated);
}
