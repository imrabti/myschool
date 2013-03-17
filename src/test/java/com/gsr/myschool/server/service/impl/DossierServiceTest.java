package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.common.shared.dto.DossierFilterDTO;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.repos.DossierRepos;
import com.gsr.myschool.server.repos.spec.DossierSpec;
import com.gsr.myschool.server.service.DossierService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:META-INF/applicationContext.xml",
        "classpath*:/META-INF/applicationContext-security.xml",
        "classpath*:/META-INF/applicationContext-activiti.xml"
})
@ActiveProfiles("default")
@TransactionConfiguration(defaultRollback = false)
public class DossierServiceTest {
    @Autowired
    private DossierService dossierService;
    @Autowired
    private DossierRepos dossierRepos;

    @Test
    public void testLoadDossierBySpec() {
        DossierFilterDTO dossierFilter = new DossierFilterDTO();
        dossierFilter.setNumDossier("FH");
        dossierFilter.setStatus(DossierStatus.SUBMITTED);
        List<Dossier> dossiers = dossierService.findAllDossiersByCriteria(dossierFilter, null, null).getDossiers();
        Assert.assertEquals(dossiers.size(), 1);
    }
}
