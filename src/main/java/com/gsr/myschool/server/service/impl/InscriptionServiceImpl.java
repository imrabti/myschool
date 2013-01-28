package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.server.business.Candidat;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.business.InfoParent;
import com.gsr.myschool.server.business.User;
import com.gsr.myschool.server.repos.CandidatRepos;
import com.gsr.myschool.server.repos.DossierRepos;
import com.gsr.myschool.server.repos.InfoParentRepos;
import com.gsr.myschool.server.security.SecurityContextProvider;
import com.gsr.myschool.server.service.InscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class InscriptionServiceImpl implements InscriptionService {
    @Autowired
    private SecurityContextProvider securityContextProvider;
    @Autowired
    private DossierRepos dossierRepos;
    @Autowired
    private InfoParentRepos infoParentRepos;
    @Autowired
    private CandidatRepos candidatRepos;

    @Override
    @Transactional(readOnly = true)
    public List<Dossier> findAllDossiers() {
        Long id = securityContextProvider.getCurrentUser().getId();
        return dossierRepos.findByOwnerId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Dossier findDossierById(Long id) {
        return dossierRepos.findOne(id);
    }

    @Override
    public Dossier createNewInscription() {
        Candidat candidat = new Candidat();
        candidatRepos.save(candidat);

        InfoParent infoParent = new InfoParent();
        infoParentRepos.save(infoParent);

        User user = securityContextProvider.getCurrentUser();
        Dossier dossier = new Dossier();
        dossier.setStatus(DossierStatus.CREATED);
        dossier.setOwner(user);
        dossier.setInfoParent(infoParent);
        dossier.setCandidat(candidat);
        dossier.setCreateDate(new Date());
        dossierRepos.save(dossier);

        return dossier;
    }
}
