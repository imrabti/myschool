package com.gsr.myschool.server.business;

import javax.persistence.*;

@Entity
public class ValueType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String regex;
    @ManyToOne
    private ValueType parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public ValueType getParent() {
        return parent;
    }

    public void setParent(ValueType parent) {
        this.parent = parent;
    }
}
