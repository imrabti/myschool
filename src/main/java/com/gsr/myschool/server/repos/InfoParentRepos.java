package com.gsr.myschool.server.repos;

import com.gsr.myschool.server.business.InfoParent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InfoParentRepos extends JpaRepository<InfoParent, Long> {
    List<InfoParent> findByDossierId(Long dossierId);
    List<InfoParent> findByDossierIdAndParentGsr(Long dossierId, Boolean isParentGsr);
}
