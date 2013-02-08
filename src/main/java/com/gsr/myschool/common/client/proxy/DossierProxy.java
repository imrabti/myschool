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
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.server.business.Dossier;

import java.util.Date;

@ProxyFor(Dossier.class)
public interface DossierProxy extends ValueProxy {
    Long getId();

    void setId(Long id);

    CandidatProxy getCandidat();

    void setCandidat(CandidatProxy candidat);

    FiliereProxy getFiliere();

    void setFiliere(FiliereProxy filiere);

    NiveauEtudeProxy getNiveauEtude();

    void setNiveauEtude(NiveauEtudeProxy niveauEtude);

    UserProxy getOwner();

    void setOwner(UserProxy owner);

    Date getCreateDate();

    void setCreateDate(Date createDate);

    Date getSubmitDate();

    void setSubmitDate(Date submitDate);

    DossierStatus getStatus();

    void setStatus(DossierStatus status);

    String getGeneratedPDFPath();

    void setGeneratedPDFPath(String generatedPDFPath);

    String getGeneratedNumDossier();

    void setGeneratedNumDossier(String generatedNumDossier);

    String getNote();

    void setNote(String note);

    Date getRdvEntretien();

    void setRdvEntretien(Date rdvEntretien);

    ValueListProxy getAnneeScolaire();

    void setAnneeScolaire(ValueListProxy anneeScolaire);
}
