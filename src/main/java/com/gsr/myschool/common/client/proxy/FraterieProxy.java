package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.common.shared.type.TypeFraterie;
import com.gsr.myschool.server.business.Fraterie;

@ProxyFor(Fraterie.class)
public interface FraterieProxy extends ValueProxy {
    public Long getId();

    public void setId(Long id);

    public CandidatProxy getCandidat();

    public void setCandidat(CandidatProxy candidat);

    public String getNom();

    public void setNom(String nom);

    public String getPrenom();

    public void setPrenom(String prenom);

    public String getNumDossierGSR();

    public void setNumDossierGSR(String numDossierGSR);

    public String getNiveau();

    public void setNiveau(String niveau);

    public String getClasse();

    public void setClasse(String classe);

    public String getEtablissement();

    public void setEtablissement(String etablissement);

    public TypeFraterie getTypeFraterie();

    public void setTypeFraterie(TypeFraterie typeFraterie);

    public Boolean getValide();

    public void setValide(Boolean valide);
}
