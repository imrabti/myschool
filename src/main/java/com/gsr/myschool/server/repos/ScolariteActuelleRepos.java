package com.gsr.myschool.server.repos;

import com.gsr.myschool.server.business.ScolariteActuelle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScolariteActuelleRepos extends JpaRepository<ScolariteActuelle, Long> {
}
