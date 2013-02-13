package com.gsr.myschool.server.business;

import com.gsr.myschool.common.shared.type.TypeFraterie;
import com.gsr.myschool.common.shared.type.TypeNiveauEtude;
import com.gsr.myschool.server.util.BeanMapper;
import com.gsr.myschool.server.validator.Name;
import com.gsr.myschool.server.validator.NotBlank;

import javax.persistence.*;
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
    @Enumerated
    private TypeNiveauEtude niveau;
    private String classe;
    private String etablissement;
    @Enumerated
    private TypeFraterie typeFraterie;
    private Boolean valide;

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

    public TypeFraterie getTypeFraterie() {
        return typeFraterie;
    }

    public void setTypeFraterie(TypeFraterie typeFraterie) {
        this.typeFraterie = typeFraterie;
    }

    public Boolean getValide() {
        return valide;
    }

    public void setValide(Boolean valide) {
        this.valide = valide;
    }

    public TypeNiveauEtude getNiveau() {
        return niveau;
    }

    public void setNiveau(TypeNiveauEtude niveau) {
        this.niveau = niveau;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(String etablissement) {
        this.etablissement = etablissement;
    }

    public Map getReportsAttributes() {
        return BeanMapper.beanToMap(this);
    }
}
