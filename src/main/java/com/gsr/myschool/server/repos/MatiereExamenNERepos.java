package com.gsr.myschool.server.repos;

import com.gsr.myschool.server.business.core.MatiereExamDuNE;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatiereExamenNERepos extends JpaRepository<MatiereExamDuNE, Long> {
    List<MatiereExamDuNE> findByNiveauEtudeId(Long id);
}
