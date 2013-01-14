package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.common.shared.dto.EmailDTO;
import com.gsr.myschool.common.shared.type.EmailType;
import com.gsr.myschool.server.business.EmailTemplate;
import com.gsr.myschool.server.repos.EmailTemplateRepos;
import com.gsr.myschool.server.service.EmailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/applicationContext.xml",
        "classpath*:/META-INF/applicationContext-security.xml"})
public class EmailServiceImplTest {
    @Autowired
    private EmailService emailService;
    @Autowired
    private EmailTemplateRepos emailTemplateRepos;

    @Before
    public void populatedb() {
        EmailTemplate email = new EmailTemplate();
        email.setId(1L);
        email.setCode(EmailType.ACTIVATION);
        email.setMessage("hello $key1, how is your $key2 and $key");
        email.setSubject("this a subject");

        emailTemplateRepos.save(email);
    }

    @Test
    public void testPopulateEmail() throws Exception {

        // create variables needed
        String to = "kecha.mohamed@gmail.com";
        String from = "test@gmail.com";
        String cc = "";
        String bcc = "";
        Map<String, String> params = new HashMap<String, String>();
        params.put("key1", "valeur1");
        params.put("key2", "valeur2");
        params.put("key3", "valeur3");

        // populate the mail template and return a mailDTO
        EmailDTO result = emailService.populateEmail(EmailType.ACTIVATION, to, from, params, cc, bcc);

        // show result
        System.out.println("==");
        System.out.println("to : " + result.getTo());
        System.out.println("from : " + result.getFrom());
        System.out.println("subject : " + result.getSubject());
        System.out.println("message : " + result.getMessage());
        System.out.println("==");
    }
}
