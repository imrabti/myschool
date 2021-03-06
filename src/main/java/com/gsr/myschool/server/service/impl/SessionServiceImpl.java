package com.gsr.myschool.server.service.impl;

import com.google.common.base.Strings;
import com.gsr.myschool.common.client.util.Base64;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.common.shared.dto.EmailDTO;
import com.gsr.myschool.common.shared.exception.AffectationClosedException;
import com.gsr.myschool.common.shared.exception.SessionEmptyException;
import com.gsr.myschool.common.shared.type.*;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.business.DossierHistoric;
import com.gsr.myschool.server.business.DossierSession;
import com.gsr.myschool.server.business.InboxMessage;
import com.gsr.myschool.server.business.core.MatiereExamDuNE;
import com.gsr.myschool.server.business.core.NiveauEtude;
import com.gsr.myschool.server.business.core.SessionExamen;
import com.gsr.myschool.server.business.core.SessionNiveauEtude;
import com.gsr.myschool.server.business.valuelist.ValueList;
import com.gsr.myschool.server.repos.*;
import com.gsr.myschool.server.security.SecurityContextProvider;
import com.gsr.myschool.server.service.EmailPreparatorService;
import com.gsr.myschool.server.service.SessionService;
import com.gsr.myschool.server.util.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
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
    @Autowired
    private EmailPreparatorService emailService;
    @Autowired
    private InboxMessageRepos inboxMessageRepos;
    @Autowired
    private DossierHistoricRepo dossierHistoricRepo;
    @Autowired
    private ValueTypeRepos valueTypeRepos;
    @Value("${mailserver.sender}")
    private String sender;

    @Override
    public void createNewSession(SessionExamen sessionExamen) {
        sessionExamen.setAnneeScolaire(getCurrentScholarYear());
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
    public void deleteNiveauEtude(Long sessionId, Long niveauEtudeId) {
        List<SessionNiveauEtude> matieres = sessionExamenNERepos.findBySessionExamenIdAndNiveauEtudeId(sessionId,
                niveauEtudeId);
        sessionExamenNERepos.delete(matieres);
    }

    @Override
    public Boolean openSession(Long sessionId) throws SessionEmptyException {
        List<SessionNiveauEtude> matieres = sessionExamenNERepos.findBySessionExamenId(sessionId);

        if (matieres.isEmpty()) {
            throw new SessionEmptyException();
        }

        for (SessionNiveauEtude item : matieres) {
            if (Strings.isNullOrEmpty(item.getHoraireA()) || Strings.isNullOrEmpty(item.getHoraireDe())) {
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
        if (session.getStatus() == SessionStatus.CREATED || session.getStatus() == SessionStatus.CANCELED) {
            List<SessionNiveauEtude> matieres = sessionExamenNERepos.findBySessionExamenId(sessionId);
            sessionExamenNERepos.delete(matieres);
            sessionExamenRepos.delete(session);
        } else if (session.getStatus() == SessionStatus.OPEN) {
            List<DossierSession> dossierSessions = dossierSessionRepos.findBySessionExamenId(session.getId());
            for (DossierSession ds : dossierSessions) {
                Dossier dossier = dossierRepos.findOne(ds.getDossier().getId());

                DossierHistoric dossierHistoric = new DossierHistoric();
                dossierHistoric.setStatus(dossier.getStatus());
                dossierHistoric.setCreateDate(new Date());
                dossierHistoric.setDossier(dossier);
                dossierHistoricRepo.save(dossierHistoric);

                dossier.setStatus(DossierStatus.ACCEPTED_FOR_TEST);
                dossierRepos.save(dossier);
            }

            dossierSessionRepos.delete(dossierSessions);

            session.setStatus(SessionStatus.CANCELED);
            sessionExamenRepos.save(session);
        } else if (session.getStatus() == SessionStatus.CLOSED) {
            SimpleDateFormat format = new SimpleDateFormat(GlobalParameters.DATE_FORMAT);
            List<DossierSession> dossierSessions = dossierSessionRepos.findBySessionExamenId(session.getId());
            for (DossierSession dossiersession : dossierSessions) {
                Dossier dossier = dossiersession.getDossier();

                DossierHistoric dossierHistoric = new DossierHistoric();
                dossierHistoric.setStatus(dossier.getStatus());
                dossierHistoric.setCreateDate(new Date());
                dossierHistoric.setDossier(dossier);
                dossierHistoricRepo.save(dossierHistoric);

                dossier.setStatus(DossierStatus.ACCEPTED_FOR_TEST);
                dossierRepos.save(dossier);

                Map<String, Object> params = new HashMap<String, Object>();
                if (dossiersession.getSessionExamen().getDateSession() != null) {
                    params.put("dateSession", format.format(dossiersession.getSessionExamen().getDateSession()));
                } else {
                    params.put("dateSession", "");
                }

                params.put("sessionNom", dossiersession.getSessionExamen().getNom());
                params.put("gender", dossier.getOwner().getGender().toString());
                params.put("lastname", dossier.getOwner().getLastName());
                params.put("firstname", dossier.getOwner().getFirstName());
                params.put("nomEnfant", dossier.getCandidat().getLastname());
                params.put("prenomEnfant", dossier.getCandidat().getFirstname());
                params.put("refdossier", dossier.getGeneratedNumDossier());

                try {
                    EmailDTO email = emailService.populateEmail(EmailType.SESSION_CANCELED,
                            dossier.getOwner().getEmail(), sender,
                            params, "", "");
                    emailService.prepare(email);

                    InboxMessage message = new InboxMessage();
                    message.setParentUser(dossier.getOwner());
                    message.setSubject(email.getSubject());
                    message.setContent(email.getMessage());
                    message.setMsgDate(new Date());
                    message.setMsgStatus(InboxMessageStatus.UNREAD);
                    inboxMessageRepos.save(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            dossierSessionRepos.delete(dossierSessions);

            session.setStatus(SessionStatus.CANCELED);
            sessionExamenRepos.save(session);
        }
    }

    @Override
    public void copySession(Long sessionId) {
        SessionExamen toCopy = sessionExamenRepos.findOne(sessionId);
        SessionExamen copied = new SessionExamen();
        copied.setStatus(SessionStatus.CREATED);
        copied.setAdresse(toCopy.getAdresse());
        copied.setAnneeScolaire(toCopy.getAnneeScolaire());
        copied.setCandidates(0);
        copied.setDateSession(toCopy.getDateSession());
        copied.setDebutTest(toCopy.getDebutTest());
        copied.setGatherKids(toCopy.getGatherKids());
        copied.setLatitude(toCopy.getLatitude());
        copied.setLongitude(toCopy.getLongitude());
        copied.setNom(toCopy.getNom() + " (copy)");
        copied.setTelephone(toCopy.getTelephone());
        copied.setWelcomKids(toCopy.getWelcomKids());
        sessionExamenRepos.save(copied);

        List<SessionNiveauEtude> dataToCopy = sessionExamenNERepos.findBySessionExamenId(sessionId);
        for (SessionNiveauEtude itemToCopy : dataToCopy) {
            SessionNiveauEtude newData = new SessionNiveauEtude();
            newData.setHoraireA(itemToCopy.getHoraireA());
            newData.setHoraireDe(itemToCopy.getHoraireDe());
            newData.setMatiere(itemToCopy.getMatiere());
            newData.setNiveauEtude(itemToCopy.getNiveauEtude());
            newData.setSessionExamen(copied);
            sessionExamenNERepos.save(newData);
        }
    }

    @Override
    public DossierSession findByDossier(Dossier dossier) {
        try {
            return dossierSessionRepos.findByDossierId(dossier.getId());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean launchSession(SessionExamen session, String link) {
        List<DossierSession> dossierSessions = dossierSessionRepos.findBySessionExamenId(session.getId());
        for (DossierSession dossiersession : dossierSessions) {
            Dossier dossier = dossierRepos.findOne(dossiersession.getDossier().getId());
            String token = Base64.encode(dossiersession.getDossier().getGeneratedNumDossier() + "" + (new Date()).toString());
            token = token.replace("=", "E");
            dossiersession.setGeneratedConvocationPDFPath(token);

            DossierHistoric dossierHistoric = new DossierHistoric();
            dossierHistoric.setStatus(dossier.getStatus());
            dossierHistoric.setCreateDate(new Date());
            dossierHistoric.setDossier(dossier);
            dossierHistoricRepo.save(dossierHistoric);

            dossier.setStatus(DossierStatus.INVITED_TO_TEST);

            dossierRepos.save(dossier);
            dossierSessionRepos.save(dossiersession);

            if (dossier.getNiveauEtude().getEmailConvocation() == null || !dossier.getNiveauEtude().getEmailConvocation())
                continue;

            // preparing the email data
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("gender", dossier.getOwner().getGender().toString());
            params.put("lastname", dossier.getOwner().getLastName());
            params.put("firstname", dossier.getOwner().getFirstName());
            params.put("nomEnfant", dossier.getCandidat().getLastname());
            params.put("prenomEnfant", dossier.getCandidat().getFirstname());
            params.put("refdossier", dossier.getGeneratedNumDossier());
            params.put("link", link + "resource/convocation?number=" + token);

            try {
                EmailDTO email = emailService.populateEmail(EmailType.CONVOCATED_FOR_TEST,
                        dossier.getOwner().getEmail(), sender,
                        params, "", "");
                emailService.prepare(email);

                InboxMessage message = new InboxMessage();
                message.setParentUser(dossier.getOwner());
                message.setSubject(email.getSubject());
                message.setContent(email.getMessage());
                message.setMsgDate(new Date());
                message.setMsgStatus(InboxMessageStatus.UNREAD);
                inboxMessageRepos.save(message);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        session = sessionExamenRepos.findOne(session.getId());
        session.setStatus(SessionStatus.CLOSED);
        sessionExamenRepos.save(session);
        return true;
    }

    @Override
    public void sendEmailConvocation(DossierSession session, String link) {
        Dossier dossier = dossierRepos.findOne(session.getDossier().getId());
        DossierSession dossierSession = dossierSessionRepos.findOne(session.getId());

        if (Strings.isNullOrEmpty(dossierSession.getGeneratedConvocationPDFPath())) {
            String token = Base64.encode(dossierSession.getDossier().getGeneratedNumDossier() + "" + (new Date()).toString());
            token = token.replace("=", "E");
            dossierSession.setGeneratedConvocationPDFPath(token);
            dossierSessionRepos.save(dossierSession);
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("gender", dossier.getOwner().getGender().toString());
        params.put("lastname", dossier.getOwner().getLastName());
        params.put("firstname", dossier.getOwner().getFirstName());
        params.put("nomEnfant", dossier.getCandidat().getLastname());
        params.put("prenomEnfant", dossier.getCandidat().getFirstname());
        params.put("refdossier", dossier.getGeneratedNumDossier());
        params.put("link", link + "resource/convocation?number=" + dossierSession.getGeneratedConvocationPDFPath());

        try {
            EmailDTO email = emailService.populateEmail(EmailType.CONVOCATED_FOR_TEST,
                    dossier.getOwner().getEmail(), sender,
                    params, sender, "");
            emailService.prepare(email);

            InboxMessage message = new InboxMessage();
            message.setParentUser(dossier.getOwner());
            message.setSubject(email.getSubject());
            message.setContent(email.getMessage());
            message.setMsgDate(new Date());
            message.setMsgStatus(InboxMessageStatus.UNREAD);
            inboxMessageRepos.save(message);
        } catch (Exception e) {
            e.printStackTrace();
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
        ValueList currentAnnee = getCurrentScholarYear();

        if (currentAnnee != null) {
            return sessionExamenRepos.findByAnneeScolaireId(currentAnnee.getId());
        } else {
            return new ArrayList<SessionExamen>();
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<SessionExamen> findAllSessions(List<Integer> sessionIdList) {
        ValueList currentAnnee = getCurrentScholarYear();

        if (currentAnnee != null) {
            return sessionExamenRepos.findByAnneeScolaireAndStatusList(currentAnnee.getId(), sessionIdList);   // SessionStatus.OPEN);
        } else {
            return new ArrayList<SessionExamen>();
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<SessionExamen> findAllSessionsWithStatus(SessionStatus sessionStatus) {
        ValueList currentAnnee = getCurrentScholarYear();

        if (currentAnnee != null) {
            return sessionExamenRepos.findByAnneeScolaireIdAndStatusOrderByNomAsc(currentAnnee.getId(), sessionStatus);
        } else {
            return new ArrayList<SessionExamen>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SessionExamen> findAllSessionsWithStatusAndAnneeScolaire(SessionStatus sessionStatus, ValueList anneeScolaire) {
        if (anneeScolaire == null) {
            anneeScolaire = getCurrentScholarYear();
        }
        return sessionExamenRepos.findByAnneeScolaireIdAndStatusOrderByNomAsc(anneeScolaire.getId(), sessionStatus);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SessionExamen> findSessionByNE(NiveauEtude niveau) {
        ValueList currentAnnee = getCurrentScholarYear();

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
            return true;
        }

        DossierSession exist = dossierSessionRepos.findByDossierId(dossier.getId());
        if (exist != null && exist.getSessionExamen().getStatus() == SessionStatus.CLOSED) {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DAY_OF_MONTH, -1);

            Date dateSession = exist.getSessionExamen().getDateSession();
            Date yesterday = calendar.getTime();

            if (yesterday.before(dateSession)) {
                throw new AffectationClosedException();
            }
        }
        if (exist != null) {
            dossierSessionRepos.delete(exist);
        }

        Dossier affectedDossier = dossierRepos.findOne(dossier.getId());

        DossierHistoric dossierHistoric = new DossierHistoric();
        dossierHistoric.setStatus(affectedDossier.getStatus());
        dossierHistoric.setCreateDate(new Date());
        dossierHistoric.setDossier(affectedDossier);
        dossierHistoricRepo.save(dossierHistoric);

        affectedDossier.setStatus(DossierStatus.AFFECTED);

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

            if (yesterday.before(dateSession)) {
                throw new AffectationClosedException();
            }
        }

        Dossier affectedDossier = dossierRepos.findOne(dossier.getId());

        DossierHistoric dossierHistoric = new DossierHistoric();
        dossierHistoric.setStatus(affectedDossier.getStatus());
        dossierHistoric.setCreateDate(new Date());
        dossierHistoric.setDossier(affectedDossier);
        dossierHistoricRepo.save(dossierHistoric);

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

    private ValueList getCurrentScholarYear() {
        String currentScholarYear = DateUtils.currentYear() + "-" + (DateUtils.currentYear() + 1);
        ValueList scholarYear = valueListRepos.findByValueAndValueTypeCode(currentScholarYear, ValueTypeCode.SCHOOL_YEAR);
        if (scholarYear == null) {
            scholarYear = new ValueList();
            scholarYear.setLabel(currentScholarYear);
            scholarYear.setValue(currentScholarYear);
            scholarYear.setValueType(valueTypeRepos.findByCode(ValueTypeCode.BAC_YEAR));

            valueListRepos.save(scholarYear);
        }
        return scholarYear;
    }
}
