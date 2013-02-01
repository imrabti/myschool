package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.server.business.EtablissementScolaire;
import com.gsr.myschool.server.business.core.Filiere;
import com.gsr.myschool.server.business.core.NiveauEtude;
import com.gsr.myschool.server.business.valuelist.ValueList;
import com.gsr.myschool.server.repos.EtablissementScolaireRepos;
import com.gsr.myschool.server.repos.FiliereRepos;
import com.gsr.myschool.server.repos.NiveauEtudeRepos;
import com.gsr.myschool.server.service.CachedListValueService;
import com.gsr.myschool.server.service.ValueListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @Override
    @Cacheable("filiere")
    public List<Filiere> findAllFiliere() {
        return filiereRepos.findAll();
    }

    @Override
    @Cacheable("etablissementScolaire")
    public List<EtablissementScolaire> findAllEtablissementScolaire() {
        return etablissementScolaireRepos.findAll();
    }

    @Override
    @Cacheable("niveauEtude")
    public List<NiveauEtude> findAllNiveauEtude() {
        return niveauEtudeRepos.findAll();
    }

    @Override
    @Cacheable("valueList")
    public List<ValueList> findAllValueList() {
        return valueListService.findAll();
    }
}
