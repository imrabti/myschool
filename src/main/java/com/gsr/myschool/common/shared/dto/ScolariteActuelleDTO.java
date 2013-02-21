package com.gsr.myschool.common.shared.dto;

import com.gsr.myschool.common.shared.type.TypeEnseignement;
import com.gsr.myschool.server.business.EtablissementScolaire;
import com.gsr.myschool.server.business.core.NiveauEtude;

import java.io.Serializable;

public class ScolariteActuelleDTO implements Serializable {
    private Long id;
    private EtablissementScolaire etablissement;
    private TypeEnseignement typeEnseignement;
    private NiveauEtude niveauEtude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EtablissementScolaire getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(EtablissementScolaire etablissement) {
        this.etablissement = etablissement;
    }

    public TypeEnseignement getTypeEnseignement() {
        return typeEnseignement;
    }

    public void setTypeEnseignement(TypeEnseignement typeEnseignement) {
        this.typeEnseignement = typeEnseignement;
    }

    public NiveauEtude getNiveauEtude() {
        return niveauEtude;
    }

    public void setNiveauEtude(NiveauEtude niveauEtude) {
        this.niveauEtude = niveauEtude;
    }
}
