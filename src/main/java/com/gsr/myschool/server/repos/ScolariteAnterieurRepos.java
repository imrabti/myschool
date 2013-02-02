package com.gsr.myschool.server.repos;

import com.gsr.myschool.server.business.ScolariteAnterieur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScolariteAnterieurRepos extends JpaRepository<ScolariteAnterieur, Long> {
    List<ScolariteAnterieur> findByCandidatId(Long candidatId);
}
