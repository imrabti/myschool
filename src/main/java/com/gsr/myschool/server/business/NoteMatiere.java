package com.gsr.myschool.server.business;

import javax.persistence.*;
import java.util.Date;

@Entity
public class NoteMatiere implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Dossier dossier;
    private String matiere;
    private String note;
    private Date dateSaisie;

    public Long getId() {
        return id;
    }

    public void setId(Long newId) {
        this.id = newId;
    }

    public Dossier getDossier() {
        return dossier;
    }

    public void setDossier(Dossier newDossier) {
        this.dossier = newDossier;
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
