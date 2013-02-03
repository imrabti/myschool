package com.gsr.myschool.server.repos;

import com.gsr.myschool.server.business.Fraterie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FraterieRepos extends JpaRepository<Fraterie, Long> {
    List<Fraterie> findByCandidatId(Long candidatId);
}
