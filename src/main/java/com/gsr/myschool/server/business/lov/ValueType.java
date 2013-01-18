package com.gsr.myschool.server.business.lov;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ValueType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne
    private ValueList regex;
    @ManyToOne
    private ValueType parent;
    private boolean system;

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

    public ValueList getRegex() {
        return regex;
    }

    public void setRegex(ValueList regex) {
        this.regex = regex;
    }

    public ValueType getParent() {
        return parent;
    }

    public void setParent(ValueType parent) {
        this.parent = parent;
    }

    public boolean isSystem() {
        return system;
    }

    public void setSystem(boolean system) {
        this.system = system;
    }
}
