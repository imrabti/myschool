package com.gsr.myschool.server.business;

import com.google.common.base.Strings;
import com.gsr.myschool.common.shared.type.Gender;
import com.gsr.myschool.common.shared.type.ParentType;
import com.gsr.myschool.server.business.valuelist.ValueList;
import com.gsr.myschool.server.util.BeanMapper;
import com.gsr.myschool.server.validator.Email;
import com.gsr.myschool.server.validator.Name;
import com.gsr.myschool.server.validator.NotBlank;
import com.gsr.myschool.server.validator.Phone;

import javax.persistence.*;
import java.util.Date;
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
    @ManyToOne
    private Dossier dossier;
    @Enumerated
    private Gender civilite;
    @NotBlank
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @NotBlank
    private String birthLocation;
    @ManyToOne
    private ValueList nationality;
    private String lientParente;
    private Boolean parentGsr;
    private String promotionGsr;
    private String formationGsr;

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

    public Dossier getDossier() {
        return dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }

    public Gender getCivilite() {
        return civilite;
    }

    public void setCivilite(Gender civilite) {
        this.civilite = civilite;
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

    public ValueList getNationality() {
        return nationality;
    }

    public void setNationality(ValueList nationality) {
        this.nationality = nationality;
    }

    public String getLientParente() {
        return lientParente;
    }

    public void setLientParente(String lientParente) {
        this.lientParente = lientParente;
    }

    public Boolean getParentGsr() {
        return parentGsr;
    }

    public void setParentGsr(Boolean parentGsr) {
        this.parentGsr = parentGsr;
    }

    public String getPromotionGsr() {
        return promotionGsr;
    }

    public void setPromotionGsr(String promotionGsr) {
        this.promotionGsr = promotionGsr;
    }

    public String getFormationGsr() {
        return formationGsr;
    }

    public void setFormationGsr(String formationGsr) {
        this.formationGsr = formationGsr;
    }

    public Map getReportsAttributes() {
        return BeanMapper.beanToMap(this);
    }

    public Boolean isInfoParentEmpty() {
        return Strings.isNullOrEmpty(nom)
                && Strings.isNullOrEmpty(prenom)
                && Strings.isNullOrEmpty(telDom)
                && Strings.isNullOrEmpty(email);
    }
}
