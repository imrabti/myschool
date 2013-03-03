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

import com.gsr.myschool.server.business.Fraterie;

public class FraterieReportDTO {
    private String nom;
    private String prenom;
    private String numDossierGSR;
    private String scolarise;
    private String filiere;
    private String niveau;
    private String etablissement;

    public FraterieReportDTO(Fraterie fraterie) {
        this.nom = fraterie.getNom();
        this.prenom = fraterie.getPrenom();
        this.numDossierGSR = fraterie.getNumDossierGSR() != null ? fraterie.getNumDossierGSR() : "";
        this.scolarise = fraterie.getScolarise() != null ? fraterie.getScolarise().toString() : "";
        this.filiere = fraterie.getFiliere() != null ? fraterie.getFiliere().getNom() : "";
        this.niveau = fraterie.getNiveau() != null ? fraterie.getNiveau().getNom() : "";
        this.etablissement = fraterie.getEtablissement() != null ? fraterie.getEtablissement().getNom() : "";
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

    public String getScolarise() {
        return scolarise;
    }

    public void setScolarise(String scolarise) {
        this.scolarise = scolarise;
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(String etablissement) {
        this.etablissement = etablissement;
    }
}
