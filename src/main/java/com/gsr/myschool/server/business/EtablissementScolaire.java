package com.gsr.myschool.server.business;

import com.gsr.myschool.common.shared.type.EtablissementType;
import com.gsr.myschool.server.business.valuelist.ValueList;

import javax.persistence.*;

@Entity
public class EtablissementScolaire implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    @ManyToOne
    private ValueList ville;
    @Enumerated
    private EtablissementType type;
    private Boolean gsr;

    public Long getId() {
        return id;
    }

    public void setId(Long newId) {
        this.id = newId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String newNom) {
        this.nom = newNom;
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

    public Boolean getGsr() {
        return gsr;
    }

    public void setGsr(Boolean gsr) {
        this.gsr = gsr;
    }
}
