package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.common.shared.type.SessionStatus;
import com.gsr.myschool.common.shared.type.ValueTypeCode;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.business.DossierSession;
import com.gsr.myschool.server.business.core.NiveauEtude;
import com.gsr.myschool.server.business.core.SessionExamen;
import com.gsr.myschool.server.business.valuelist.ValueList;
import com.gsr.myschool.server.repos.DossierRepos;
import com.gsr.myschool.server.repos.DossierSessionRepos;
import com.gsr.myschool.server.repos.SessionExamenRepos;
import com.gsr.myschool.server.repos.ValueListRepos;
import com.gsr.myschool.server.security.SecurityContextProvider;
import com.gsr.myschool.server.service.SessionService;
import com.gsr.myschool.server.util.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SessionServiceImpl implements SessionService {
    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private ValueListRepos valueListRepos;
    @Autowired
    private SessionExamenRepos sessionExamenRepos;
    @Autowired
    private DossierSessionRepos dossierSessionRepos;
    @Autowired
    private SecurityContextProvider securityContextProvider;
    @Autowired
    private DossierRepos dossierRepos;

    @Override
    public void createNewSession(SessionExamen sessionExamen) {
        String currentAnneeScolaire = DateUtils.currentYear() + "-" + (DateUtils.currentYear() + 1);
        sessionExamen.setAnneeScolaire(valueListRepos.findByValueAndValueTypeCode(currentAnneeScolaire,
                ValueTypeCode.SCHOOL_YEAR));
        sessionExamen.setStatus(SessionStatus.OPEN);
        sessionExamen.setCandidates(0);
        sessionExamenRepos.save(sessionExamen);
    }

    @Override
    public void updateSession(SessionExamen sessionExamen) {
        SessionExamen oldSession = sessionExamenRepos.findOne(sessionExamen.getId());
        oldSession.setAdresse(sessionExamen.getAdresse());
        oldSession.setDateSession(sessionExamen.getDateSession());
        oldSession.setNom(sessionExamen.getNom());
        oldSession.setLatitude(sessionExamen.getLatitude());
        oldSession.setLongitude(sessionExamen.getLongitude());
        oldSession.setStatus(sessionExamen.getStatus());
        oldSession.setDebutTest(sessionExamen.getDebutTest());
        oldSession.setWelcomKids(sessionExamen.getWelcomKids());
        oldSession.setGatherKids(sessionExamen.getGatherKids());
        oldSession.setTelephone(sessionExamen.getTelephone());
        sessionExamenRepos.save(oldSession);
    }

    @Override
    public void attacheToSession(Long sessionId, Long niveauEtudeId) {
        // TODO load all attached matiere and add them...
    }

    @Override
    @Transactional(readOnly = true)
    public List<SessionExamen> findAllSessions() {
        String currentAnneeScolaire = DateUtils.currentYear() + "-" + (DateUtils.currentYear() + 1);
        ValueList currentAnnee = valueListRepos.findByValueAndValueTypeCode(currentAnneeScolaire,
                ValueTypeCode.SCHOOL_YEAR);

        if (currentAnnee != null) {
            return sessionExamenRepos.findByAnneeScolaireId(currentAnnee.getId());
        } else {
            return new ArrayList<SessionExamen>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SessionExamen> findAllOpenedSessions() {
        String currentAnneeScolaire = DateUtils.currentYear() + "-" + (DateUtils.currentYear() + 1);
        ValueList currentAnnee = valueListRepos.findByValueAndValueTypeCode(currentAnneeScolaire,
                ValueTypeCode.SCHOOL_YEAR);

        if (currentAnnee != null) {
            return sessionExamenRepos.findByAnneeScolaireIdAndStatus(currentAnnee.getId(), SessionStatus.OPEN);
        } else {
            return new ArrayList<SessionExamen>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SessionExamen> findSessionByNE(NiveauEtude niveau) {
        String currentAnneeScolaire = DateUtils.currentYear() + "-" + (DateUtils.currentYear() + 1);
        ValueList currentAnnee = valueListRepos.findByValueAndValueTypeCode(currentAnneeScolaire,
                ValueTypeCode.SCHOOL_YEAR);

        if (currentAnnee != null && niveau != null) {
            return sessionExamenRepos.findByNiveauEtude(currentAnnee.getId(), niveau.getId(), SessionStatus.OPEN);
        } else {
            return new ArrayList<SessionExamen>();
        }
    }

    @Override
    public Boolean affecter(Dossier dossier, SessionExamen session) {
        DossierSession sameexist = dossierSessionRepos.findByDossierIdAndSessionExamenId(dossier.getId(), session.getId());
        if (sameexist != null) {
            return false;
        }

        DossierSession exist = dossierSessionRepos.findByDossierId(dossier.getId());
        if (exist != null) {
            dossierSessionRepos.delete(exist);
        }

        Dossier affectedDossier = dossierRepos.findOne(dossier.getId());
        affectedDossier.setStatus(DossierStatus.INVITED_TO_TEST);

        SessionExamen examen = sessionExamenRepos.findOne(session.getId());
        examen.setCandidates(examen.getCandidates() + 1);

        DossierSession dossierSession = new DossierSession();
        dossierSession.setDateAffectation(new Date());
        dossierSession.setAssignedBy(securityContextProvider.getCurrentAdmin());
        dossierSession.setDossier(affectedDossier);
        dossierSession.setSessionExamen(examen);

        try {
            dossierRepos.save(affectedDossier);
            sessionExamenRepos.save(examen);
            dossierSessionRepos.save(dossierSession);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean desaffecter(Dossier dossier) {
        DossierSession exist = dossierSessionRepos.findByDossierId(dossier.getId());
        if (exist == null || exist.getSessionExamen() == null || exist.getDossier() == null) {
            return false;
        }
        Dossier affectedDossier = dossierRepos.findOne(dossier.getId());
        affectedDossier.setStatus(DossierStatus.ACCEPTED_FOR_TEST);

        SessionExamen examen = sessionExamenRepos.findOne(exist.getSessionExamen().getId());
        examen.setCandidates(examen.getCandidates() - 1);

        try {
            dossierRepos.save(affectedDossier);
            sessionExamenRepos.save(examen);
            dossierSessionRepos.delete(exist);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
