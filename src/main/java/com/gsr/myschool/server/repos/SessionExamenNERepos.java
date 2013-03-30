package com.gsr.myschool.server.repos;

import com.gsr.myschool.server.business.core.SessionNiveauEtude;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionExamenNERepos extends JpaRepository<SessionNiveauEtude, Long> {
    List<SessionNiveauEtude> findByNiveauEtudeId(Long id);

    List<SessionNiveauEtude> findBySessionExamenId(Long id);
}
