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
import com.gsr.myschool.common.shared.dto.BilanDTO;
import com.gsr.myschool.common.shared.dto.ReportDTO;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.reporting.ReportController;
import com.gsr.myschool.server.reporting.ReportService;
import com.gsr.myschool.server.repos.DossierRepos;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:META-INF/applicationContext.xml",
        "classpath*:/META-INF/applicationContext-security.xml",
        "classpath*:/META-INF/applicationContext-mq.xml",
        "classpath*:/META-INF/applicationContext-activiti.xml"
})
public class JasperTest {
    @Autowired
    ReportService reportService;
    @Autowired
    DossierRepos dossierRepos;
    @Autowired
    ReportController reportController;

    /*@Test
    public void test() {
        ReportDTO dto = new ReportDTO("reportSeconde");
        Map<String, Object> myMap = new HashMap<String, Object>();
        List<Map> myList = new ArrayList<Map>();
        myList.add(new JasperTestDTO("nom1", "18:30", "19:30").getReportsAttributes());
        myList.add(new JasperTestDTO("nom2", "18:30", "19:30").getReportsAttributes());
        myList.add(new JasperTestDTO("nom3", "18:30", "19:30").getReportsAttributes());
        myList.add(new JasperTestDTO("nom4", "18:30", "19:30").getReportsAttributes());
        myList.add(new JasperTestDTO("nom5", "18:30", "19:30").getReportsAttributes());
        myList.add(new JasperTestDTO("nom6", "18:30", "19:30").getReportsAttributes());
        myMap.put("section", "test");
        myMap.put("matieres", myList);
        dto.setReportParameters(myMap);
        String params = "MSGS : date dateTest heureAccueilDebut heureAccueilFin heureDebut heureRecuperation niveauEtude section adresse phone nomParent prenomEnfant" +
                "CP, CECM : MSGS + heureEpreuveFin heureEpreuveDebut" +
                "Seconde : MSGS + matieres";

        try {
            FileOutputStream out = new FileOutputStream("filename.pdf");
            byte[] bytes = reportService.generatePdf(dto);
            out.write(bytes);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    //    @Test
    public void printAllDossiersInFolder() {
        for (Dossier dossier : dossierRepos.findAll()) {
            if (!dossier.getStatus().equals(DossierStatus.CREATED)) {
                ReportDTO dto = new ReportDTO(dossier.getGeneratedNumDossier());
                if (dossier.getFiliere() != null && dossier.getFiliere().getId() >= 30) {
                    dto.setReportName("reportPrepa");
                } else {
                    dto.setReportName("reportGeneral");
                }
                dto = reportController.buildReportDto(dossier);
                try {
                    reportService.generatePdfIntoFolder(dto, dossier.getGeneratedNumDossier());
                } catch (Exception e) {

                    System.err.println(" ERROR IN DOSSIER NUM = " + dossier.getGeneratedNumDossier());
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void printBilan() {
        List<BilanDTO> dossiers = dossierRepos.findBilanDossier();

        SimpleDateFormat dateFormat = new SimpleDateFormat(GlobalParameters.DATE_FORMAT);
        ReportDTO dto = new ReportDTO("bilan");

        Map<String, Object> myMap = new HashMap<String, Object>();

        myMap.put("date", dateFormat.format(new Date()));

        List<Map> myList = new ArrayList<Map>();
        List<Map> myList2 = new ArrayList<Map>();

        for (BilanDTO bilan : dossiers) {
            if (bilan.getFiliere().longValue() == GlobalParameters.SECTION_FRANCAISE) {
                myList.add(bilan.getReportsAttributes());
            } else if (bilan.getFiliere().longValue() == GlobalParameters.SECTION_BILINGUE) {
                myList2.add(bilan.getReportsAttributes());
            }
        }
        myMap.put("bilingues", myList2);
        myMap.put("francais", myList);

        dto.setReportParameters(myMap);
        dto.setFileName("convocation_.pdf");

        try {
            FileOutputStream out = new FileOutputStream("filename.pdf");
            byte[] bytes = reportService.generatePdf(dto);
            out.write(bytes);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
