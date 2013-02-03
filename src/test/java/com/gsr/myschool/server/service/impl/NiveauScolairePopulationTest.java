package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.server.business.core.Filiere;
import com.gsr.myschool.server.business.core.NiveauEtude;
import com.gsr.myschool.server.repos.FiliereRepos;
import com.gsr.myschool.server.repos.NiveauEtudeRepos;
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
public class NiveauScolairePopulationTest {
    @Autowired
    private FiliereRepos filiereRepos;
    @Autowired
    private NiveauEtudeRepos niveauEtudeRepos;

    @Test
    public void populateFiliere() {
        Filiere filiere = new Filiere();
        filiere.setNom("Science et vie");
        filiereRepos.save(filiere);

        filiere = new Filiere();
        filiere.setNom("Electronique");
        filiereRepos.save(filiere);

        filiere = new Filiere();
        filiere.setNom("Mathématique appliqué");
        filiereRepos.save(filiere);

        filiere = new Filiere();
        filiere.setNom("Littérature francaise");
        filiereRepos.save(filiere);
    }

    @Test
    public void populateNiveauEtude() {
        Filiere filere1 = filiereRepos.findByNom("Science et vie");
        Filiere filere2 = filiereRepos.findByNom("Electronique");
        Filiere filere3 = filiereRepos.findByNom("Mathématique appliqué");
        Filiere filere4 = filiereRepos.findByNom("Littérature francaise");

        NiveauEtude niveauEtude = new NiveauEtude();
        niveauEtude.setFiliere(filere1);
        niveauEtude.setAnnee(2013);
        niveauEtude.setNom("S&V - 1");
        niveauEtudeRepos.save(niveauEtude);

        niveauEtude = new NiveauEtude();
        niveauEtude.setFiliere(filere1);
        niveauEtude.setAnnee(2013);
        niveauEtude.setNom("S&V - 2");
        niveauEtudeRepos.save(niveauEtude);

        niveauEtude = new NiveauEtude();
        niveauEtude.setFiliere(filere1);
        niveauEtude.setAnnee(2013);
        niveauEtude.setNom("S&V - 3");
        niveauEtudeRepos.save(niveauEtude);

        niveauEtude = new NiveauEtude();
        niveauEtude.setFiliere(filere2);
        niveauEtude.setAnnee(2013);
        niveauEtude.setNom("ELEC - 1");
        niveauEtudeRepos.save(niveauEtude);

        niveauEtude = new NiveauEtude();
        niveauEtude.setFiliere(filere2);
        niveauEtude.setAnnee(2013);
        niveauEtude.setNom("ELEC - 2");
        niveauEtudeRepos.save(niveauEtude);

        niveauEtude = new NiveauEtude();
        niveauEtude.setFiliere(filere2);
        niveauEtude.setAnnee(2013);
        niveauEtude.setNom("ELEC - 3");
        niveauEtudeRepos.save(niveauEtude);

        niveauEtude = new NiveauEtude();
        niveauEtude.setFiliere(filere3);
        niveauEtude.setAnnee(2013);
        niveauEtude.setNom("MA - 1");
        niveauEtudeRepos.save(niveauEtude);

        niveauEtude = new NiveauEtude();
        niveauEtude.setFiliere(filere3);
        niveauEtude.setAnnee(2013);
        niveauEtude.setNom("MA - 2");
        niveauEtudeRepos.save(niveauEtude);

        niveauEtude = new NiveauEtude();
        niveauEtude.setFiliere(filere3);
        niveauEtude.setAnnee(2013);
        niveauEtude.setNom("MA - 3");
        niveauEtudeRepos.save(niveauEtude);

        niveauEtude = new NiveauEtude();
        niveauEtude.setFiliere(filere4);
        niveauEtude.setAnnee(2013);
        niveauEtude.setNom("LF - 1");
        niveauEtudeRepos.save(niveauEtude);

        niveauEtude = new NiveauEtude();
        niveauEtude.setFiliere(filere4);
        niveauEtude.setAnnee(2013);
        niveauEtude.setNom("LF - 2");
        niveauEtudeRepos.save(niveauEtude);

        niveauEtude = new NiveauEtude();
        niveauEtude.setFiliere(filere4);
        niveauEtude.setAnnee(2013);
        niveauEtude.setNom("LF - 3");
        niveauEtudeRepos.save(niveauEtude);
    }
}
