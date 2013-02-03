package com.gsr.myschool.server.repos;

import com.gsr.myschool.server.business.core.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FiliereRepos extends JpaRepository<Filiere, Long> {
    Filiere findByNom(String nom);
}
