package com.gsr.myschool.server.repos;

import com.gsr.myschool.common.shared.type.SessionStatus;
import com.gsr.myschool.server.business.core.SessionExamen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SessionExamenRepos extends JpaRepository<SessionExamen, Long> {
    List<SessionExamen> findByAnneeScolaireId(Long id);

    @Query("select distinct s from SessionExamen s, SessionNiveauEtude sn " +
            "where s.id = sn.sessionExamen.id " +
            "and s.anneeScolaire.id = :anneeId " +
            "and sn.niveauEtude.id = :id " +
            "and s.status = :status")
    List<SessionExamen> findByNiveauEtude(@Param("anneeId") Long anneeId,
                                          @Param("id") Long id,
                                          @Param("status") SessionStatus status);

    List<SessionExamen> findByAnneeScolaireIdAndStatus(Long id, SessionStatus status);

    @Query("select s from SessionExamen s "+
            "where s.anneeScolaire.id = :anneeId " +
            "and s.status in  :statusList")
    List<SessionExamen> findByAnneeScolaireAndStatusList(@Param("anneeId") Long anneeId,
                                              @Param("statusList") List<Integer> statusList);
}
