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

import com.google.common.base.Strings;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.common.shared.dto.BilanDTO;
import com.gsr.myschool.common.shared.dto.ReportDTO;
import com.gsr.myschool.common.shared.type.BilanType;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.server.reporting.ReportService;
import com.gsr.myschool.server.repos.DossierRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/bilan")
public class BilanController {
    @Autowired
    private DossierRepos dossierRepos;
    @Autowired
    private ReportService reportService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/pdf")
    @ResponseStatus(HttpStatus.OK)
    public void generateBilan(@RequestParam(defaultValue = "") String status, @RequestParam String type, HttpServletResponse response) {
        ReportDTO dto = null;
        Integer bilanType;
        DossierStatus dossierStatus = null;
        try {
            bilanType = Integer.parseInt(type);
            if (!Strings.isNullOrEmpty(status)) {
                dossierStatus = DossierStatus.valueOf(status);
            }
        } catch (Exception e) {
            return ;
        }
        if (BilanType.CYCLE.ordinal() == bilanType) {
            List<BilanDTO> dossiers;

            if (Strings.isNullOrEmpty(status)) {
                dossiers = dossierRepos.findBilanCycle();
            } else {
                dossiers = dossierRepos.findBilanCycle(dossierStatus);
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat(GlobalParameters.DATE_FORMAT);
            dto = new ReportDTO("bilan_cycle");

            Map<String, Object> myMap = new HashMap<String, Object>();

            myMap.put("status", dossierStatus==null?null:dossierStatus.toString());
            myMap.put("date", dateFormat.format(new Date()));
            Long total1 = 0L;

            List<Map> myList1 = new ArrayList<Map>();

            for (BilanDTO bilan : dossiers) {
                myList1.add(bilan.getReportsAttributes());
                total1 += bilan.getTotal();
            }
            myMap.put("cycles", myList1);

            myMap.put("cyclesTotal", total1.toString());

            dto.setReportParameters(myMap);
            dto.setFileName("BilanCycle_"+ System.currentTimeMillis()  +".pdf");

        } else if (BilanType.NIVEAU_ETUDE.ordinal() == bilanType) {
            List<BilanDTO> dossiers;
            if (Strings.isNullOrEmpty(status)) {
                dossiers = dossierRepos.findBilanDossier();
            } else {
                dossiers = dossierRepos.findBilanDossier(dossierStatus);
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat(GlobalParameters.DATE_FORMAT);
            dto = new ReportDTO("bilan");

            Map<String, Object> myMap = new HashMap<String, Object>();

            myMap.put("status", dossierStatus==null?null:dossierStatus.toString());
            myMap.put("date", dateFormat.format(new Date()));
            Long total1 = 0L, total2 = 0L, total3 = 0L;

            List<Map> myList1 = new ArrayList<Map>(), myList2 = new ArrayList<Map>(), myList3 = new ArrayList<Map>();

            for (BilanDTO bilan : dossiers) {
                if (bilan.getFiliere().longValue() == GlobalParameters.SECTION_FRANCAISE) {
                    myList1.add(bilan.getReportsAttributes());
                    total1 += bilan.getTotal();
                } else if (bilan.getFiliere().longValue() == GlobalParameters.SECTION_BILINGUE) {
                    myList2.add(bilan.getReportsAttributes());
                    total2 += bilan.getTotal();
                } else if (bilan.getFiliere() >= GlobalParameters.PREPA_FILIERE_FROM) {
                    if (bilan.getNiveau().contains("("))
                    bilan.setNiveau(bilan.getNiveau().substring(0, bilan.getNiveau().indexOf("(")));
                    myList3.add(bilan.getReportsAttributes());
                    total3 += bilan.getTotal();
                }
            }
            myMap.put("francais", myList1);
            myMap.put("bilingues", myList2);
            myMap.put("prepas", myList3);

            myMap.put("francaisTotal", total1.toString());
            myMap.put("bilinguesTotal", total2.toString());
            myMap.put("prepasTotal", total3.toString());

            dto.setReportParameters(myMap);
            dto.setFileName("BilanNiveauEtude_"+ System.currentTimeMillis()  +".pdf");
        }

        try {
            response.addHeader("Content-Disposition", "attachment; filename=" + dto.getFileName());

            BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            byte[] result = reportService.generatePdf(dto);

            outputStream.write(result, 0, result.length);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
