package com.gsr.myschool.server.repos;

import com.gsr.myschool.server.business.core.SessionExamen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionExamenRepos extends JpaRepository<SessionExamen, Long> {
    List<SessionExamen> findByAnneeScolaireId(Long id);
}
