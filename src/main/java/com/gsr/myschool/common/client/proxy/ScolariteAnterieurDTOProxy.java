package com.gsr.myschool.common.client.proxy;


import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.common.shared.dto.ScolariteAnterieurDTO;
import com.gsr.myschool.common.shared.type.TypeNiveauEtude;

@ProxyFor(ScolariteAnterieurDTO.class)
public interface ScolariteAnterieurDTOProxy extends ValueProxy {
    public CandidatProxy getCandidat();

    public void setCandidat(CandidatProxy candidat);

    public EtablissementScolaireProxy getEtablissement();

    public void setEtablissement(EtablissementScolaireProxy etablissement);

    public String getNewEtablissementScolaire();

    public void setNewEtablissementScolaire(String newEtablissementScolaire);

    public TypeNiveauEtude getTypeNiveauEtude();

    public void setTypeNiveauEtude(TypeNiveauEtude typeNiveauEtude);

    public String getClasse();

    public void setClasse(String classe);

    public ValueListProxy getAnneeScolaire();

    public void setAnneeScolaire(ValueListProxy anneeScolaire);
}
