package com.gsr.myschool.server.business.core;

import javax.persistence.*;

@Entity
public class MatiereExamDuNE implements java.io.Serializable {
    @ManyToOne
    private NiveauEtude niveauEtude;
    @ManyToOne
    private MatiereExamen matiereExamen;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public NiveauEtude getNiveauEtude() {
        return niveauEtude;
    }

    public void setNiveauEtude(NiveauEtude newNiveauEtude) {
        this.niveauEtude = newNiveauEtude;
    }

    public MatiereExamen getMatiereExamen() {
        return matiereExamen;
    }

    public void setMatiereExamen(MatiereExamen newMatiereExamen) {
        this.matiereExamen = newMatiereExamen;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer newId) {
        this.id = newId;
    }
}