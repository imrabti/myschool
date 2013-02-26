/**
 * Copyright 2012 Nuvola Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gsr.myschool.common.shared.dto;

import com.gsr.myschool.common.shared.type.DossierStatus;
import org.adorsys.xlseasy.annotation.Sheet;
import org.adorsys.xlseasy.annotation.SheetColumn;

import java.io.Serializable;
import java.util.Date;

@Sheet
public class DossierExcelDTO implements Serializable {
    /* candidat */
    @SheetColumn
    private String firstname;
    @SheetColumn
    private String lastname;
    @SheetColumn
    private Date birthDate;
    @SheetColumn
    private String birthLocation;
    @SheetColumn
    private String phone;
    @SheetColumn
    private String cin;
    @SheetColumn
    private String cne;
    @SheetColumn
    private String email;
    @SheetColumn
    private String gsm;
    @SheetColumn
    private String bacYear;
    @SheetColumn
    private String bacSerie;
    @SheetColumn
    private String nationality;


    /* filiere */
    @SheetColumn
    private String filierenom;
    @SheetColumn
    private String filieredescription;

    /* choix 1 */
    @SheetColumn
    private Integer niveauEtudeannee;
    @SheetColumn
    private String niveauEtudenom;

    /* filiere 2 */
    @SheetColumn
    private String filiere2nom;
    @SheetColumn
    private String filiere2description;

    /* choix 2 */
    @SheetColumn
    private Integer niveauEtude2annee;
    @SheetColumn
    private String niveauEtude2nom;

    @SheetColumn
    private String owneremail;

    @SheetColumn
    private Date createDate;
    @SheetColumn
    private Date submitDate;
    @SheetColumn
    private DossierStatus status;

    @SheetColumn
    private String generatedNumDossier;

    @SheetColumn
    private String anneeScolaire;

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

    public String getBacYear() {
        return bacYear;
    }

    public void setBacYear(String bacYear) {
        this.bacYear = bacYear;
    }

    public String getBacSerie() {
        return bacSerie;
    }

    public void setBacSerie(String bacSerie) {
        this.bacSerie = bacSerie;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getFilierenom() {
        return filierenom;
    }

    public void setFilierenom(String filierenom) {
        this.filierenom = filierenom;
    }

    public String getFilieredescription() {
        return filieredescription;
    }

    public void setFilieredescription(String filieredescription) {
        this.filieredescription = filieredescription;
    }

    public Integer getNiveauEtudeannee() {
        return niveauEtudeannee;
    }

    public void setNiveauEtudeannee(Integer niveauEtudeannee) {
        this.niveauEtudeannee = niveauEtudeannee;
    }

    public String getNiveauEtudenom() {
        return niveauEtudenom;
    }

    public void setNiveauEtudenom(String niveauEtudenom) {
        this.niveauEtudenom = niveauEtudenom;
    }

    public String getFiliere2nom() {
        return filiere2nom;
    }

    public void setFiliere2nom(String filiere2nom) {
        this.filiere2nom = filiere2nom;
    }

    public String getFiliere2description() {
        return filiere2description;
    }

    public void setFiliere2description(String filiere2description) {
        this.filiere2description = filiere2description;
    }

    public Integer getNiveauEtude2annee() {
        return niveauEtude2annee;
    }

    public void setNiveauEtude2annee(Integer niveauEtude2annee) {
        this.niveauEtude2annee = niveauEtude2annee;
    }

    public String getNiveauEtude2nom() {
        return niveauEtude2nom;
    }

    public void setNiveauEtude2nom(String niveauEtude2nom) {
        this.niveauEtude2nom = niveauEtude2nom;
    }

    public String getOwneremail() {
        return owneremail;
    }

    public void setOwneremail(String owneremail) {
        this.owneremail = owneremail;
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

    public String getGeneratedNumDossier() {
        return generatedNumDossier;
    }

    public void setGeneratedNumDossier(String generatedNumDossier) {
        this.generatedNumDossier = generatedNumDossier;
    }

    public String getAnneeScolaire() {
        return anneeScolaire;
    }

    public void setAnneeScolaire(String anneeScolaire) {
        this.anneeScolaire = anneeScolaire;
    }
}
