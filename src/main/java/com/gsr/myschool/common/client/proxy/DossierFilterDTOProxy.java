package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.common.shared.dto.DossierFilterDTO;
import com.gsr.myschool.common.shared.type.DossierStatus;

import java.util.Date;

@ProxyFor(DossierFilterDTO.class)
public interface DossierFilterDTOProxy extends ValueProxy {
    public String getNumDossier();

    public void setNumDossier(String numDossier);

    public DossierStatus getStatus();

    public void setStatus(DossierStatus status);

    public Date getDateTill();

    public void setDateTill(Date till);

    public Date getDateFrom();

    public void setDateFrom(Date from);

    public String getFirstnameOrlastname();

    public void setFirstnameOrlastname(String firstnameOrlastname);
}
