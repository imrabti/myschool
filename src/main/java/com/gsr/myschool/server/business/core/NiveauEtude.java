/***********************************************************************
 * Module:  NiveauEtude.java
 * Author:  mbouayad
 * Purpose: Defines the Class NiveauEtude
 ***********************************************************************/

package com.gsr.myschool.server.business.core;

import javax.persistence.*;

@Entity
public class NiveauEtude implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Filiere filiere;
    private Integer annee;
    private String nom;

    public Long getId() {
        return id;
    }

    public void setId(Long newId) {
        this.id = newId;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere newFiliere) {
        this.filiere = newFiliere;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer newAnnee) {
        this.annee = newAnnee;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String newNom) {
        this.nom = newNom;
    }
}
