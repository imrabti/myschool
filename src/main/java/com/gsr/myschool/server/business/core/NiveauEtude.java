/***********************************************************************
 * Module:  NiveauEtude.java
 * Author:  mbouayad
 * Purpose: Defines the Class NiveauEtude
 ***********************************************************************/

package com.gsr.myschool.server.business.core;

import javax.persistence.*;

@Entity
public class NiveauEtude implements java.io.Serializable {
    @ManyToOne
    private Filiere filiere;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer annee;
    private String nom;

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere newFiliere) {
        this.filiere = newFiliere;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer newId) {
        this.id = newId;
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