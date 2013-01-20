package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.service.DossierService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DossierServiceImpl implements DossierService {
    @Override
    @Transactional(readOnly = true)
    public List<Dossier> findAllDossiersByUser(Long userId) {
        List<Dossier> Dossiers = new ArrayList<Dossier>();

        Dossier dossier = new Dossier();
        dossier.setGeneratedNumDossier("001S2013");
        dossier.setCreated(new Date());
        dossier.setSubmitted(new Date());
        dossier.setId(1l);
        dossier.setStatus(DossierStatus.STANDBY);
        Dossiers.add(dossier);

        dossier = new Dossier();
        dossier.setGeneratedNumDossier("002S2013");
        dossier.setSubmitted(new Date());
        dossier.setCreated(new Date());
        dossier.setId(2l);
        dossier.setStatus(DossierStatus.RECEIVED);
        Dossiers.add(dossier);

        dossier = new Dossier();
        dossier.setGeneratedNumDossier("003S2013");
        dossier.setSubmitted(new Date());
        dossier.setCreated(new Date());
        dossier.setId(3l);
        dossier.setStatus(DossierStatus.ACCEPTED_FOR_STUDY);
        Dossiers.add(dossier);

        return Dossiers;
    }
}
