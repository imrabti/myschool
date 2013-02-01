package com.gsr.myschool.common.shared.dto;

import com.gsr.myschool.common.shared.type.TypeNiveauEtude;
import com.gsr.myschool.server.business.Candidat;
import com.gsr.myschool.server.business.EtablissementScolaire;
import com.gsr.myschool.server.business.valuelist.ValueList;

import java.io.Serializable;

public class ScolariteAnterieurDTO implements Serializable {
    private Candidat candidat;
    private EtablissementScolaire etablissement;
    private String newEtablissementScolaire;
    private TypeNiveauEtude typeNiveauEtude;
    private String classe;
    private ValueList anneeScolaire;

    public Candidat getCandidat() {
        return candidat;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    public EtablissementScolaire getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(EtablissementScolaire etablissement) {
        this.etablissement = etablissement;
    }

    public String getNewEtablissementScolaire() {
        return newEtablissementScolaire;
    }

    public void setNewEtablissementScolaire(String newEtablissementScolaire) {
        this.newEtablissementScolaire = newEtablissementScolaire;
    }

    public TypeNiveauEtude getTypeNiveauEtude() {
        return typeNiveauEtude;
    }

    public void setTypeNiveauEtude(TypeNiveauEtude typeNiveauEtude) {
        this.typeNiveauEtude = typeNiveauEtude;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public ValueList getAnneeScolaire() {
        return anneeScolaire;
    }

    public void setAnneeScolaire(ValueList anneeScolaire) {
        this.anneeScolaire = anneeScolaire;
    }
}
