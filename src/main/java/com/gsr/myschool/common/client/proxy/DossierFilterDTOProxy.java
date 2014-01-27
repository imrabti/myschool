package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.common.shared.dto.DossierFilterDTO;
import com.gsr.myschool.common.shared.type.DossierStatus;

import java.util.Date;
import java.util.List;

@ProxyFor(DossierFilterDTO.class)
public interface DossierFilterDTOProxy extends ValueProxy {
    String getNumDossier();

    void setNumDossier(String numDossier);

    Boolean getGsrFraterie();

    void setGsrFraterie(Boolean gsrFraterie);

    DossierStatus getStatus();

    void setStatus(DossierStatus status);

    Date getDateTill();

    void setDateTill(Date till);

    Date getDateFrom();

    void setDateFrom(Date from);

    String getFirstnameOrlastname();

    void setFirstnameOrlastname(String firstnameOrlastname);

    NiveauEtudeProxy getNiveauEtude();

    void setNiveauEtude(NiveauEtudeProxy niveauEtude);

    FiliereProxy getFiliere();

    void setFiliere(FiliereProxy filiere);

    Boolean getParentGsr();

    void setParentGsr(Boolean parentGsr);

    List<DossierStatus> getStatusList();

    void setStatusList(List<DossierStatus> statusList);

    SessionExamenProxy getSession();

    void setSession(SessionExamenProxy session);

    String getSessionIds();

    void setSessionIds(String sessionIds);

    ValueListProxy getAnneeScolaire();

    void setAnneeScolaire(ValueListProxy anneeScolaire);
}
