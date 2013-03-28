package com.gsr.myschool.server.reporting;

import com.gsr.myschool.common.shared.dto.ReportDTO;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService, ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    @Override
    public byte[] generatePdf(ReportDTO reportDTO) throws Exception {
        Resource resource = getResource("classpath:/META-INF/templates/" +
                reportDTO.getReportName() + ".jasper");
        reportDTO.getReportParameters().put("SUBREPORT_DIR", resource.getFile().getParent()+"/");

        JREmptyDataSource emptyDataSource = new JREmptyDataSource();
        JasperPrint jasperPrint = JasperFillManager.fillReport(resource.getInputStream(),
                reportDTO.getReportParameters(), emptyDataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    @Override
    public void generatePdfIntoFolder(ReportDTO reportDTO, String filename) throws Exception {
        Resource resource = getResource("classpath:/META-INF/templates/" +
                reportDTO.getReportName() + ".jasper");
        reportDTO.getReportParameters().put("SUBREPORT_DIR", resource.getFile().getParent()+"/");

        JREmptyDataSource emptyDataSource = new JREmptyDataSource();
        JasperPrint jasperPrint = JasperFillManager.fillReport(resource.getInputStream(),
                reportDTO.getReportParameters(), emptyDataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint, "D:\\PDFGSR\\"+filename+"_v2.pdf");
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Resource getResource(String location){
        return resourceLoader.getResource(location);
    }
}

