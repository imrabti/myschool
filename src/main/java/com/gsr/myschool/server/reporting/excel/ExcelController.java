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

import com.google.common.base.Strings;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.common.shared.dto.DossierExcelDTO;
import com.gsr.myschool.common.shared.dto.DossierFilterDTO;
import com.gsr.myschool.common.shared.dto.DossierMultiple;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.service.DossierService;
import com.gsr.myschool.server.service.XlsExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ExcelController {
    private static String TMP_FOLDER_PATH = "/tmp/";

    @Autowired
    private DossierService dossierService;
    @Autowired
    private XlsExportService xlsExportService;

    @RequestMapping(method = RequestMethod.POST, value = "/excel")
    @ResponseStatus(HttpStatus.OK)
    public void generateExcel(@RequestBody DossierFilterDTO requestdata, HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Dossier> dossiers = dossierService.findAllDossiersByCriteria(requestdata, null, null).getDossiers();
            List<DossierExcelDTO> resultDossiers = map(dossiers);

            String fileName = new Date().getTime() + ".xls";
            File file = new File(request.getSession().getServletContext().getRealPath("/") + TMP_FOLDER_PATH + fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            xlsExportService.saveSpreadsheetRecords(DossierExcelDTO.class, resultDossiers, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            response.getWriter().println(fileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/excel", produces = "application/vnd.ms-excel")
    @ResponseStatus(HttpStatus.OK)
    public void generateExcel(@RequestParam String fileName, HttpServletRequest request, HttpServletResponse response) {
        try {
            final int buffersize = 1024;
            final byte[] buffer = new byte[buffersize];

            response.addHeader("Content-Disposition", "attachment; filename=recherche.xls");

            File file = new File(request.getSession().getServletContext().getRealPath("/") + TMP_FOLDER_PATH + fileName);
            InputStream inputStream = new FileInputStream(file);
            BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());

            int available = 0;
            while ((available = inputStream.read(buffer)) >= 0) {
                outputStream.write(buffer, 0, available);
            }

            inputStream.close();

            outputStream.flush();
            outputStream.close();

            file.delete();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/excel/multidossier", produces = "application/vnd.ms-excel")
    public void generateMultiDossierExcel(@RequestParam String q, HttpServletResponse response) {
        DossierStatus status = Strings.isNullOrEmpty(q) ? null :  DossierStatus.valueOf(q);
        List<DossierMultiple> dossierMultiples = dossierService.findMultipleDossierByStatus(status);

        try {
            response.addHeader("Content-Disposition", "attachment; filename=multidossier_" + System.currentTimeMillis() + ".xls");

            BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            xlsExportService.saveSpreadsheetRecords(DossierMultiple.class, dossierMultiples, outputStream);

            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<DossierExcelDTO> map(List<Dossier> dossiers) {
        List<DossierExcelDTO> resultDossiers = new ArrayList<DossierExcelDTO>();
        SimpleDateFormat dateFormat = new SimpleDateFormat(GlobalParameters.DATE_FORMAT);
        for (Dossier dossier : dossiers) {
            DossierExcelDTO d = new DossierExcelDTO();
            /* candidat */
            if (dossier.getCandidat() != null) {
                d.setCin(dossier.getCandidat().getCin());
                d.setCne(dossier.getCandidat().getCne());
                d.setEmail(dossier.getCandidat().getEmail());
                d.setFirstname(dossier.getCandidat().getFirstname());
                d.setLastname(dossier.getCandidat().getLastname());
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
                if (dossier.getCandidat().getBirthDate() != null) {
                    d.setBirthDate(dateFormat.format(dossier.getCandidat().getBirthDate()));
                }

            }

            /* Scolarite actuelle */
            if (dossier.getScolariteActuelle() != null) {
                if (dossier.getScolariteActuelle().getEtablissement() != null) {
                    d.setEtablissementActuel(dossier.getScolariteActuelle().getEtablissement().getNom());
                }
                if (dossier.getScolariteActuelle().getFiliere() != null) {
                    d.setFormationActuel(dossier.getScolariteActuelle().getFiliere().getNom());
                }
                if (dossier.getScolariteActuelle().getNiveauEtude() != null) {
                    d.setNiveauEtudeActuel(dossier.getScolariteActuelle().getNiveauEtude().getNom());
                }
            }

            /* filieres */
            if (dossier.getFiliere2() != null) {
                d.setFiliere2nom(dossier.getFiliere2().getNom());
            }
            if (dossier.getFiliere() != null) {
                d.setFilierenom(dossier.getFiliere().getNom());
            }
            if (dossier.getNiveauEtude2() != null) {
                d.setNiveauEtude2nom(dossier.getNiveauEtude2().getNom());
            }
            if (dossier.getNiveauEtude() != null) {
                d.setNiveauEtudenom(dossier.getNiveauEtude().getNom());
            }

            /* autres infos */
            if (dossier.getAnneeScolaire() != null) {
                d.setAnneeScolaire(dossier.getAnneeScolaire().getLabel());
            }
            if (dossier.getOwner() != null) {
                d.setOwneremail(dossier.getOwner().getEmail());
            }
            if (dossier.getCreateDate() != null) {
                d.setCreateDate(dateFormat.format(dossier.getCreateDate()));
            }
            if (dossier.getSubmitDate() != null) {
                d.setSubmitDate(dateFormat.format(dossier.getSubmitDate()));
            }
            if (dossier.getStatus() != null) {
                d.setStatus(dossier.getStatus().toString());
            }

            resultDossiers.add(d);
        }
        return resultDossiers;
    }
}
