package com.gsr.myschool.common.shared.dto;

import com.gsr.myschool.common.shared.type.DossierStatus;

import java.io.Serializable;
import java.util.Date;

public class DossierFilterDTO implements Serializable {
    private String numDossier;
    private DossierStatus status;
    private Date dateTill;
    private Date dateFrom;
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
}
