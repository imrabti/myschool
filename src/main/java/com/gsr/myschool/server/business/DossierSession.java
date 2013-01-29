package com.gsr.myschool.server.business;

import javax.persistence.*;
import java.util.Date;

@Entity
public class DossierSession implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private AdminUser assignedBy;
    @ManyToOne
    private Dossier dossier;
    private Date dateAffectation;
    private String generatedConvocationPDFPath;

    public Long getId() {
        return id;
    }

    public void setId(Long newId) {
        this.id = newId;
    }

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
