package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.server.business.core.Filiere;

@ProxyFor(Filiere.class)
public interface FiliereProxy extends ValueProxy {
    Long getId();

    void setId(Long newId);

    String getNom();

    void setNom(String newNom);

    String getDescription();

    void setDescription(String newDescription);
}
