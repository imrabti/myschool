package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.server.business.ScolariteActuelle;

@ProxyFor(ScolariteActuelle.class)
public interface ScolariteActuelleProxy extends ValueProxy {
    Long getId();

    void setId(Long newId);

    EtablissementScolaireProxy getEtablissement();

    void setEtablissement(EtablissementScolaireProxy etablissement);

    FiliereProxy getFiliere();

    void setFiliere(FiliereProxy filiere);

    NiveauEtudeProxy getNiveauEtude();

    void setNiveauEtude(NiveauEtudeProxy niveauEtude);
}
