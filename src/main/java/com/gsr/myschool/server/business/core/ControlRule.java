package com.gsr.myschool.server.business.core;

import javax.persistence.*;

@Entity
public class ControlRule implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private NiveauEtude niveauEtude;
    private String activityContolRule;
    private Integer orderRule;

    public Long getId() {
        return id;
    }

    public void setId(Long newId) {
        this.id = newId;
    }

    public NiveauEtude getNiveauEtude() {
        return niveauEtude;
    }

    public void setNiveauEtude(NiveauEtude newNiveauEtude) {
        this.niveauEtude = newNiveauEtude;
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
