package com.gsr.myschool.server.business;

import javax.persistence.*;
import java.util.Date;

@Entity
public class DossierSession implements java.io.Serializable {
    @ManyToOne
    private AdminUser assignedBy;
    @ManyToOne
    private Dossier dossier;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Date dateAffectation;
    private String generatedConvocationPDFPath;

    public AdminUser getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(AdminUser newGsrUser) {
        this.assignedBy = newGsrUser;
    }

    public Dossier getDossier() {
        return dossier;
    }

    public void setDossier(Dossier newDossier) {
        this.dossier = newDossier;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer newId) {
        this.id = newId;
    }

    public Date getDateAffectation() {
        return dateAffectation;
    }

    public void setDateAffectation(Date newDateAffectation) {
        this.dateAffectation = newDateAffectation;
    }

    public String getGeneratedConvocationPDFPath() {
        return generatedConvocationPDFPath;
    }

    public void setGeneratedConvocationPDFPath(String newGeneratedConvocationPDFPath) {
        this.generatedConvocationPDFPath = newGeneratedConvocationPDFPath;
    }
}
