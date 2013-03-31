package com.gsr.myschool.common.shared.dto;

public class NiveauEtudeNode implements Comparable<NiveauEtudeNode> {
    private Long id;
    private String name;
    private Boolean complete;
    private Integer annee;

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

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    @Override
    public int compareTo(NiveauEtudeNode o) {
        return this.getAnnee().compareTo(o.getAnnee());
    }
}
