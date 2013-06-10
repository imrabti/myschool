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

package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.common.shared.dto.DossierFilterDTO;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.common.shared.type.EmailType;
import com.gsr.myschool.common.shared.type.SettingsKey;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.business.EmailTemplate;
import com.gsr.myschool.server.business.Settings;
import com.gsr.myschool.server.business.core.*;
import com.gsr.myschool.server.process.ValidationProcessService;
import com.gsr.myschool.server.repos.*;
import com.gsr.myschool.server.repos.spec.DossierSpec;
import com.gsr.myschool.server.service.DossierService;
import com.gsr.myschool.server.service.InscriptionService;
import com.gsr.myschool.server.service.SettingsService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Service
@Transactional
public class SettingsServiceImpl implements SettingsService {
    @Autowired
    private SettingsRepos settingsRepos;
    @Autowired
    private FiliereRepos filiereRepos;
    @Autowired
    private NiveauEtudeRepos niveauEtudeRepos;
    @Autowired
    private PieceJustifRepos pieceJustifRepos;
    @Autowired
    private PieceJustifDuNERepos pieceJustifDuNERepos;
    @Autowired
    private MatiereExamenRepos matiereExamenRepos;
    @Autowired
    private MatiereExamenNERepos matiereExamenNERepos;
    @Autowired
    private EmailTemplateRepos emailTemplateRepos;
    @Autowired
    private InscriptionService inscriptionService;
    @Autowired
    private DossierService dossierService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private DossierRepos dossierRepos;
    @Autowired
    private ValidationProcessService validationProcessService;

    @Override
    public void updateSettings(SettingsKey key, String value) {
        Settings setting = settingsRepos.findOne(key);
        setting.setValue(value);
        settingsRepos.save(setting);
    }

    @Override
    public String getSetting(SettingsKey key) {
        Settings setting = settingsRepos.findOne(key);
        return setting != null ? setting.getValue() : "";
    }

    @Override
    public Boolean addFiliere(Filiere filiere) {
        filiereRepos.save(filiere);
        return true;
    }

    @Override
    public Boolean addNiveauEtude(NiveauEtude niveauEtude) {
        niveauEtude.setFiliere(filiereRepos.findOne(niveauEtude.getFiliere().getId()));
        niveauEtudeRepos.save(niveauEtude);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PieceJustif> findAllPieceJustif() {
        return pieceJustifRepos.findAll();
    }

    @Override
    public Boolean deletePieceJustif(PieceJustif piece) {
        List<PieceJustifDuNE> result = pieceJustifDuNERepos.findByPieceJustifId(piece.getId());
        if (result == null || result.isEmpty()) {
            pieceJustifRepos.delete(piece);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean addPieceJustif(PieceJustif piece) {
        try {
            pieceJustifRepos.save(piece);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatiereExamen> findAllMatiereExamen() {
        return matiereExamenRepos.findAll();
    }

    @Override
    public Boolean deleteMatiereExamen(MatiereExamen matiere) {
        List<MatiereExamDuNE> result = matiereExamenNERepos.findByMatiereExamenId(matiere.getId());
        if (result == null || result.isEmpty()) {
            matiereExamenRepos.delete(matiere);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean addMatiereExamen(MatiereExamen matiere) {
        try {
            matiereExamenRepos.save(matiere);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean updateTemplateEmail(EmailTemplate template) {
        try {
            emailTemplateRepos.save(template);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public EmailTemplate findEmailTemplateByCode(EmailType code) {
        return emailTemplateRepos.findByCode(code);
    }

    @Override
    public void deleteDossiers(Boolean isPrepa) {
        DossierFilterDTO filter = new DossierFilterDTO();
        filter.setStatus(DossierStatus.CREATED);

        List<Dossier> dossiers = dossierService.findAllDossiersByCriteria(filter, null, null).getDossiers();

        for (Dossier dossier : dossiers) {
            // ne supprimer que les dossier crées non soumis des prepa
            Boolean skipCondition = isPrepa ?
                    dossier.getFiliere() != null && dossier.getFiliere().getId().longValue() < GlobalParameters.PREPA_FILIERE_FROM :
                    dossier.getFiliere() != null && dossier.getFiliere().getId().longValue() >= GlobalParameters.PREPA_FILIERE_FROM;
            if (skipCondition)
                continue;

            if (dossier.getCreateDate() != null) {
                Calendar date = new GregorianCalendar();
                date.setTime(dossier.getCreateDate());
                date.add(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
                Calendar deleteAfterThis = new GregorianCalendar();

                if (deleteAfterThis.after(date)) {
                    try {
                        inscriptionService.deleteInscription(dossier.getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void correctionProcess() {
        List<Dossier> dossiers = dossierRepos.findAll(DossierSpec.dossierStatusIs(DossierStatus.SUBMITTED));
        int count = 0;
        for (Dossier dossier : dossiers) {
            try {
                HistoricProcessInstance historic = historyService.createHistoricProcessInstanceQuery()
                        .finished()
                        .processDefinitionKey("validation")
                        .processInstanceBusinessKey(dossier.getId().toString())
                        .singleResult();
                if (historic != null) {
                    historyService.deleteHistoricProcessInstance(historic.getId());
                }
                ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                        .processDefinitionKey("validation")
                        .processInstanceBusinessKey(dossier.getId().toString())
                        .singleResult();
                if (pi == null) {
                    validationProcessService.startProcess(dossier);
                    count++;
                }
            } catch (Exception e) {
                System.out.println(dossier.getId().toString());
                e.printStackTrace();
            }
        }
        System.out.println("Number of process started = " + count);
    }
}
