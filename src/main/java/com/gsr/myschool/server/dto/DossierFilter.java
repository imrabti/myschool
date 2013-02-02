package com.gsr.myschool.server.dto;

import com.gsr.myschool.common.shared.type.DossierStatus;

import java.util.Date;

public class DossierFilter {
    private String numDossier;
    private DossierStatus status;
    private Date dateCreation;

    public DossierFilter() {
    }

    public DossierFilter(String numDossier, DossierStatus status, Date dateCreation) {
        this.numDossier = numDossier;
        this.status = status;
        this.dateCreation = dateCreation;
    }

    public String getNumDossier() {
        return numDossier;
    }

    public void setNumDossier(String numDossier) {
        this.numDossier = numDossier;
    }

    public DossierStatus getStatus() {
        return status;
    }

    public void setStatus(DossierStatus status) {
        this.status = status;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
}
