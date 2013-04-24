package com.gsr.myschool.common.shared.dto;

import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.server.business.core.Filiere;
import com.gsr.myschool.server.business.core.NiveauEtude;
import com.gsr.myschool.server.business.core.SessionExamen;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DossierFilterDTO implements Serializable {
    private String numDossier;
    private Boolean gsrFraterie;
    private DossierStatus status;
    private Date dateTill;
    private Date dateFrom;
    private Filiere filiere;
    private NiveauEtude niveauEtude;
    private String firstnameOrlastname;
    private Boolean parentGsr;
    private List<DossierStatus> statusList;
    private SessionExamen session;
    private List<SessionExamen> sessionList;

    public String getNumDossier() {
        return numDossier;
    }

    public void setNumDossier(String numDossier) {
        this.numDossier = numDossier;
    }

    public Boolean getGsrFraterie() {
        return gsrFraterie;
    }

    public void setGsrFraterie(Boolean gsrFraterie) {
        this.gsrFraterie = gsrFraterie;
    }

    public DossierStatus getStatus() {
        return status;
    }

    public void setStatus(DossierStatus status) {
        this.status = status;
    }

    public Date getDateTill() {
        return dateTill;
    }

    public void setDateTill(Date till) {
        this.dateTill = till;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date from) {
        this.dateFrom = from;
    }

    public String getFirstnameOrlastname() {
        return firstnameOrlastname;
    }

    public void setFirstnameOrlastname(String firstnameOrlastname) {
        this.firstnameOrlastname = firstnameOrlastname;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    public NiveauEtude getNiveauEtude() {
        return niveauEtude;
    }

    public void setNiveauEtude(NiveauEtude niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public Boolean getParentGsr() {
        return parentGsr;
    }

    public void setParentGsr(Boolean parentGsr) {
        this.parentGsr = parentGsr;
    }

    public List<DossierStatus> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<DossierStatus> statusList) {
        this.statusList = statusList;
    }

    public SessionExamen getSession() {
        return session;
    }

    public void setSession(SessionExamen session) {
        this.session = session;
    }

    public List<SessionExamen> getSessionList() {
        return sessionList;
    }

    public void setSessionList(List<SessionExamen> sessionList) {
        this.sessionList = sessionList;
    }
}
