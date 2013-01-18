package com.gsr.myschool.server.business.core;

import javax.persistence.*;

@Entity
public class PieceJustifDuNE implements java.io.Serializable {
    @ManyToOne
    private PieceJustif pieceJustif;
    @ManyToOne
    private NiveauEtude niveauEtude;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public PieceJustif getPieceJustif() {
        return pieceJustif;
    }

    public void setPieceJustif(PieceJustif newPieceJustif) {
        this.pieceJustif = newPieceJustif;
    }

    public NiveauEtude getNiveauEtude() {
        return niveauEtude;
    }

    public void setNiveauEtude(NiveauEtude newNiveauEtude) {
        this.niveauEtude = newNiveauEtude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer newId) {
        this.id = newId;
    }
}