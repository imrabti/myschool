package com.gsr.myschool.common.shared.dto;

import com.gsr.myschool.common.shared.type.EtablissementType;
import com.gsr.myschool.server.business.valuelist.ValueList;

public class EtablissementFilterDTO {
    private String nom;
    private ValueList ville;
    private EtablissementType type;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ValueList getVille() {
        return ville;
    }

    public void setVille(ValueList ville) {
        this.ville = ville;
    }

    public EtablissementType getType() {
        return type;
    }

    public void setType(EtablissementType type) {
        this.type = type;
    }
}
