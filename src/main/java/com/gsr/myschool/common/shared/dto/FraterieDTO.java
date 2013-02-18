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

import com.gsr.myschool.common.shared.type.TypeEnseignement;
import com.gsr.myschool.server.business.Candidat;
import com.gsr.myschool.server.business.EtablissementScolaire;
import com.gsr.myschool.server.business.core.NiveauEtude;
import com.gsr.myschool.server.validator.Name;
import com.gsr.myschool.server.validator.NotBlank;

import java.util.Date;

public class FraterieDTO {
    private Long id;
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
    private Date birthDate;
    @NotBlank
    private String birthLocation;
    private Boolean scolarise;
    private TypeEnseignement filiere;
    private NiveauEtude niveau;
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

    public Boolean getValide() {
        return valide;
    }

    public void setValide(Boolean valide) {
        this.valide = valide;
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

    public TypeEnseignement getFiliere() {
        return filiere;
    }

    public void setFiliere(TypeEnseignement filiere) {
        this.filiere = filiere;
    }

    public NiveauEtude getNiveau() {
        return niveau;
    }

    public void setNiveau(NiveauEtude niveau) {
        this.niveau = niveau;
    }

    public EtablissementScolaire getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(EtablissementScolaire etablissement) {
        this.etablissement = etablissement;
    }
}
