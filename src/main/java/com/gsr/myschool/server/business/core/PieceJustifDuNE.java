package com.gsr.myschool.server.business.core;

import javax.persistence.*;

@Entity
public class PieceJustifDuNE implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private PieceJustif pieceJustif;
    @ManyToOne
    private NiveauEtude niveauEtude;

    public Long getId() {
        return id;
    }

    public void setId(Long newId) {
        this.id = newId;
    }

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
}
