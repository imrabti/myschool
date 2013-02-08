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
    public Long getId();

    public void setId(Long id);

    public CandidatProxy getCandidat();

    public void setCandidat(CandidatProxy candidat);

    public FiliereProxy getFiliere();

    public void setFiliere(FiliereProxy filiere);

    public NiveauEtudeProxy getNiveauEtude();

    public void setNiveauEtude(NiveauEtudeProxy niveauEtude);

    public UserProxy getOwner();

    public void setOwner(UserProxy owner);

    public Date getCreateDate();

    public void setCreateDate(Date createDate);

    public Date getSubmitDate();

    public void setSubmitDate(Date submitDate);

    public DossierStatus getStatus();

    public void setStatus(DossierStatus status);

    public String getGeneratedPDFPath();

    public void setGeneratedPDFPath(String generatedPDFPath);

    public String getGeneratedNumDossier();

    public void setGeneratedNumDossier(String generatedNumDossier);

    public String getNote();

    public void setNote(String note);

    public Date getRdvEntretien();

    public void setRdvEntretien(Date rdvEntretien);
}
