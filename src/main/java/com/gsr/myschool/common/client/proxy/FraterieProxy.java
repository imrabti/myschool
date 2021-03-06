package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.server.business.Fraterie;

import java.util.Date;

@ProxyFor(Fraterie.class)
public interface FraterieProxy extends ValueProxy {
    Long getId();

    void setId(Long id);

    CandidatProxy getCandidat();

    void setCandidat(CandidatProxy candidat);

    String getNom();

    void setNom(String nom);

    String getPrenom();

    void setPrenom(String prenom);

    String getNumDossierGSR();

    void setNumDossierGSR(String numDossierGSR);

    NiveauEtudeProxy getNiveau();

    void setNiveau(NiveauEtudeProxy niveau);

    Boolean getValide();

    void setValide(Boolean valide);

    EtablissementScolaireProxy getEtablissement();

    void setEtablissement(EtablissementScolaireProxy etablissement);

    Date getBirthDate();

    void setBirthDate(Date birthDate);

    String getBirthLocation();

    void setBirthLocation(String birthLocation);

    Boolean getScolarise();

    void setScolarise(Boolean scolarise);

    FiliereProxy getFiliere();

    void setFiliere(FiliereProxy filiere);
}
