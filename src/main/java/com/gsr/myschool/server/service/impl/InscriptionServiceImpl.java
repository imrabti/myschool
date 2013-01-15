package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.common.shared.type.InscriptionStatusType;
import com.gsr.myschool.server.business.Inscription;
import com.gsr.myschool.server.service.InscriptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class InscriptionServiceImpl implements InscriptionService {
    @Override
    @Transactional(readOnly = true)
    public List<Inscription> findAllInscriptionsByUser(Long userId) {
        List<Inscription> inscriptions = new ArrayList<Inscription>();

        Inscription inscription = new Inscription();
        inscription.setCreated(new Date());
        inscription.setId(1l);
        inscription.setStatus(InscriptionStatusType.CREATED);
        inscriptions.add(inscription);

        inscription = new Inscription();
        inscription.setCreated(new Date());
        inscription.setId(2l);
        inscription.setStatus(InscriptionStatusType.PROCESSED);
        inscriptions.add(inscription);

        inscription = new Inscription();
        inscription.setCreated(new Date());
        inscription.setId(3l);
        inscription.setStatus(InscriptionStatusType.WAITING);
        inscriptions.add(inscription);

        inscription = new Inscription();
        inscription.setCreated(new Date());
        inscription.setId(4l);
        inscription.setStatus(InscriptionStatusType.WAITING);
        inscriptions.add(inscription);

        return null;
    }
}
