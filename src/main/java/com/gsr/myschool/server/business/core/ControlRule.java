package com.gsr.myschool.server.business.core;

import javax.persistence.*;

@Entity
public class ControlRule implements java.io.Serializable {
    @ManyToOne
    private NiveauEtude niveauEtude;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String activityContolRule;
    private Integer orderRule;

    public NiveauEtude getNiveauEtude() {
        return niveauEtude;
    }

    public void setNiveauEtude(NiveauEtude newNiveauEtude) {
        this.niveauEtude = newNiveauEtude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer newId) {
        this.id = newId;
    }

    public String getActivityContolRule() {
        return activityContolRule;
    }

    public void setActivityContolRule(String newActivityContolRule) {
        this.activityContolRule = newActivityContolRule;
    }

    public Integer getOrderRule() {
        return orderRule;
    }

    public void setOrderRule(Integer orderRule) {
        this.orderRule = orderRule;
    }
}