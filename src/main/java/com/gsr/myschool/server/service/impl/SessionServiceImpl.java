package com.gsr.myschool.server.service.impl;

import com.google.common.base.Strings;
import com.gsr.myschool.common.shared.exception.AffectationClosedException;
import com.gsr.myschool.common.shared.exception.SessionEmptyException;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.common.shared.type.SessionStatus;
import com.gsr.myschool.common.shared.type.ValueTypeCode;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.business.DossierSession;
import com.gsr.myschool.server.business.core.MatiereExamDuNE;
import com.gsr.myschool.server.business.core.NiveauEtude;
import com.gsr.myschool.server.business.core.SessionExamen;
import com.gsr.myschool.server.business.core.SessionNiveauEtude;
import com.gsr.myschool.server.business.valuelist.ValueList;
import com.gsr.myschool.server.repos.DossierRepos;
import com.gsr.myschool.server.repos.DossierSessionRepos;
import com.gsr.myschool.server.repos.MatiereExamenNERepos;
import com.gsr.myschool.server.repos.NiveauEtudeRepos;
import com.gsr.myschool.server.repos.SessionExamenNERepos;
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

import java.util.*;

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
    @Autowired
    private NiveauEtudeRepos niveauEtudeRepos;
    @Autowired
    private MatiereExamenNERepos matiereExamenNERepos;
    @Autowired
    private SessionExamenNERepos sessionExamenNERepos;

    @Override
    public void createNewSession(SessionExamen sessionExamen) {
        String currentAnneeScolaire = DateUtils.currentYear() + "-" + (DateUtils.currentYear() + 1);
        sessionExamen.setAnneeScolaire(valueListRepos.findByValueAndValueTypeCode(currentAnneeScolaire,
                ValueTypeCode.SCHOOL_YEAR));
        sessionExamen.setStatus(SessionStatus.CREATED);
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
        SessionExamen session = sessionExamenRepos.findOne(sessionId);
        NiveauEtude niveauEtude = niveauEtudeRepos.findOne(niveauEtudeId);

        List<MatiereExamDuNE> matieres = matiereExamenNERepos.findByNiveauEtudeId(niveauEtudeId);
        for (MatiereExamDuNE item : matieres) {
            SessionNiveauEtude attachedMatiere = new SessionNiveauEtude();
            attachedMatiere.setNiveauEtude(niveauEtude);
            attachedMatiere.setSessionExamen(session);
            attachedMatiere.setMatiere(item.getMatiereExamen().getNom());
            sessionExamenNERepos.save(attachedMatiere);
        }
    }

    @Override
    public void updateHoraire(List<String> horaires) {
        for (String item : horaires) {
            SessionNiveauEtude matiere = sessionExamenNERepos.findOne(Long.parseLong(item.split("#")[0]));
            matiere.setHoraireDe(item.split("#")[1]);
            matiere.setHoraireA(item.split("#")[2]);
            sessionExamenNERepos.save(matiere);
        }
    }

    @Override
    public void deleteNiveauEtude(Long niveauEtudeId) {
        List<SessionNiveauEtude> matieres = sessionExamenNERepos.findByNiveauEtudeId(niveauEtudeId);
        sessionExamenNERepos.delete(matieres);
    }

    @Override
    public Boolean openSession(Long sessionId) throws SessionEmptyException {
        List<SessionNiveauEtude> matieres = sessionExamenNERepos.findBySessionExamenId(sessionId);

        if (matieres.isEmpty()) {
            throw new SessionEmptyException();
        }

        for (SessionNiveauEtude item : matieres) {
            if (Strings.isNullOrEmpty(item.getHoraireA())|| Strings.isNullOrEmpty(item.getHoraireDe())) {
                return false;
            }
        }

        SessionExamen session = sessionExamenRepos.findOne(sessionId);
        session.setStatus(SessionStatus.OPEN);
        sessionExamenRepos.save(session);

        return true;
    }

    @Override
    public void cancelOrDeleteSession(Long sessionId) {
        SessionExamen session = sessionExamenRepos.findOne(sessionId);
        if (session.getStatus() == SessionStatus.CREATED) {
            List<SessionNiveauEtude> matieres = sessionExamenNERepos.findBySessionExamenId(sessionId);
            sessionExamenNERepos.delete(matieres);
            sessionExamenRepos.delete(session);
        } else if (session.getStatus() == SessionStatus.OPEN) {
            // TODO : Remove affectation and set to Cancel.
        } else if (session.getStatus() == SessionStatus.CLOSED) {
            // TODO : Remove affectation send Email and set to Cancel.
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SessionNiveauEtude> findAllMatieresByNiveauEtude(Long sessionId, Long niveauEtudeId) {
        return sessionExamenNERepos.findBySessionExamenIdAndNiveauEtudeId(sessionId, niveauEtudeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SessionNiveauEtude> findAllNiveauEtudeBySession(Long sessionId) {
        return sessionExamenNERepos.findBySessionExamenId(sessionId);
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
    public Boolean affecter(Dossier dossier, SessionExamen session) throws AffectationClosedException {
        DossierSession sameexist = dossierSessionRepos.findByDossierIdAndSessionExamenId(dossier.getId(), session.getId());
        if (sameexist != null) {
            return false;
        }

        DossierSession exist = dossierSessionRepos.findByDossierId(dossier.getId());
        if (exist != null && exist.getSessionExamen().getStatus() == SessionStatus.CLOSED) {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DAY_OF_MONTH, -1);

            Date dateSession = exist.getSessionExamen().getDateSession();
            Date yesterday = calendar.getTime();

            if (dateSession.before(yesterday)) {
                throw new AffectationClosedException();
            }
        }
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
    public Boolean desaffecter(Dossier dossier) throws AffectationClosedException {
        DossierSession exist = dossierSessionRepos.findByDossierId(dossier.getId());
        if (exist == null || exist.getSessionExamen() == null || exist.getDossier() == null) {
            return false;
        }
        if (exist.getSessionExamen().getStatus() == SessionStatus.CLOSED) {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DAY_OF_MONTH, -1);

            Date dateSession = exist.getSessionExamen().getDateSession();
            Date yesterday = calendar.getTime();

            if (dateSession.before(yesterday)) {
                throw new AffectationClosedException();
            }
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
