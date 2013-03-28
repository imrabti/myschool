package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.common.shared.type.SessionStatus;
import com.gsr.myschool.server.business.core.SessionExamen;

import java.util.Date;

@ProxyFor(SessionExamen.class)
public interface SessionExamenProxy extends ValueProxy {
    Long getId();

    void setId(Long newId);

    Date getDateSession();

    void setDateSession(Date newDateSession);

    Double getLongitude();

    void setLongitude(Double longitude);

    Double getLatitude();

    void setLatitude(Double latitude);

    String getAdresse();

    void setAdresse(String adresse);

    String getTelephone();

    void setTelephone(String telephone);

    String getNom();

    void setNom(String nom);

    String getDebutTest();

    void setDebutTest(String debutTest);

    String getWelcomKids();

    void setWelcomKids(String welcomKids);

    String getGatherKids();

    void setGatherKids(String gatherKids);

    SessionStatus getStatus();

    void setStatus(SessionStatus status);

    Integer getCandidates();

    void setCandidates(Integer number);
}
