package com.gsr.myschool.server.business.core;

import javax.persistence.*;

@Entity
public class PieceJustif implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String nom;

    public Long getId() {
        return id;
    }

    public void setId(Long newId) {
        this.id = newId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String newNom) {
        this.nom = newNom;
    }
}
