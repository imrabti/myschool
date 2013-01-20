package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.server.business.Dossier;

import java.util.Date;

@ProxyFor(Dossier.class)
public interface DossierProxy extends ValueProxy {
    Long getId();

    void setId(Long id);

    DossierStatus getStatus();

    void setStatus(DossierStatus status);

    UserProxy getUser();

    void setUser(UserProxy user);

    String getReference();

    void setReference(String reference);

    Date getSubmitted();

    void setSubmitted(Date submitted);

    Date getCreated();

    void setCreated(Date created);

    Date getUpdated();

    void setUpdated(Date updated);
}
