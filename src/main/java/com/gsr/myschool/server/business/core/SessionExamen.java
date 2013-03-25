package com.gsr.myschool.server.business.core;

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
    private String nom;

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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
