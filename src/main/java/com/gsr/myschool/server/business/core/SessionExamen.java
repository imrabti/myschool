package com.gsr.myschool.server.business.core;

import com.gsr.myschool.common.shared.type.SessionStatus;
import com.gsr.myschool.server.business.valuelist.ValueList;

import javax.persistence.*;
import java.util.Date;

@Entity
public class SessionExamen implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date dateSession;
    private Double longitude;
    private Double latitude;
    private String adresse;
    private String telephone;
    private String nom;
    private String debutTest;
    private String welcomKids;
    private String gatherKids;
    @ManyToOne
    private ValueList anneeScolaire;
    @Enumerated
    private SessionStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long newId) {
        this.id = newId;
    }

    public Date getDateSession() {
        return dateSession;
    }

    public void setDateSession(Date newDateSession) {
        this.dateSession = newDateSession;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDebutTest() {
        return debutTest;
    }

    public void setDebutTest(String debutTest) {
        this.debutTest = debutTest;
    }

    public String getWelcomKids() {
        return welcomKids;
    }

    public void setWelcomKids(String welcomKids) {
        this.welcomKids = welcomKids;
    }

    public String getGatherKids() {
        return gatherKids;
    }

    public void setGatherKids(String gatherKids) {
        this.gatherKids = gatherKids;
    }

    public ValueList getAnneeScolaire() {
        return anneeScolaire;
    }

    public void setAnneeScolaire(ValueList anneeScolaire) {
        this.anneeScolaire = anneeScolaire;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public void setStatus(SessionStatus status) {
        this.status = status;
    }
}
