/***********************************************************************
 * Module:  InfoParent.java
 * Author:  mbouayad
 * Purpose: Defines the Class InfoParent
 ***********************************************************************/

package com.gsr.myschool.server.business;

import com.gsr.myschool.common.shared.type.ParentType;

import javax.persistence.*;

@Entity
public class InfoParent implements java.io.Serializable {
    @ManyToOne
    private Dossier dossier;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nom;
    private String prenom;
    private String telGsm;
    private String telDom;
    private String telBureau;
    private String email;
    private String address;
    private String fonction;
    @Enumerated
    private ParentType parentType;
    private String institution;

    public Dossier getDossier() {
        return dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
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

    public String getTelGsm() {
        return telGsm;
    }

    public void setTelGsm(String telGsm) {
        this.telGsm = telGsm;
    }

    public String getTelDom() {
        return telDom;
    }

    public void setTelDom(String telDom) {
        this.telDom = telDom;
    }

    public String getTelBureau() {
        return telBureau;
    }

    public void setTelBureau(String telBureau) {
        this.telBureau = telBureau;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public ParentType getParentType() {
        return parentType;
    }

    public void setParentType(ParentType parentType) {
        this.parentType = parentType;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }
}
