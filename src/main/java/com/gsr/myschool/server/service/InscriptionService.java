package com.gsr.myschool.server.service;

import com.gsr.myschool.common.shared.dto.ScolariteAnterieurDTO;
import com.gsr.myschool.server.business.Candidat;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.business.InfoParent;
import com.gsr.myschool.server.business.ScolariteAnterieur;

import java.util.List;

public interface InscriptionService {
    List<Dossier> findAllDossiers();

    Dossier findDossierById(Long dossierId);

    Dossier createNewInscription();

    void deleteInscription(Long dossierId);

    Dossier updateDossier(Dossier dossier);

    InfoParent updateParent(InfoParent infoParent);

    Candidat updateCandidat(Candidat candidat);

    List<ScolariteAnterieur> findScolariteAnterieursByDossierId(Long dossierId);

    void createNewScolariteAnterieur(ScolariteAnterieurDTO scolariteAnterieur, Long dossierId);

    void deleteScolariteAnterieur(Long scolariteAnterieurId);
}
