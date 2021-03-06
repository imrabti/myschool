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
        List<DossierStatus> list = new ArrayList<DossierStatus>();
        list.add(DossierStatus.CREATED);
        list.add(DossierStatus.ACCEPTED_FOR_TEST);
        list.add(DossierStatus.INVITED_TO_TEST);

        dossierFilter.setStatusList(list);

        List<Dossier> result = dossierRepos.findAll(DossierSpec.statusIn(list));

        Integer total = dossierService.findAllDossiersByCriteria(dossierFilter, null, null).getDossiers().size();
        Integer test = total;
    }
}
