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
    private Date createDate;
    private Date submitDate;
    @Enumerated
    private DossierStatus status;
    private String generatedPDFPath;
    private String generatedNumDossier;
    private String note;
    private Date rdvEntretien;

    public Candidat getCandidat() {
        return candidat;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public DossierStatus getStatus() {
        return status;
    }

    public void setStatus(DossierStatus status) {
        this.status = status;
    }

    public String getGeneratedPDFPath() {
        return generatedPDFPath;
    }

    public void setGeneratedPDFPath(String generatedPDFPath) {
        this.generatedPDFPath = generatedPDFPath;
    }

    public String getGeneratedNumDossier() {
        return generatedNumDossier;
    }

    public void setGeneratedNumDossier(String generatedNumDossier) {
        this.generatedNumDossier = generatedNumDossier;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getRdvEntretien() {
        return rdvEntretien;
    }

    public void setRdvEntretien(Date rdvEntretien) {
        this.rdvEntretien = rdvEntretien;
    }
}
