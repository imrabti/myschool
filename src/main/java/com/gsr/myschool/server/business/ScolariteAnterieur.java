package com.gsr.myschool.server.business;

import com.gsr.myschool.common.shared.type.TypeNiveauEtude;
import com.gsr.myschool.server.business.valuelist.ValueList;

import javax.persistence.*;

@Entity
public class ScolariteAnterieur implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Candidat candidat;
    @ManyToOne
    private EtablissementScolaire etablissement;
    @Enumerated
    private TypeNiveauEtude typeNiveauEtude;
    private String classe;
    @ManyToOne
    private ValueList anneeScolaire;

    public Long getId() {
        return id;
    }

    public void setId(Long newId) {
        this.id = newId;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public void setCandidat(Candidat newCandidat) {
        this.candidat = newCandidat;
    }

    public EtablissementScolaire getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(EtablissementScolaire newEtablissementScolaire) {
        this.etablissement = newEtablissementScolaire;
    }

    public TypeNiveauEtude getTypeNiveauEtude() {
        return typeNiveauEtude;
    }

    public void setTypeNiveauEtude(TypeNiveauEtude newTypeNiveauEtude) {
        this.typeNiveauEtude = newTypeNiveauEtude;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String newClasse) {
        this.classe = newClasse;
    }

    public ValueList getAnneeScolaire() {
        return anneeScolaire;
    }

    public void setAnneeScolaire(ValueList anneeScolaire) {
        this.anneeScolaire = anneeScolaire;
    }
}
