package com.gsr.myschool.server.service;

import com.gsr.myschool.server.business.Dossier;

import java.util.List;

public interface DossierService {
    List<Dossier> findAllDossiersByUser(Long userId);
}
