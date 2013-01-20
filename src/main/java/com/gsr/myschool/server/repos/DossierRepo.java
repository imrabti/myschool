package com.gsr.myschool.server.repos;

import com.gsr.myschool.server.business.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DossierRepo extends JpaRepository<Dossier, Long> {
}
