package com.gsr.myschool.server.business;

import com.gsr.myschool.common.shared.type.TypeFraterie;

import javax.persistence.*;

@Entity
public class Fraterie implements java.io.Serializable {
    @ManyToOne
    private Candidat candidat;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nom;
    private String prenom;
    private String numDossierGSR;
    @Enumerated
    private TypeFraterie typeFraterie;
    private Boolean valide;

    public Candidat getCandidat() {
        return candidat;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumDossierGSR() {
        return numDossierGSR;
    }

    public void setNumDossierGSR(String numDossierGSR) {
        this.numDossierGSR = numDossierGSR;
    }

    public TypeFraterie getTypeFraterie() {
        return typeFraterie;
    }

    public void setTypeFraterie(TypeFraterie typeFraterie) {
        this.typeFraterie = typeFraterie;
    }

    public Boolean getValide() {
        return valide;
    }

    public void setValide(Boolean valide) {
        this.valide = valide;
    }
}