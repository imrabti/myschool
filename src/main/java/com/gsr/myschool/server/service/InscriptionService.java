package com.gsr.myschool.server.service;

import com.gsr.myschool.server.business.Dossier;

import java.util.List;

public interface InscriptionService {
    List<Dossier> findAllDossiers();

    Dossier findDossierById(Long id);

    Dossier createNewInscription();
}
