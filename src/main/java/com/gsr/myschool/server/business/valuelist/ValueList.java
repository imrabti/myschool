package com.gsr.myschool.server.business.valuelist;

import com.gsr.myschool.server.util.BeanMapper;
import com.gsr.myschool.server.validator.NotBlank;
import org.apache.commons.beanutils.BeanMap;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Map;

@Entity
public class ValueList implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    @Length(max = 30)
    private String value;
    @NotBlank
    @Length(max = 30)
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

	public Map getReportsAttributes() {
        return BeanMapper.beanToMap(this);
	}
}
