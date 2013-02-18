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

package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.common.shared.dto.FraterieDTO;
import com.gsr.myschool.common.shared.type.TypeEnseignement;

import java.util.Date;

@ProxyFor(FraterieDTO.class)
public interface FraterieDTOProxy extends ValueProxy {
    Long getId();

    void setId(Long id);

    CandidatProxy getCandidat();

    void setCandidat(CandidatProxy candidat);

    String getNom();

    void setNom(String nom);

    String getPrenom();

    void setPrenom(String prenom);

    String getNumDossierGSR();

    void setNumDossierGSR(String numDossierGSR);

    NiveauEtudeProxy getNiveau();

    void setNiveau(NiveauEtudeProxy niveau);

    Boolean getValide();

    void setValide(Boolean valide);

    EtablissementScolaireProxy getEtablissement();

    void setEtablissement(EtablissementScolaireProxy etablissement);

    Date getBirthDate();

    void setBirthDate(Date birthDate);

    String getBirthLocation();

    void setBirthLocation(String birthLocation);

    Boolean getScolarise();

    void setScolarise(Boolean scolarise);

    TypeEnseignement getFiliere();

    void setFiliere(TypeEnseignement filiere);
}
