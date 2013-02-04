package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.server.business.EtablissementScolaire;
import com.gsr.myschool.server.repos.EtablissementScolaireRepos;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:META-INF/applicationContext.xml",
        "classpath*:/META-INF/applicationContext-security.xml",
        "classpath*:/META-INF/applicationContext-activiti.xml"
})
@ActiveProfiles("default")
@TransactionConfiguration(defaultRollback = false)
public class EtablissementScolairePopulationTest {
    @Autowired
    private EtablissementScolaireRepos etablissementScolaireRepos;

    @Test
    public void populateValueType() {
        EtablissementScolaire etablissementScolaire = new EtablissementScolaire();
        etablissementScolaire.setNom("Al Jahid");
        etablissementScolaire.setAdresse("21 Cite Ibn Rochd Casablanca");
        etablissementScolaire.setReference(true);
        etablissementScolaireRepos.save(etablissementScolaire);

        etablissementScolaire = new EtablissementScolaire();
        etablissementScolaire.setNom("CTM des mines");
        etablissementScolaire.setAdresse("Hay Nakhil 3445 Rabat");
        etablissementScolaire.setReference(true);
        etablissementScolaireRepos.save(etablissementScolaire);

        etablissementScolaire = new EtablissementScolaire();
        etablissementScolaire.setNom("Des orangers");
        etablissementScolaire.setAdresse("31 Bd Zerktouni Casablanca");
        etablissementScolaire.setReference(true);
        etablissementScolaireRepos.save(etablissementScolaire);

        etablissementScolaire = new EtablissementScolaire();
        etablissementScolaire.setNom("Ibn Sina");
        etablissementScolaire.setAdresse("23 Bd 2 MarsCasablanca");
        etablissementScolaire.setReference(true);
        etablissementScolaireRepos.save(etablissementScolaire);
    }
}
