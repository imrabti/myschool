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

import org.adorsys.xlseasy.annotation.CellAlign;
import org.adorsys.xlseasy.annotation.Sheet;
import org.adorsys.xlseasy.annotation.SheetCellStyle;
import org.adorsys.xlseasy.annotation.SheetColumn;

import java.io.Serializable;

@Sheet(autoSizeColumns = true,
        columnOrder = {"lastname", "firstname", "birthDate", "birthLocation", "phone",
                "cin", "cne", "email", "gsm", "bacYear", "bacSerie", "nationality",
                "filierenom", "niveauEtudenom", "filiere2nom", "niveauEtude2nom", "owneremail",
                "createDate", "submitDate", "status", "generatedNumDossier", "anneeScolaire",
                "niveauEtudeActuel", "formationActuel", "etablissementActuel"})
public class DossierExcelDTO implements Serializable {
    /* candidat */
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Prénom")
    private String firstname;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Nom")
    private String lastname;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Date de naissance")
    private String birthDate;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Lieu de naissance")
    private String birthLocation;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Numéro de téléphone")
    private String phone;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "CIN")
    private String cin;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "CNE")
    private String cne;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Email")
    private String email;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Téléphone GSM")
    private String gsm;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Année du Bac")
    private String bacYear;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Série du Bac")
    private String bacSerie;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Nationalité")
    private String nationality;

    /* scolarité actuelle */
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Etablissement scolaire(Etape3)")
    private String etablissementActuel;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Niveau actuel (Etape3)")
    private String niveauEtudeActuel;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Type d'enseignement (Etape3)")
    private String formationActuel;

    /* filiere */
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Choix 1 filiere")
    private String filierenom;

    /* choix 1 */
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Choix 1 Niveau d'étude")
    private String niveauEtudenom;

    /* filiere 2 */
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Choix 2 Filiere")
    private String filiere2nom;

    /* choix 2 */
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Choix 2 Niveau d'étude")
    private String niveauEtude2nom;

    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Email utilisateur")
    private String owneremail;

    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Date création")
    private String createDate;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Date de soumission")
    private String submitDate;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Status dossier")
    private String status;

    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Numéro dossier")
    private String generatedNumDossier;

    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Année scolaire")
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getEtablissementActuel() {
        return etablissementActuel;
    }

    public void setEtablissementActuel(String etablissementActuel) {
        this.etablissementActuel = etablissementActuel;
    }

    public String getNiveauEtudeActuel() {
        return niveauEtudeActuel;
    }

    public void setNiveauEtudeActuel(String niveauEtudeActuel) {
        this.niveauEtudeActuel = niveauEtudeActuel;
    }

    public String getFormationActuel() {
        return formationActuel;
    }

    public void setFormationActuel(String formationActuel) {
        this.formationActuel = formationActuel;
    }
}
