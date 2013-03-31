package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.common.shared.dto.EmailDTO;
import com.gsr.myschool.common.shared.type.EmailType;
import com.gsr.myschool.server.business.EmailTemplate;
import com.gsr.myschool.server.reporting.ConvocationController;
import com.gsr.myschool.server.repos.EmailTemplateRepos;
import com.gsr.myschool.server.service.EmailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:META-INF/applicationContext.xml",
        "classpath*:/META-INF/applicationContext-activiti.xml",
        "classpath*:/META-INF/applicationContext-security.xml"
})
//@TransactionConfiguration(defaultRollback = true)
//@Transactional
//@ActiveProfiles("test")
public class EmailServiceImplTest {
    @Autowired
    private EmailService emailService;
    @Autowired
    private EmailTemplateRepos emailTemplateRepos;
    @Autowired
    private ConvocationController convocationController;
    EmailTemplate email = new EmailTemplate();

    @Before
    public void populatedb() {
        email.setId(1L);
        email.setCode(EmailType.ACTIVATION);
        email.setMessage("hello $key1, how is your $key2 and $key and #foreach ($test in $testlist) <li>$test</li> #end");
        email.setSubject("this a subject");
    }

    @Test
    public void testPopulateEmail() throws Exception {

        // create variables needed
        String to = "kecha.mohamed@gmail.com";
        String from = "test@gmail.com";
        String cc = "";
        String bcc = "";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("key1", "valeur1");
        params.put("key2", "valeur2");
        params.put("key3", "valeur3");
        List<String> testlist = new ArrayList<String>();
        testlist.add("test1");
        testlist.add("test2");
        testlist.add("test3");

        params.put("testlist",testlist);

        // populate the mail template and return a mailDTO
        EmailDTO result = emailService.populateEmail(EmailType.ACTIVATION, to, from, params, cc, bcc);

        // show result
        System.out.println("==");
        System.out.println("to : " + result.getTo());
        System.out.println("from : " + result.getFrom());
        System.out.println("subject : " + result.getSubject());
        System.out.println("message : " + result.getMessage());
        System.out.println("==");


        File f = convocationController.generateConvocation(902L);
        System.out.println("==");
        emailService.sendWithAttachement(result, f);
        System.out.println("==");
        sleep(10000);
    }
}
