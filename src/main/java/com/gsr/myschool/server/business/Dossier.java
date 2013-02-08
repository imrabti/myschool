package com.gsr.myschool.server.business;

import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.server.business.core.Filiere;
import com.gsr.myschool.server.business.core.NiveauEtude;
import com.gsr.myschool.server.business.valuelist.ValueList;
import com.gsr.myschool.server.util.BeanMapper;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;

@Entity
public class Dossier implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Candidat candidat;
    @NotNull
    @ManyToOne
    private Filiere filiere;
    @NotNull
    @ManyToOne
    private NiveauEtude niveauEtude;
    @ManyToOne
    private User owner;
    private Date createDate;
    private Date submitDate;
    @Enumerated
    private DossierStatus status;
    private String generatedPDFPath;
    private String generatedNumDossier;
    private String note;
    private Date rdvEntretien;
    @ManyToOne
    private ValueList anneeScolaire;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    public NiveauEtude getNiveauEtude() {
        return niveauEtude;
    }

    public void setNiveauEtude(NiveauEtude niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public DossierStatus getStatus() {
        return status;
    }

    public void setStatus(DossierStatus status) {
        this.status = status;
    }

    public String getGeneratedPDFPath() {
        return generatedPDFPath;
    }

    public void setGeneratedPDFPath(String generatedPDFPath) {
        this.generatedPDFPath = generatedPDFPath;
    }

    public String getGeneratedNumDossier() {
        return generatedNumDossier;
    }

    public void setGeneratedNumDossier(String generatedNumDossier) {
        this.generatedNumDossier = generatedNumDossier;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getRdvEntretien() {
        return rdvEntretien;
    }

    public void setRdvEntretien(Date rdvEntretien) {
        this.rdvEntretien = rdvEntretien;
    }

    public ValueList getAnneeScolaire() {
        return anneeScolaire;
    }

    public void setAnneeScolaire(ValueList anneeScolaire) {
        this.anneeScolaire = anneeScolaire;
    }

    public Map getReportsAttributes() {
        return BeanMapper.beanToMap(this);
    }
}
