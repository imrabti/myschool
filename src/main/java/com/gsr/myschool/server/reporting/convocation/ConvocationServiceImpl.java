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

package com.gsr.myschool.server.reporting.convocation;

import com.google.common.base.Strings;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.common.shared.dto.ReportDTO;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.business.DossierSession;
import com.gsr.myschool.server.business.InfoParent;
import com.gsr.myschool.server.business.core.NiveauEtude;
import com.gsr.myschool.server.business.core.SessionExamen;
import com.gsr.myschool.server.business.core.SessionNiveauEtude;
import com.gsr.myschool.server.reporting.ReportService;
import com.gsr.myschool.server.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ConvocationServiceImpl implements ConvocationService {
    @Autowired
    ReportService reportService;
    @Value("${reportCECM}")
    String reportCECM;
    @Value("${reportMSGS}")
    String reportMSGS;
    @Value("${reportSeconde}")
    String reportSeconde;
    @Value("${reportCP}")
    String reportCP;
    @Autowired
    private NiveauEtudeRepos niveauEtudeRepos;
    @Autowired
    private SessionExamenNERepos sessionExamenNERepos;
    @Autowired
    private SessionExamenRepos sessionExamenRepos;
    @Autowired
    private DossierSessionRepos dossierSessionRepos;
    @Autowired
    private InfoParentRepos infoParentRepos;

    @Override
    public File generateConvocation(Dossier dossier) {
        DossierSession dossierSession = dossierSessionRepos.findByDossierId(dossier.getId());
        List<InfoParent> parents = infoParentRepos.findByDossierId(dossier.getId());

        NiveauEtude niveauEtude = dossier.getNiveauEtude();
        SessionExamen session = dossierSession.getSessionExamen();
        if (niveauEtude == null || session == null) return null;

        List<SessionNiveauEtude> matieres = sessionExamenNERepos.findBySessionExamenIdAndNiveauEtudeId(session.getId(),
                niveauEtude.getId());

        SimpleDateFormat dateFormat = new SimpleDateFormat(GlobalParameters.DATE_FORMAT);
        ReportDTO dto;

        Map<String, Object> myMap = new HashMap<String, Object>();

        myMap.put("date", dateFormat.format(new Date()));

        if (session.getDateSession() != null) {
            myMap.put("dateTest", dateFormat.format(session.getDateSession()));
        } else {
            myMap.put("dateTest", "8:00");
        }

        myMap.put("heureAccueilDebut", session.getWelcomKids());
        myMap.put("heureAccueilFin", "8:00");

        myMap.put("heureDebut", session.getDebutTest());
        myMap.put("heureRecuperation", session.getGatherKids());

        myMap.put("niveauEtude", niveauEtude.getNom());
        myMap.put("section", niveauEtude.getFiliere().getNom());

        myMap.put("adresse", session.getAdresse());
        myMap.put("phone", session.getTelephone());

        for (InfoParent parent : parents) {
            if (!Strings.isNullOrEmpty(parent.getNom())) {
                myMap.put("nomParent", parent.getNom());
            }
        }
        myMap.put("prenomEnfant", dossier.getCandidat().getFirstname());

        if (niveauEtude.getAnnee() <= 5 && niveauEtude.getAnnee() >= 4) {
            dto = new ReportDTO(reportMSGS);
        } else if (niveauEtude.getAnnee() == 6) {
            dto = new ReportDTO(reportCP);
            myMap.put("heureEpreuveDebut", matieres.get(0).getHoraireDe());
            myMap.put("heureEpreuveFin", matieres.get(0).getHoraireA());
        } else if (niveauEtude.getAnnee() >= 7 && niveauEtude.getAnnee() <= 10) {
            dto = new ReportDTO(reportCECM);
            myMap.put("heureEpreuveDebut", matieres.get(0).getHoraireDe());
            myMap.put("heureEpreuveFin", matieres.get(0).getHoraireA());
        } else {
            dto = new ReportDTO(reportSeconde);

            List<Map> myList = new ArrayList<Map>();
            for (SessionNiveauEtude matiere : matieres) {
                myList.add(matiere.getReportsAttributes());
            }
            myMap.put("matieres", myList);
        }

        dto.setReportParameters(myMap);

        try {
            File convocation = new File("convocation_" + dossier.getGeneratedNumDossier() + ".pdf");
            FileOutputStream fs = new FileOutputStream(convocation);
            BufferedOutputStream outputStream = new BufferedOutputStream(fs);
            byte[] result = reportService.generatePdf(dto);

            outputStream.write(result, 0, result.length);
            outputStream.flush();
            outputStream.close();

            return convocation;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ReportDTO generateConvocationTest(Long niveauId, Long sessionId) {
        NiveauEtude niveauEtude = niveauEtudeRepos.findOne(Long.valueOf(niveauId));
        SessionExamen session = sessionExamenRepos.findOne(Long.valueOf(sessionId));
        List<SessionNiveauEtude> matieres = sessionExamenNERepos.findBySessionExamenIdAndNiveauEtudeId(Long.valueOf(sessionId), Long.valueOf(niveauId));

        SimpleDateFormat dateFormat = new SimpleDateFormat(GlobalParameters.DATE_FORMAT);
        ReportDTO dto;

        Map<String, Object> myMap = new HashMap<String, Object>();

        myMap.put("date", dateFormat.format(new Date()));
        if (session.getDateSession() != null) {
            myMap.put("dateTest", dateFormat.format(session.getDateSession()));
        } else {
            myMap.put("dateTest", "8:00");
        }

        myMap.put("heureAccueilDebut", Strings.isNullOrEmpty(session.getWelcomKids()) ? "" : session.getWelcomKids());
        myMap.put("heureAccueilFin", "8:00");

        myMap.put("heureDebut", Strings.isNullOrEmpty(session.getDebutTest()) ? "8:00" : session.getDebutTest());
        myMap.put("heureRecuperation", Strings.isNullOrEmpty(session.getGatherKids()) ? "8:00" : session.getGatherKids());

        myMap.put("niveauEtude", niveauEtude.getNom());
        myMap.put("section", niveauEtude.getFiliere().getNom());

        myMap.put("adresse", Strings.isNullOrEmpty(session.getAdresse()) ? "GSR, 2 Mars, Casablanca" : session.getAdresse());
        myMap.put("phone", Strings.isNullOrEmpty(session.getTelephone()) ? "06998877" : session.getTelephone());

        myMap.put("nomParent", "NomParent");
        myMap.put("prenomEnfant", "Mehdi");

        if (niveauEtude.getAnnee() <= 5 && niveauEtude.getAnnee() >= 4) {
            dto = new ReportDTO(reportMSGS);
        } else if (niveauEtude.getAnnee() == 6) {
            dto = new ReportDTO(reportCP);

            if (matieres.isEmpty()) {
                myMap.put("heureEpreuveDebut", "8:45");
                myMap.put("heureEpreuveFin", "11:30");
            } else {
                myMap.put("heureEpreuveDebut", matieres.get(0).getHoraireDe());
                myMap.put("heureEpreuveFin", matieres.get(0).getHoraireA());
            }
        } else if (niveauEtude.getAnnee() >= 7 && niveauEtude.getAnnee() <= 10) {
            dto = new ReportDTO(reportCECM);

            if (matieres.isEmpty()) {
                myMap.put("heureEpreuveDebut", "8:45");
                myMap.put("heureEpreuveFin", "11:30");
            } else {
                myMap.put("heureEpreuveDebut", matieres.get(0).getHoraireDe());
                myMap.put("heureEpreuveFin", matieres.get(0).getHoraireA());
            }
        } else {
            dto = new ReportDTO(reportSeconde);

            List<Map> myList = new ArrayList<Map>();
            for (SessionNiveauEtude matiere : matieres) {
                myList.add(matiere.getReportsAttributes());
            }
            myMap.put("matieres", myList);
        }

        dto.setReportParameters(myMap);

        return dto;
    }
}
