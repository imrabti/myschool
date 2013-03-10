package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.server.business.EtablissementScolaire;
import com.gsr.myschool.server.business.core.Filiere;
import com.gsr.myschool.server.business.core.NiveauEtude;
import com.gsr.myschool.server.business.valuelist.ValueList;
import com.gsr.myschool.server.repos.DossierRepos;
import com.gsr.myschool.server.repos.EtablissementScolaireRepos;
import com.gsr.myschool.server.repos.FiliereRepos;
import com.gsr.myschool.server.repos.NiveauEtudeRepos;
import com.gsr.myschool.server.service.CachedListValueService;
import com.gsr.myschool.server.service.ValueListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CachedListValueServiceServiceImpl implements CachedListValueService {
    @Autowired
    private FiliereRepos filiereRepos;
    @Autowired
    private NiveauEtudeRepos niveauEtudeRepos;
    @Autowired
    private EtablissementScolaireRepos etablissementScolaireRepos;
    @Autowired
    private ValueListService valueListService;
    @Autowired
    private DossierRepos dossierRepos;

    @Override
    @Cacheable("filiere")
    public List<Filiere> findAllFiliere() {
        return filiereRepos.findAll();
    }

    @Override
    @Cacheable("etablissementScolaire")
    public List<EtablissementScolaire> findAllEtablissementScolaire() {
        List<EtablissementScolaire> result = etablissementScolaireRepos.findAll();
        EtablissementScolaire autres = new EtablissementScolaire();
        autres.setNom("- Autres -");
        result.add(autres);
        return result;
    }

    @Override
    @Cacheable("niveauEtude")
    public List<NiveauEtude> findAllNiveauEtude() {
        return  niveauEtudeRepos.findAll(new Sort(new Order("annee")));
    }

    @Override
    @Cacheable("valueList")
    public List<ValueList> findAllValueList() {
        return valueListService.findAll();
    }

    @Override
    public List<String> findAllNumDossier() {
        return dossierRepos.findAllNumDossier();
    }
}
