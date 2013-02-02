package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.server.business.EtablissementScolaire;

@ProxyFor(EtablissementScolaire.class)
public interface EtablissementScolaireProxy extends ValueProxy {
    Long getId();

    void setId(Long newId);

    String getNom();

    void setNom(String newNom);

    Boolean getReference();

    void setReference(Boolean newReference);

    String getAdresse();

    void setAdresse(String newAdresse);
}
