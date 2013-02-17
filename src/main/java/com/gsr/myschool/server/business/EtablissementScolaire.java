package com.gsr.myschool.server.business;

import javax.persistence.*;

@Entity
public class EtablissementScolaire implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String nom;
    private Boolean reference;
    private String adresse;
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

    public Boolean getReference() {
        return reference;
    }

    public void setReference(Boolean newReference) {
        this.reference = newReference;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String newAdresse) {
        this.adresse = newAdresse;
    }

    public Boolean getGsr() {
        return gsr;
    }

    public void setGsr(Boolean gsr) {
        this.gsr = gsr;
    }
}
