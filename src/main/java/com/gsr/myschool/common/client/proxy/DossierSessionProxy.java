package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.server.business.DossierSession;

import java.util.Date;

@ProxyFor(DossierSession.class)
public interface DossierSessionProxy extends ValueProxy {
    Long getId();

    void setId(Long id);

    AdminUserProxy getAssignedBy();

    void setAssignedBy(AdminUserProxy assignedBy);

    DossierProxy getDossier();

    void setDossier(DossierProxy dossier);

    SessionExamenProxy getSessionExamen();

    void setSessionExamen(SessionExamenProxy sessionExamen);

    Date getDateAffectation();

    void setDateAffectation(Date dateAffectation);

    String getGeneratedConvocationPDFPath();

    void setGeneratedConvocationPDFPath(String generatedConvocationPDFPath);
}
