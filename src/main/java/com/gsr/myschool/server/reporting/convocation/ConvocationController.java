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

import com.gsr.myschool.common.shared.dto.ReportDTO;
import com.gsr.myschool.server.reporting.ReportService;
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
@RequestMapping("/convocation")
public class ConvocationController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private ConvocationService convocationService;

    @RequestMapping(value = "test", method = RequestMethod.GET, produces = "application/pdf")
    @ResponseStatus(HttpStatus.OK)
    public void generateReportTest(@RequestParam String niveauId, @RequestParam String sessionId, HttpServletResponse response) {
        ReportDTO dto = convocationService.generateConvocationTest(Long.valueOf(niveauId), Long.valueOf(sessionId));
        try {
            response.addHeader("Content-Disposition", "attachment; filename=convocation.pdf");

            BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            byte[] result = reportService.generatePdf(dto);

            outputStream.write(result, 0, result.length);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/pdf")
    @ResponseStatus(HttpStatus.OK)
    public void generateConvocation(@RequestParam String number, HttpServletResponse response) {
        ReportDTO dto = convocationService.generateConvocation(number);
        try {
            response.addHeader("Content-Disposition", "attachment; filename=convocation.pdf");

            BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            byte[] result = reportService.generatePdf(dto);

            outputStream.write(result, 0, result.length);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
