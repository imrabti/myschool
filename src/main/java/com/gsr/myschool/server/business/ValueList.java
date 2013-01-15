package com.gsr.myschool.server.business;

import javax.persistence.*;

@Entity
public class ValueList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String value;
    @ManyToOne
    private ValueList parent;
    @ManyToOne
    private ValueType valueType;

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

    public ValueType getTypeValue() {
        return valueType;
    }

    public void setTypeValue(ValueType typeValue) {
        this.valueType = typeValue;
    }

    public ValueList getParent() {
        return parent;
    }

    public void setParent(ValueList valueList) {
        this.parent = valueList;
    }
}