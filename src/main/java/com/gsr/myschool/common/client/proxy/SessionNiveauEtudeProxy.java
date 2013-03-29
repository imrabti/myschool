package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.server.business.core.SessionNiveauEtude;

@ProxyFor(SessionNiveauEtude.class)
public interface SessionNiveauEtudeProxy extends ValueProxy {
    Long getId();

    void setId(Long id);

    String getMatiere();

    void setMatiere(String matiere);

    String getHoraireDe();

    void setHoraireDe(String horaireDe);

    String getHoraireA();

    void setHoraireA(String horaireA);

    SessionExamenProxy getSessionExamen();

    void setSessionExamen(SessionExamenProxy sessionExamen);

    NiveauEtudeProxy getNiveauEtude();

    void setNiveauEtude(NiveauEtudeProxy niveauEtude);
}
