package com.gsr.myschool.server.repos;

import com.gsr.myschool.server.business.Candidat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatRepos extends JpaRepository<Candidat, Long> {
}
