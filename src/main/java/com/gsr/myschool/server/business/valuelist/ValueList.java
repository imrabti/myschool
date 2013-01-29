package com.gsr.myschool.server.business.valuelist;

import javax.persistence.*;

@Entity
public class ValueList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String value;
    private String label;
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

    public ValueList getParent() {
        return parent;
    }

    public void setParent(ValueList valueList) {
        this.parent = valueList;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }
}
