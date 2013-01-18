package com.gsr.myschool.server.business;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Candidat implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nom;
    private String prenom;
    private Date birthDate;
    private String birthLocation;
    private String phone;
    private String cin;
    private String cne;
    private String email;
    private String gsm;

    public Integer getId() {
        return id;
    }

    public void setId(Integer newId) {
        this.id = newId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String newNom) {
        this.nom = newNom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String newPrenom) {
        this.prenom = newPrenom;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date newBirthDate) {
        this.birthDate = newBirthDate;
    }

    public String getBirthLocation() {
        return birthLocation;
    }

    public void setBirthLocation(String newBirthLocation) {
        this.birthLocation = newBirthLocation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String newPhone) {
        this.phone = newPhone;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String newCin) {
        this.cin = newCin;
    }

    public String getCne() {
        return cne;
    }

    public void setCne(String newCne) {
        this.cne = newCne;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String newGsm) {
        this.gsm = newGsm;
    }
}