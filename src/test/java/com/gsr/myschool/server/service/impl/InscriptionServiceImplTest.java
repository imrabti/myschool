package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.server.service.InscriptionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:META-INF/applicationContext.xml",
        "classpath*:/META-INF/applicationContext-security.xml",
        "classpath*:/META-INF/applicationContext-activiti.xml"
})
@ActiveProfiles("test")
public class InscriptionServiceImplTest {
    @Autowired
    private InscriptionService inscriptionService;

    @Test
    public void testCreateNewInscription() {
        inscriptionService.createNewInscription();
    }
}
