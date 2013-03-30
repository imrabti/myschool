package com.gsr.myschool.server.business.core;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SessionNiveauEtude {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String matiere;
    private String horaireDe;
    private String horaireA;
    @ManyToOne
    private SessionExamen sessionExamen;
    @ManyToOne
    private NiveauEtude niveauEtude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getHoraireDe() {
        return horaireDe;
    }

    public void setHoraireDe(String horaireDe) {
        this.horaireDe = horaireDe;
    }

    public String getHoraireA() {
        return horaireA;
    }

    public void setHoraireA(String horaireA) {
        this.horaireA = horaireA;
    }

    public SessionExamen getSessionExamen() {
        return sessionExamen;
    }

    public void setSessionExamen(SessionExamen sessionExamen) {
        this.sessionExamen = sessionExamen;
    }

    public NiveauEtude getNiveauEtude() {
        return niveauEtude;
    }

    public void setNiveauEtude(NiveauEtude niveauEtude) {
        this.niveauEtude = niveauEtude;
    }
}
