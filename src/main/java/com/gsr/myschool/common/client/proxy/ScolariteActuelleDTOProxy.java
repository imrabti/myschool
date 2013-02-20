package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.common.shared.dto.ScolariteActuelleDTO;
import com.gsr.myschool.common.shared.type.TypeEnseignement;

@ProxyFor(ScolariteActuelleDTO.class)
public interface ScolariteActuelleDTOProxy extends ValueProxy {
    Long getId();

    void setId(Long id);

    EtablissementScolaireProxy getEtablissement();

    void setEtablissement(EtablissementScolaireProxy etablissement);

    TypeEnseignement getTypeEnseignement();

    void setTypeEnseignement(TypeEnseignement typeEnseignement);

    NiveauEtudeProxy getNiveauEtude();

    void setNiveauEtude(NiveauEtudeProxy niveauEtude);
}
