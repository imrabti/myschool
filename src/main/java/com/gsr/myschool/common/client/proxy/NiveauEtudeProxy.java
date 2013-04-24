package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.common.shared.type.TypeNiveauEtude;
import com.gsr.myschool.server.business.core.NiveauEtude;

@ProxyFor(NiveauEtude.class)
public interface NiveauEtudeProxy extends ValueProxy {
    Long getId();

    void setId(Long newId);

    FiliereProxy getFiliere();

    void setFiliere(FiliereProxy newFiliere);

    Integer getAnnee();

    void setAnnee(Integer newAnnee);

    String getNom();

    void setNom(String newNom);

    TypeNiveauEtude getType();

    void setType(TypeNiveauEtude type);
}
