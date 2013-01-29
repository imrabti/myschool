package com.gsr.myschool.server.service;

import com.gsr.myschool.server.business.Candidat;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.business.InfoParent;

import java.util.List;

public interface InscriptionService {
    List<Dossier> findAllDossiers();

    Dossier findDossierById(Long id);

    Dossier createNewInscription();

    Dossier updateDossier(Dossier dossier);

    InfoParent updateParent(InfoParent infoParent);

    Candidat updateCandidat(Candidat candidat);
}
