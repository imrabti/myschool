package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.server.business.core.Filiere;
import com.gsr.myschool.server.business.core.NiveauEtude;
import com.gsr.myschool.server.repos.FiliereRepos;
import com.gsr.myschool.server.repos.NiveauEtudeRepos;
import com.gsr.myschool.server.service.CachedListValueService;
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

    @Override
    @Cacheable("filiere")
    public List<Filiere> findAllFiliere() {
        return filiereRepos.findAll();
    }

    @Override
    @Cacheable("niveauEtude")
    public List<NiveauEtude> findAllNiveauEtude() {
        return niveauEtudeRepos.findAll();
    }
}
