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

package com.gsr.myschool.server.reporting.excel;

import com.gsr.myschool.common.shared.dto.DossierExcelDTO;
import com.gsr.myschool.common.shared.dto.DossierFilterDTO;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.service.DossierService;
import com.gsr.myschool.server.service.XlsExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/excel")
public class ExcelController {
    @Autowired
    private DossierService dossierService;
    @Autowired
    private XlsExportService xlsExportService;

    @RequestMapping(method = RequestMethod.POST, produces = "application/vnd.ms-excel")
    @ResponseStatus(HttpStatus.OK)
    public void generateExcel(@RequestBody DossierFilterDTO requestdata, HttpServletResponse response) {
        try {
            List<Dossier> dossiers = dossierService.findAllDossiersByCriteria(requestdata);
            List<DossierExcelDTO> resultDossiers = map(dossiers);

            response.addHeader("Content-Disposition", "attachment; filename=test.xls");

            OutputStream outputStream = response.getOutputStream();
            xlsExportService.saveSpreadsheetRecords(DossierExcelDTO.class, resultDossiers, outputStream);

            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<DossierExcelDTO> map(List<Dossier> dossiers) {
        List<DossierExcelDTO> resultDossiers = new ArrayList<DossierExcelDTO>();
        for (Dossier dossier : dossiers) {
            DossierExcelDTO d = new DossierExcelDTO();
            /* candidat */
            if (dossier.getCandidat() != null) {
                d.setCin(dossier.getCandidat().getCin());
                d.setCne(dossier.getCandidat().getCne());
                d.setEmail(dossier.getCandidat().getEmail());
                d.setFirstname(dossier.getCandidat().getFirstname());
                d.setLastname(dossier.getCandidat().getLastname());
                d.setBirthDate(dossier.getCandidat().getBirthDate());
                d.setBirthLocation(dossier.getCandidat().getBirthLocation());
                d.setGsm(dossier.getCandidat().getGsm());
                d.setPhone(dossier.getCandidat().getPhone());
                if (dossier.getCandidat().getBacSerie() != null) {
                    d.setBacSerie(dossier.getCandidat().getBacSerie().getLabel());
                }
                if (dossier.getCandidat().getBacYear() != null) {
                    d.setBacYear(dossier.getCandidat().getBacYear().getLabel());
                }
                if (dossier.getCandidat().getNationality() != null) {
                    d.setNationality(dossier.getCandidat().getNationality().getLabel());
                }
            }

            /* filieres */
            if (dossier.getFiliere2() != null) {
                d.setFiliere2description(dossier.getFiliere2().getDescription());
                d.setFiliere2nom(dossier.getFiliere2().getNom());
            }
            if (dossier.getFiliere() != null) {
                d.setFilieredescription(dossier.getFiliere().getDescription());
                d.setFilierenom(dossier.getFiliere().getNom());
            }
            if (dossier.getNiveauEtude2() != null) {
                d.setNiveauEtude2annee(dossier.getNiveauEtude2().getAnnee());
                d.setNiveauEtude2nom(dossier.getNiveauEtude2().getNom());
            }
            if (dossier.getNiveauEtude() != null) {
                d.setNiveauEtudeannee(dossier.getNiveauEtude().getAnnee());
                d.setNiveauEtudenom(dossier.getNiveauEtude().getNom());
            }

            /* autres infos */
            if (dossier.getAnneeScolaire() != null) {
                d.setAnneeScolaire(dossier.getAnneeScolaire().getLabel());
            }
            if (dossier.getOwner() != null) {
                d.setOwneremail(dossier.getOwner().getEmail());
            }
            d.setCreateDate(dossier.getCreateDate());
            d.setSubmitDate(dossier.getSubmitDate());
            d.setSubmitDate(dossier.getSubmitDate());
            d.setStatus(dossier.getStatus());

            resultDossiers.add(d);
        }
        return resultDossiers;
    }
}
