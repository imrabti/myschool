package com.gsr.myschool.server.service.impl;


import com.gsr.myschool.shared.dto.TestXlsDTO;
import com.gsr.myschool.server.service.XlsExportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/applicationContext.xml",
        "classpath*:/META-INF/applicationContext-security.xml"})
public class XlsExportServiceImplTest {
    @Autowired
    private XlsExportService xlsExportService;

    @Test
    public void testexport() {
        List<TestXlsDTO> liste = new ArrayList<TestXlsDTO>();

        TestXlsDTO a = new TestXlsDTO();
        a.setDate(new Date());
        a.setFname("fname 1");
        a.setLname("title test");

        TestXlsDTO b = new TestXlsDTO();
        b.setDate(new Date());
        b.setFname("fname b");
        b.setLname("title test b");

        liste.add(a);
        liste.add(b);
        liste.add(b);
        liste.add(a);
        liste.add(b);

        xlsExportService.saveSpreadsheetRecords(TestXlsDTO.class, liste, System.out);
//        File fs = new File("./test.xls");
//        OutputStream f = new FileOutputStream(fs);
//        spreadSheetImpl.saveSpreadsheetRecords(Account.class, accounts, f);
//        f.close();
    }
}
