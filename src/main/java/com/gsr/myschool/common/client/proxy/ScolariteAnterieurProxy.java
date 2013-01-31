package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.common.shared.type.TypeNiveauEtude;
import com.gsr.myschool.server.business.ScolariteAnterieur;

@ProxyFor(ScolariteAnterieur.class)
public interface ScolariteAnterieurProxy extends ValueProxy {
    Long getId();

    void setId(Long newId);

    CandidatProxy getCandidat();

    void setCandidat(CandidatProxy newCandidat);

    EtablissementScolaireProxy getEtablissement();

    void setEtablissement(EtablissementScolaireProxy newEtablissementScolaire);

    TypeNiveauEtude getTypeNiveauEtude();

    void setTypeNiveauEtude(TypeNiveauEtude newTypeNiveauEtude);

    String getClasse();

    void setClasse(String newClasse);

    ValueListProxy getAnneeScolaire();

    void setAnneeScolaire(ValueListProxy anneeScolaire);
}
