package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.server.business.Inscription;
import com.gsr.myschool.server.business.core.Filiere;
import com.gsr.myschool.server.business.core.NiveauEtude;
import com.gsr.myschool.server.repos.FiliereRepos;
import com.gsr.myschool.server.repos.NiveauEtudeRepos;
import com.gsr.myschool.server.service.InscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class InscriptionServiceImpl implements InscriptionService {
    @Autowired
    private FiliereRepos filiereRepos;
    @Autowired
    private NiveauEtudeRepos niveauEtudeRepos;

    @Override
    @Transactional(readOnly = true)
    public List<Inscription> findAllInscriptionsByUser(Long userId) {
        List<Inscription> inscriptions = new ArrayList<Inscription>();

        Inscription inscription = new Inscription();
        inscription.setCreated(new Date());
        inscription.setId(1l);
        inscription.setStatus(DossierStatus.CREATED);
        inscriptions.add(inscription);

        inscription = new Inscription();
        inscription.setCreated(new Date());
        inscription.setId(2l);
        inscription.setStatus(DossierStatus.SUBMITED);
        inscriptions.add(inscription);

        inscription = new Inscription();
        inscription.setCreated(new Date());
        inscription.setId(3l);
        inscription.setStatus(DossierStatus.RECEIVED);
        inscriptions.add(inscription);

        inscription = new Inscription();
        inscription.setCreated(new Date());
        inscription.setId(4l);
        inscription.setStatus(DossierStatus.INVITED_TO_TEST);
        inscriptions.add(inscription);

        return inscriptions;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Filiere> findAllFiliere() {
        return filiereRepos.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NiveauEtude> findAllNiveauEtude() {
        return niveauEtudeRepos.findAll();
    }
}
