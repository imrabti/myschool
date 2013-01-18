package com.gsr.myschool.server.business;

import javax.persistence.*;
import java.util.Date;

@Entity
public class NoteMatiere implements java.io.Serializable {
    @ManyToOne
    private Dossier dossier;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String matiere;
    private String note;
    private Date dateSaisie;

    public Dossier getDossier() {
        return dossier;
    }

    public void setDossier(Dossier newDossier) {
        this.dossier = newDossier;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer newId) {
        this.id = newId;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String newMatiere) {
        this.matiere = newMatiere;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String newNote) {
        this.note = newNote;
    }

    public Date getDateSaisie() {
        return dateSaisie;
    }

    public void setDateSaisie(Date newDateSaisie) {
        this.dateSaisie = newDateSaisie;
    }
}