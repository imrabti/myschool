package com.gsr.myschool.server.business;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class LOV {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic(optional=false)
    @Column
    private String value;

    @Column
    private String label;

    @JoinColumn(referencedColumnName="id")
    @ManyToOne(fetch=FetchType.EAGER)
    private LOV parent;

    @JoinColumn
    @NotNull(message = "Chaque valeur doit avoir une définition")
    @ManyToOne(fetch=FetchType.EAGER)
    private DefLov defLov;

    public LOV() {
    }

    public LOV(String value, LOV parent, DefLov defLov) {
        this.value = value;
        this.parent = parent;
        this.defLov = defLov;
        this.label = value;
    }

    public LOV(String value, LOV parent, DefLov defLov, String label) {
        this.value = value;
        this.parent = parent;
        this.defLov = defLov;
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public LOV getParent() {
        return parent;
    }

    public void setParent(LOV parent) {
        this.parent = parent;
    }

    public DefLov getDefLov() {
        return defLov;
    }

    public void setDefLov(DefLov defLov) {
        this.defLov = defLov;
    }
}
