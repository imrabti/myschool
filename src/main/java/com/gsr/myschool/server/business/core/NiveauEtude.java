package com.gsr.myschool.server.business.core;

import com.gsr.myschool.common.shared.type.TypeNiveauEtude;
import com.gsr.myschool.server.util.BeanMapper;
import org.apache.commons.beanutils.BeanMap;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Map;

@Entity
public class NiveauEtude implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Filiere filiere;
    private Integer annee;
    private String nom;
    @Enumerated
    private TypeNiveauEtude type;

    public Long getId() {
        return id;
    }

    public void setId(Long newId) {
        this.id = newId;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere newFiliere) {
        this.filiere = newFiliere;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer newAnnee) {
        this.annee = newAnnee;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String newNom) {
        this.nom = newNom;
    }

    public TypeNiveauEtude getType() {
        return type;
    }

    public void setType(TypeNiveauEtude type) {
        this.type = type;
    }

    public Map getReportsAttributes() {
        return BeanMapper.beanToMap(this);
	}
}
