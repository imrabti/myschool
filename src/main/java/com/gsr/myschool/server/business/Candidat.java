package com.gsr.myschool.server.business;

import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.server.business.valuelist.ValueList;
import com.gsr.myschool.server.util.BeanMapper;
import com.gsr.myschool.server.validator.Email;
import com.gsr.myschool.server.validator.Name;
import com.gsr.myschool.server.validator.NotBlank;
import com.gsr.myschool.server.validator.Phone;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Entity
public class Candidat implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Name
    @NotBlank
    private String firstname;
    @Name
    @NotBlank
    private String lastname;
    @NotBlank
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @NotBlank
    private String birthLocation;
    @Phone
    private String phone;
    private String cin;
    private String cne;
    @Email
    private String email;
    @Phone
    private String gsm;
    @ManyToOne
    public ValueList bacYear;
    @ManyToOne
    public ValueList bacSerie;
    @ManyToOne
    public ValueList nationality;
    @Transient
    private String birthDateStr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getCne() {
        return cne;
    }

    public void setCne(String cne) {
        this.cne = cne;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public ValueList getBacYear() {
        return bacYear;
    }

    public void setBacYear(ValueList bacYear) {
        this.bacYear = bacYear;
    }

    public ValueList getBacSerie() {
        return bacSerie;
    }

    public void setBacSerie(ValueList bacSerie) {
        this.bacSerie = bacSerie;
    }

    public ValueList getNationality() {
        return nationality;
    }

    public void setNationality(ValueList nationality) {
        this.nationality = nationality;
    }

    public String getBirthDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat(GlobalParameters.DATE_FORMAT);
        return sdf.format(birthDate);
    }

    public void setBirthDateStr(String birthDateStr) {
        this.birthDateStr = birthDateStr;
    }

    public Map getReportsAttributes() {
        return BeanMapper.beanToMap(this);
	}
}
