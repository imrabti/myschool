package com.gsr.myschool.server.business;

import com.gsr.myschool.common.shared.type.DossierStatus;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Dossier implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated
    private DossierStatus status;
    @ManyToOne
    private User user;
    private Date submitted;
    private Date created;
    private Date updated;
    private String generatedNumDossier;
    private String note;
    private Date rdvEntretien;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DossierStatus getStatus() {
        return status;
    }

    public void setStatus(DossierStatus status) {
        this.status = status;
    }

    public Date getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Date submitted) {
        this.submitted = submitted;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
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
