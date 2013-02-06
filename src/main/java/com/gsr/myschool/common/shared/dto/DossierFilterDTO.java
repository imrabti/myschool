package com.gsr.myschool.common.shared.dto;

import com.gsr.myschool.common.shared.type.DossierStatus;

import java.io.Serializable;
import java.util.Date;

public class DossierFilterDTO implements Serializable {
    private String numDossier;
    private DossierStatus status;
    private Date created;
    private String firstnameOrlastname;

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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getFirstnameOrlastname() {
        return firstnameOrlastname;
    }

    public void setFirstnameOrlastname(String firstnameOrlastname) {
        this.firstnameOrlastname = firstnameOrlastname;
    }
}
