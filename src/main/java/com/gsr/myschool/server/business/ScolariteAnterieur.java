package com.gsr.myschool.server.business;

import com.gsr.myschool.common.shared.type.TypeNiveauEtude;

import javax.persistence.*;

@Entity
public class ScolariteAnterieur implements java.io.Serializable {
    @ManyToOne
    private Candidat candidat;
    @ManyToOne
    private EtablissementScolaire etablissement;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Enumerated
    private TypeNiveauEtude typeNiveauEtude;
    private String classe;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer newId) {
        this.id = newId;
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
}