package com.gsr.myschool.server.repos;

import com.gsr.myschool.server.business.core.NiveauEtude;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NiveauEtudeRepos extends JpaRepository<NiveauEtude, Long> {
}
