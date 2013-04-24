package com.gsr.myschool.common.shared.dto;

import com.gsr.myschool.server.business.DossierSession;

import java.io.Serializable;

public class DossierConvocationDTO implements Serializable {
    private DossierSession dossierSession;
    private Boolean gsrFraterie;
    private Boolean gsrParent;
    private String haveFraterie;
    private String haveParentGsr;

    public DossierSession getDossierSession() {
        return dossierSession;
    }

    public void setDossierSession(DossierSession dossierSession) {
        this.dossierSession = dossierSession;
    }

    public Boolean getGsrFraterie() {
        return gsrFraterie;
    }

    public void setGsrFraterie(Boolean gsrFraterie) {
        this.gsrFraterie = gsrFraterie;
    }

    public Boolean getGsrParent() {
        return gsrParent;
    }

    public void setGsrParent(Boolean gsrParent) {
        this.gsrParent = gsrParent;
    }

    public String getHaveFraterie() {
        haveFraterie = "Non";
        if (getGsrFraterie()) {
            haveFraterie = "Oui";
        }
        return haveFraterie;
    }

    public void setHaveFraterie(String haveFraterie) {
        this.haveFraterie = haveFraterie;
    }

    public String getHaveParentGsr() {
        haveParentGsr = "Non";
        if (getGsrParent()) {
            haveParentGsr = "Oui";
        }
        return haveParentGsr;
    }

    public void setHaveParentGsr(String haveParentGsr) {
        this.haveParentGsr = haveParentGsr;
    }
}
