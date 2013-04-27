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

import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.common.shared.dto.DossierConvocationDTO;
import com.gsr.myschool.common.shared.dto.DossierConvoqueExcelDTO;
import com.gsr.myschool.common.shared.dto.DossierFilterDTO;
import com.gsr.myschool.common.shared.dto.ReportDTO;
import com.gsr.myschool.server.business.Candidat;
import com.gsr.myschool.server.reporting.ReportService;
import com.gsr.myschool.server.service.DossierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ConvocationReportController {
    private static String TMP_FOLDER_PATH = "/tmp/";

    @Autowired
    private DossierService dossierService;
    @Autowired
    private ReportService reportService;
    @Value("${convocationReport}")
    private String convocationReport;

    @RequestMapping(method = RequestMethod.POST, value = "/convocationReport")
    @ResponseStatus(HttpStatus.OK)
    public void generateExcel(@RequestBody DossierFilterDTO requestdata, HttpServletRequest request, HttpServletResponse response) {
        try {

            List<DossierConvocationDTO> dossiers = dossierService.findAllDossiersBySessionAndCriteria(requestdata, null, null).getDossierConvocationDTOs();

            ReportDTO dto = new ReportDTO(convocationReport);
            Map<String, Object> myMap = new HashMap<String, Object>();
            List<Map> myList = new ArrayList<Map>();

            SimpleDateFormat dateFormat = new SimpleDateFormat(GlobalParameters.DATE_FORMAT);
            for (DossierConvocationDTO dossier : dossiers) {
                DossierConvoqueExcelDTO d = new DossierConvoqueExcelDTO();
                /* candidat */
                Candidat candidat = dossier.getDossierSession().getDossier().getCandidat();
                if (candidat != null) {
                    d.setName(candidat.getLastname() + " " + candidat.getFirstname());
                    if (candidat.getBirthDate() != null) {
                        d.setBirthDate(dateFormat.format(candidat.getBirthDate()));
                    }
                }

                /* autres infos */
                if (dossier.getDossierSession().getDossier().getScolariteActuelle().getEtablissement() != null) {
                    d.setEtablissement(dossier.getDossierSession().getDossier().
                            getScolariteActuelle().getEtablissement().getNom());
                }
                if (dossier.getDossierSession().getDossier().getGeneratedNumDossier() != null) {
                    d.setGeneratedNumDossier(dossier.getDossierSession().getDossier().getGeneratedNumDossier());
                }
                if (dossier.getGsrFraterie() != null) {
                    d.setFraterieGsr(dossier.getHaveFraterie());
                }
                if (dossier.getGsrParent() != null) {
                    d.setParentGsr(dossier.getHaveParentGsr());
                }
                if (dossier.getDossierSession().getSessionExamen() != null) {
                    d.setSession(dossier.getDossierSession().getSessionExamen().getNom());
                }
                myList.add(d.getReportsAttributes());
            }

            String niveauEtude = "";
            if (requestdata.getNiveauEtude().getNom().contains("(")) {
                niveauEtude = requestdata.getNiveauEtude().getNom().contains("(")
                        ? requestdata.getNiveauEtude().getNom().substring(0, requestdata.getNiveauEtude().getNom().indexOf("("))
                        : requestdata.getNiveauEtude().getNom();
            }
            myMap.put("dossiers", myList);
            myMap.put("section", requestdata.getFiliere() != null ? requestdata.getFiliere().getNom() : "");
            myMap.put("cycle", niveauEtude);
            myMap.put("date", dateFormat.format(new Date()));
            myMap.put("status", requestdata.getStatus() != null ? requestdata.getStatus().toString() : null);

            dto.setReportParameters(myMap);

            String fileName = new Date().getTime() + ".pdf";
            File file = new File(request.getSession().getServletContext().getRealPath("/") + TMP_FOLDER_PATH + fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            reportService.generatePdfIntoFolder(dto, request.getSession().getServletContext().getRealPath("/") + TMP_FOLDER_PATH + fileName);

            response.getWriter().println(fileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/convocationReport", produces = "application/pdf")
    @ResponseStatus(HttpStatus.OK)
    public void generateExcel(@RequestParam String fileName, HttpServletRequest request, HttpServletResponse response) {
        try {
            final int buffersize = 1024;
            final byte[] buffer = new byte[buffersize];

            response.addHeader("Content-Disposition", "attachment; filename=convocation_report.pdf");

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
}
