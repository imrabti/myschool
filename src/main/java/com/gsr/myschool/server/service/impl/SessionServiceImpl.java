package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.common.shared.type.SessionStatus;
import com.gsr.myschool.common.shared.type.ValueTypeCode;
import com.gsr.myschool.server.business.core.SessionExamen;
import com.gsr.myschool.server.business.valuelist.ValueList;
import com.gsr.myschool.server.repos.SessionExamenRepos;
import com.gsr.myschool.server.repos.ValueListRepos;
import com.gsr.myschool.server.service.SessionService;
import com.gsr.myschool.server.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SessionServiceImpl implements SessionService {
    @Autowired
    private ValueListRepos valueListRepos;
    @Autowired
    private SessionExamenRepos sessionExamenRepos;

    @Override
    public void createNewSession(SessionExamen sessionExamen) {
        String currentAnneeScolaire = DateUtils.currentYear() + "-" + (DateUtils.currentYear() + 1);
        sessionExamen.setAnneeScolaire(valueListRepos.findByValueAndValueTypeCode(currentAnneeScolaire,
                ValueTypeCode.SCHOOL_YEAR));
        sessionExamen.setStatus(SessionStatus.OPEN);
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
}
