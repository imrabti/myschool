package com.gsr.myschool.server.business;


import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table
public class DefLov {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    @JoinColumn
    @ManyToOne
    private LOV regex;

    @JoinColumn
    @ManyToOne(fetch=FetchType.EAGER)
    private DefLov parent;

    @Column
    private Boolean system;

    public DefLov() {
    }

    public DefLov(String name, LOV regex, DefLov parent, Boolean system) {
        this.name = name;
        this.regex = regex;
        this.parent = parent;
        this.system = system;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LOV getRegex() {
        return regex;
    }

    public void setRegex(LOV regex) {
        this.regex = regex;
    }

    public DefLov getParent() {
        return parent;
    }

    public void setParent(DefLov parent) {
        this.parent = parent;
    }

    public Boolean getSystem() {
        return system;
    }

    public void setSystem(Boolean system) {
        this.system = system;
    }
}
