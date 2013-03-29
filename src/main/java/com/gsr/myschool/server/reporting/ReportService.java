package com.gsr.myschool.server.reporting;

import com.gsr.myschool.common.shared.dto.ReportDTO;

public interface ReportService {
     byte[] generatePdf(ReportDTO reportDTO) throws Exception;

    void generatePdfIntoFolder(ReportDTO reportDTO, String filename) throws Exception;
}
