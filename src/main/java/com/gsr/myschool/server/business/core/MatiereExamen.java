package com.gsr.myschool.server.business.core;

import com.gsr.myschool.server.business.valuelist.ValueList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class MatiereExamen implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String nom;
    @ManyToOne
    private ValueList de;
    @ManyToOne
    private ValueList a;

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

    public ValueList getDe() {
        return de;
    }

    public void setDe(ValueList de) {
        this.de = de;
    }

    public ValueList getA() {
        return a;
    }

    public void setA(ValueList a) {
        this.a = a;
    }
}
