package com.gsr.myschool.server.business.valuelist;

import com.gsr.myschool.common.shared.type.ValueTypeCode;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ValueType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated
    @Column(unique = true)
    private ValueTypeCode code;
    @ManyToOne
    private ValueList regex;
    @ManyToOne
    private ValueType parent;
    private Boolean system;
    private String  name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ValueTypeCode getCode() {
        return code;
    }

    public void setCode(ValueTypeCode code) {
        this.code = code;
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

    public Boolean getSystem() {
        return system;
    }

    public void setSystem(Boolean system) {
        this.system = system;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
