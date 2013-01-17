package com.gsr.myschool.server.repos;

import com.gsr.myschool.server.business.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscriptionRepo extends JpaRepository<Inscription, Long> {
}
