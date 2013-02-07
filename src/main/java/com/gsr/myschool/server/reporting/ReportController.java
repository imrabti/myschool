package com.gsr.myschool.server.reporting;

import com.gsr.myschool.common.shared.dto.ReportDTO;
import com.gsr.myschool.common.shared.type.ParentType;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.repos.DossierRepos;
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

@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ReportService reportService;
    @Value("${reportTemplatePattern}") String jasperTmp;
    @Autowired
    private DossierRepos dossierRepos;

    @RequestMapping(method = RequestMethod.GET, produces = "application/pdf")
    @ResponseStatus(HttpStatus.OK)
    public void generateReport(@RequestParam String dossierId, HttpServletResponse response) {
        Dossier dossier = dossierRepos.findOne(Long.valueOf(dossierId));
        ReportDTO reportDTO = buildReportDto(dossier);
        try {
            response.addHeader("Content-Disposition", "inline; filename="+reportDTO.getReportName()+".pdf");

            BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            byte[] result =  reportService.generatePdf(reportDTO);

            outputStream.write(result, 0, result.length);
            outputStream.flush();
            outputStream.close();
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    private ReportDTO buildReportDto(Dossier dossier) {
        ReportDTO printableDossier = new ReportDTO("report2");
        printableDossier.getReportParameters().put("dossier", dossier.getReportsAttributes());
        if (dossier.getInfoParent().getParentType().equals(ParentType.PERE)) {
            printableDossier.getReportParameters().put("pere", dossier.getInfoParent().getReportsAttributes());
        }
        if (dossier.getInfoParent().getParentType().equals(ParentType.MERE)) {
            printableDossier.getReportParameters().put("mere", dossier.getInfoParent().getReportsAttributes());
        }
        if (dossier.getInfoParent().getParentType().equals(ParentType.TUTEUR)) {
            printableDossier.getReportParameters().put("tuteur", dossier.getInfoParent().getReportsAttributes());
        }
        printableDossier.getReportParameters().put("candidat", dossier.getCandidat().getReportsAttributes());
        printableDossier.getReportParameters().put("niveauEtude", dossier.getNiveauEtude().getReportsAttributes());
        printableDossier.getReportParameters().put("filiere", dossier.getFiliere().getReportsAttributes());

        return printableDossier;
    }
}

