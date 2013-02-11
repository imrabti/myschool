package com.gsr.myschool.server.service;

import com.gsr.myschool.common.shared.dto.ScolariteAnterieurDTO;
import com.gsr.myschool.common.shared.type.ParentType;
import com.gsr.myschool.server.business.Candidat;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.business.Fraterie;
import com.gsr.myschool.server.business.InfoParent;
import com.gsr.myschool.server.business.ScolariteAnterieur;

import java.util.List;
import java.util.Map;

public interface InscriptionService {
    List<Dossier> findAllDossiers();

    Dossier findDossierById(Long dossierId);

    Dossier createNewInscription();

    void deleteInscription(Long dossierId);

    Dossier updateDossier(Dossier dossier);

    InfoParent updateParent(InfoParent infoParent);

    Candidat updateCandidat(Candidat candidat);

    List<InfoParent> findInfoParentByDossierId(Long dossierId);

    List<ScolariteAnterieur> findScolariteAnterieursByDossierId(Long dossierId);

    void createNewScolariteAnterieur(ScolariteAnterieurDTO scolariteAnterieur, Long dossierId);

    void deleteScolariteAnterieur(Long scolariteAnterieurId);

    List<Fraterie> findFraterieByDossierId(Long dossierId);

    void createNewFraterie(Fraterie fraterie, Long dossierId);

    void deleteFraterie(Long fraterieId);

    List<String> submitInscription(Long dossierId);
}
