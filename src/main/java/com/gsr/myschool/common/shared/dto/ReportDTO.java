package com.gsr.myschool.common.shared.dto;

import java.util.Map;

public class ReportDTO {
     private String reportName;

     private Map<String, Object> reportParameters;

     public String getReportName() {
          return reportName;
     }

     public void setReportName(String reportName) {
          this.reportName = reportName;
     }

     public Map<String, Object> getReportParameters() {
          return reportParameters;
     }

     public void setReportParameters(Map<String, Object> reportParameters) {
          this.reportParameters = reportParameters;
     }
}