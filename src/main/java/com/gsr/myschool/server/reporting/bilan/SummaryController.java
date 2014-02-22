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

package com.gsr.myschool.server.reporting.bilan;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.gsr.myschool.common.shared.dto.BilanDTO;
import com.gsr.myschool.common.shared.dto.SummaryExcelDTO;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.common.shared.type.ValueTypeCode;
import com.gsr.myschool.server.business.core.NiveauEtude;
import com.gsr.myschool.server.business.valuelist.ValueList;
import com.gsr.myschool.server.repos.DossierHistoricRepo;
import com.gsr.myschool.server.repos.NiveauEtudeRepos;
import com.gsr.myschool.server.repos.ValueListRepos;
import com.gsr.myschool.server.repos.ValueTypeRepos;
import com.gsr.myschool.server.service.XlsExportService;
import com.gsr.myschool.server.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.util.List;

@Controller
@RequestMapping("/summary")
public class SummaryController {
    @Autowired
    private DossierHistoricRepo dossierHistoricRepo;
    @Autowired
    private XlsExportService xlsExportService;
    @Autowired
    private ValueListRepos valueListRepos;
    @Autowired
    private ValueTypeRepos valueTypeRepos;
    @Autowired
    private NiveauEtudeRepos niveauEtudeRepos;

    @RequestMapping(method = RequestMethod.GET, produces = "application/vnd.ms-excel")
    @ResponseStatus(HttpStatus.OK)
    public void generateBilan(@RequestParam(required = false) String annee, HttpServletResponse response) {
        ValueList anneeScolaire;

        try {
            anneeScolaire = valueListRepos.findByValueAndValueTypeCode(annee, ValueTypeCode.SCHOOL_YEAR);
            if (anneeScolaire == null) {
                anneeScolaire = getCurrentScholarYear();
            }
        } catch (Exception e) {
            return;
        }

        List<SummaryExcelDTO> dto = getReportSummary(anneeScolaire);

        try {
            response.addHeader("Content-Disposition", "attachment; filename=summary.xls");

            BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            xlsExportService.saveSpreadsheetRecords(SummaryExcelDTO.class, dto, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<SummaryExcelDTO> getReportSummary(ValueList anneeScolaire) {
        List<SummaryExcelDTO> dtos = Lists.newArrayList();

        List<NiveauEtude> niveauEtudes = niveauEtudeRepos.findAll();

        for (NiveauEtude niveauEtude : niveauEtudes) {
            SummaryExcelDTO dto = new SummaryExcelDTO();
            dto.setNiveau(niveauEtude.getFiliere().getNom() + " - " + niveauEtude.getNom());

            BilanDTO created = dossierHistoricRepo.findSummaryHistoric(DossierStatus.CREATED, anneeScolaire, niveauEtude);
            if (created != null) {
                dto.setCreated(Objects.firstNonNull(created.getTotal(), 0L));
            }

            BilanDTO acceptedForStudy = dossierHistoricRepo.findSummaryHistoric(DossierStatus.ACCEPTED_FOR_STUDY, anneeScolaire, niveauEtude);
            if (acceptedForStudy != null) {
                dto.setAcceptedForStudy(Objects.firstNonNull(acceptedForStudy.getTotal(), 0L));
            }

            BilanDTO acceptedForTest = dossierHistoricRepo.findSummaryHistoric(DossierStatus.ACCEPTED_FOR_TEST, anneeScolaire, niveauEtude);
            if (acceptedForTest != null) {
                dto.setAcceptedForTest(Objects.firstNonNull(acceptedForTest.getTotal(), 0L));
            }

            BilanDTO notAcceptedForTest = dossierHistoricRepo.findSummaryHistoric(DossierStatus.NOT_ACCEPTED_FOR_TEST, anneeScolaire, niveauEtude);
            if (notAcceptedForTest != null) {
                dto.setNotAcceptedForTest(Objects.firstNonNull(notAcceptedForTest.getTotal(), 0L));
            }

            BilanDTO affected = dossierHistoricRepo.findSummaryHistoric(DossierStatus.AFFECTED, anneeScolaire, niveauEtude);
            if (affected != null) {
                dto.setAffected(Objects.firstNonNull(affected.getTotal(), 0L));
            }

            BilanDTO received = dossierHistoricRepo.findSummaryHistoric(DossierStatus.RECEIVED, anneeScolaire, niveauEtude);
            if (received != null) {
                dto.setReceived(Objects.firstNonNull(received.getTotal(), 0L));
            }

            BilanDTO rejected = dossierHistoricRepo.findSummaryHistoric(DossierStatus.REJECTED, anneeScolaire, niveauEtude);
            if (rejected != null) {
                dto.setRejected(Objects.firstNonNull(rejected.getTotal(), 0L));
            }

            BilanDTO invitedToTest = dossierHistoricRepo.findSummaryHistoric(DossierStatus.INVITED_TO_TEST, anneeScolaire, niveauEtude);
            if (invitedToTest != null) {
                dto.setInvitedToTest(Objects.firstNonNull(invitedToTest.getTotal(), 0L));
            }

            BilanDTO standBy = dossierHistoricRepo.findSummaryHistoric(DossierStatus.STANDBY, anneeScolaire, niveauEtude);
            if (standBy != null) {
                dto.setStandBy(Objects.firstNonNull(standBy.getTotal(), 0L));
            }

            BilanDTO notAdmited = dossierHistoricRepo.findSummaryHistoric(DossierStatus.NOT_ADMITTED, anneeScolaire, niveauEtude);
            if (notAdmited != null) {
                dto.setNotAdmitted(Objects.firstNonNull(notAdmited.getTotal(), 0L));
            }

            BilanDTO toBeRegistred = dossierHistoricRepo.findSummaryHistoric(DossierStatus.TO_BE_REGISTERED, anneeScolaire, niveauEtude);
            if (toBeRegistred != null) {
                dto.setToBeRegistred(Objects.firstNonNull(toBeRegistred.getTotal(), 0L));
            }

            BilanDTO submited = dossierHistoricRepo.findSummaryHistoric(DossierStatus.SUBMITTED, anneeScolaire, niveauEtude);
            if (submited != null) {
                dto.setSubmitted(Objects.firstNonNull(submited.getTotal(), 0L));
            }
            dtos.add(dto);
        }

        return dtos;
    }

    private ValueList getCurrentScholarYear() {
        String currentScholarYear = DateUtils.currentYear() + "-" + (DateUtils.currentYear() + 1);
        ValueList scholarYear = valueListRepos.findByValueAndValueTypeCode(currentScholarYear, ValueTypeCode.SCHOOL_YEAR);
        if (scholarYear == null) {
            scholarYear = new ValueList();
            scholarYear.setLabel(currentScholarYear);
            scholarYear.setValue(currentScholarYear);
            scholarYear.setValueType(valueTypeRepos.findByCode(ValueTypeCode.SCHOOL_YEAR));

            valueListRepos.save(scholarYear);
        }
        return scholarYear;
    }
}
