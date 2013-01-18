package com.gsr.myschool.server.business;

import com.gsr.myschool.common.shared.type.DossierStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Dossier implements java.io.Serializable {
    @ManyToOne
    private Candidat candidat;
    @ManyToOne
    private User owner;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Date dateCreation;
    @Enumerated
    private DossierStatus status;
    private String generatedPDFPath;
    private String generatedNumDossier;
    private String note;
    private Date rdvEntretien;

    public Candidat getCandidat() {
        return candidat;
    }

    public void setCandidat(Candidat newCandidat) {
        this.candidat = newCandidat;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User newUser) {
        this.owner = newUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer newId) {
        this.id = newId;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date newDateCreation) {
        this.dateCreation = newDateCreation;
    }

    public DossierStatus getStatus() {
        return status;
    }

    public void setStatus(DossierStatus newStatus) {
        this.status = newStatus;
    }

    public String getGeneratedPDFPath() {
        return generatedPDFPath;
    }

    public void setGeneratedPDFPath(String newGeneratedPDFPath) {
        this.generatedPDFPath = newGeneratedPDFPath;
    }

    public String getGeneratedNumDossier() {
        return generatedNumDossier;
    }

    public void setGeneratedNumDossier(String newGeneratedNumDossier) {
        this.generatedNumDossier = newGeneratedNumDossier;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String newNote) {
        this.note = newNote;
    }

    public Date getRdvEntretien() {
        return rdvEntretien;
    }

    public void setRdvEntretien(Date newRdvEntretien) {
        this.rdvEntretien = newRdvEntretien;
    }
}