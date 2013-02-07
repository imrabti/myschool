package com.gsr.myschool.server.business;

import com.gsr.myschool.common.shared.type.ParentType;
import com.gsr.myschool.server.util.BeanMapper;
import com.gsr.myschool.server.validator.Email;
import com.gsr.myschool.server.validator.Name;
import com.gsr.myschool.server.validator.NotBlank;
import com.gsr.myschool.server.validator.Phone;
import org.apache.commons.beanutils.BeanMap;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Map;

@Entity
public class InfoParent implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Name
    @NotBlank
    private String nom;
    @Name
    @NotBlank
    private String prenom;
    @Phone
    private String telGsm;
    @Phone
    @NotBlank
    private String telDom;
    @Phone
    private String telBureau;
    @Email
    @NotBlank
    private String email;
    private String address;
    private String fonction;
    @Enumerated
    private ParentType parentType;
    private String institution;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTelGsm() {
        return telGsm;
    }

    public void setTelGsm(String telGsm) {
        this.telGsm = telGsm;
    }

    public String getTelDom() {
        return telDom;
    }

    public void setTelDom(String telDom) {
        this.telDom = telDom;
    }

    public String getTelBureau() {
        return telBureau;
    }

    public void setTelBureau(String telBureau) {
        this.telBureau = telBureau;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public ParentType getParentType() {
        return parentType;
    }

    public void setParentType(ParentType parentType) {
        this.parentType = parentType;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

	public Map getReportsAttributes() {
        return BeanMapper.beanToMap(this);
	}
}
