package com.gsr.myschool.server.repos;

import com.gsr.myschool.server.business.EtablissementScolaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EtablissementScolaireRepos extends JpaRepository<EtablissementScolaire, Long>, JpaSpecificationExecutor {
}
