package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.common.shared.dto.DossierFilterDTO;
import com.gsr.myschool.common.shared.type.DossierStatus;

import java.util.Date;
import java.util.List;

@ProxyFor(DossierFilterDTO.class)
public interface DossierFilterDTOProxy extends ValueProxy {
    public String getNumDossier();

    public void setNumDossier(String numDossier);

    public Boolean getGsrFraterie();

    public void setGsrFraterie(Boolean gsrFraterie);

    public DossierStatus getStatus();

    public void setStatus(DossierStatus status);

    public Date getDateTill();

    public void setDateTill(Date till);

    public Date getDateFrom();

    public void setDateFrom(Date from);

    public String getFirstnameOrlastname();

    public void setFirstnameOrlastname(String firstnameOrlastname);

    public NiveauEtudeProxy getNiveauEtude();

    public void setNiveauEtude(NiveauEtudeProxy niveauEtude);

    public FiliereProxy getFiliere();

    public void setFiliere(FiliereProxy filiere);

    public Boolean getParentGsr();

    public void setParentGsr(Boolean parentGsr);

    List<DossierStatus> getStatusList();

    void setStatusList(List<DossierStatus> statusList);
}
