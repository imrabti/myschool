package com.gsr.myschool.server.business.core;

import javax.persistence.*;

@Entity
public class MatiereExamDuNE implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private NiveauEtude niveauEtude;
    @ManyToOne
    private MatiereExamen matiereExamen;

    public Long getId() {
        return id;
    }

    public void setId(Long newId) {
        this.id = newId;
    }

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
}
