package com.gsr.myschool.server.business;

import com.gsr.myschool.server.business.core.Filiere;
import com.gsr.myschool.server.business.core.NiveauEtude;
import com.gsr.myschool.server.util.BeanMapper;
import com.gsr.myschool.server.validator.Name;
import com.gsr.myschool.server.validator.NotBlank;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Entity
public class Fraterie implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Candidat candidat;
    @Name
    @NotBlank
    private String nom;
    @Name
    @NotBlank
    private String prenom;
    private String numDossierGSR;
    private Boolean valide;
    @NotBlank
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @NotBlank
    private String birthLocation;
    private Boolean scolarise;
    @ManyToOne
    private Filiere filiere;
    @ManyToOne
    private NiveauEtude niveau;
    @ManyToOne
    private EtablissementScolaire etablissement;

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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumDossierGSR() {
        return numDossierGSR;
    }

    public void setNumDossierGSR(String numDossierGSR) {
        this.numDossierGSR = numDossierGSR;
    }

    public NiveauEtude getNiveau() {
        return niveau;
    }

    public void setNiveau(NiveauEtude niveau) {
        this.niveau = niveau;
    }

    public Boolean getValide() {
        return valide;
    }

    public void setValide(Boolean valide) {
        this.valide = valide;
    }

    public EtablissementScolaire getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(EtablissementScolaire etablissement) {
        this.etablissement = etablissement;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthLocation() {
        return birthLocation;
    }

    public void setBirthLocation(String birthLocation) {
        this.birthLocation = birthLocation;
    }

    public Boolean getScolarise() {
        return scolarise;
    }

    public void setScolarise(Boolean scolarise) {
        this.scolarise = scolarise;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    public Map getReportsAttributes() {
        return BeanMapper.beanToMap(this);
    }
}
