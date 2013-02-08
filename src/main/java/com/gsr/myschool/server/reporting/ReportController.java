package com.gsr.myschool.server.reporting;

import com.gsr.myschool.common.shared.dto.ReportDTO;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.business.Fraterie;
import com.gsr.myschool.server.business.InfoParent;
import com.gsr.myschool.server.repos.DossierRepos;
import com.gsr.myschool.server.repos.FraterieRepos;
import com.gsr.myschool.server.repos.InfoParentRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/report")
public class ReportController {
    @Autowired
    ReportService reportService;
    @Value("${reportPrepName}")
    String reportPrepName;
    @Value("${reportSecName}")
    String reportSecName;
    @Autowired
    private DossierRepos dossierRepos;
    @Autowired
    private FraterieRepos fraterieRepos;
    @Autowired
    private InfoParentRepos infoParentRepos;

    @RequestMapping(method = RequestMethod.GET, produces = "application/pdf")
    @ResponseStatus(HttpStatus.OK)
    public void generateReport(@RequestParam String dossierId, HttpServletResponse response) {
        Dossier dossier = dossierRepos.findOne(Long.valueOf(dossierId));
        ReportDTO reportDTO = buildReportDto(dossier);
        try {
            response.addHeader("Content-Disposition", "inline; filename=" + reportDTO.getReportName() + ".pdf");

            BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            byte[] result = reportService.generatePdf(reportDTO);

            outputStream.write(result, 0, result.length);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ReportDTO buildReportDto(Dossier dossier) {
        ReportDTO printableDossier = new ReportDTO("");
        if (dossier.getFiliere() != null && dossier.getFiliere().getId() == 30) {
            printableDossier.setReportName("reportPrepa");
        } else {
            printableDossier.setReportName("reportGeneral");
        }
        printableDossier.getReportParameters().put("dossier", dossier.getReportsAttributes());
        printableDossier.getReportParameters().put("candidat", dossier.getCandidat().getReportsAttributes());
        if (dossier.getNiveauEtude() != null) {
            printableDossier.getReportParameters().put("niveauEtude", dossier.getNiveauEtude().getReportsAttributes());
        }
        if (dossier.getFiliere() != null) {
            printableDossier.getReportParameters().put("filiere", dossier.getFiliere().getReportsAttributes());
        }
        loadInfoParents(dossier, printableDossier);
        loadFraterie(dossier, printableDossier);
        return printableDossier;
    }

    private void loadFraterie(Dossier dossier, ReportDTO printableDossier) {
        List<Fraterie> fraterieList = fraterieRepos.findByCandidatId(dossier.getCandidat().getId());
        if (fraterieList != null && fraterieList.size() > 0) {
            printableDossier.getReportParameters().put("fraterie", fraterieList.get(0).getReportsAttributes());
        }
    }

    private void loadInfoParents(Dossier dossier, ReportDTO printableDossier) {
        List<InfoParent> infoParents = infoParentRepos.findByDossierId(dossier.getId());
        for (InfoParent infoParent : infoParents) {
            switch (infoParent.getParentType()) {
                case PERE:
                    printableDossier.getReportParameters().put("pere", infoParent.getReportsAttributes());
                    break;
                case MERE:
                    printableDossier.getReportParameters().put("mere", infoParent.getReportsAttributes());
                    break;
                case TUTEUR:
                    printableDossier.getReportParameters().put("tuteur", infoParent.getReportsAttributes());
                    break;
            }
        }
    }
}
