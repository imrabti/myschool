package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.common.shared.dto.DossierMultiple;

@ProxyFor(DossierMultiple.class)
public interface DossierMultipleProxy extends ValueProxy {
    String getCompte();

    void setCompte(String compte);

    String getNumDossier();

    void setNumDossier(String numDossier);

    String getCandidat();

    void setCandidat(String candidat);

    String getEtablissement();

    void setEtablissement(String etablissement);

    String getPere();

    void setPere(String pere);

    String getMere();

    void setMere(String mere);

    String getTuteur();

    void setTuteur(String tuteur);

    String getFiliere();

    void setFiliere(String filiere);

    String getNiveauEtude();

    void setNiveauEtude(String niveauEtude);
}
