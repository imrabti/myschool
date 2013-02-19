package com.gsr.myschool.server.service;

import com.gsr.myschool.common.shared.dto.EtablissementFilterDTO;
import com.gsr.myschool.common.shared.dto.FraterieDTO;
import com.gsr.myschool.common.shared.dto.ScolariteAnterieurDTO;
import com.gsr.myschool.server.business.*;

import java.util.List;

public interface InscriptionService {
    List<Dossier> findAllDossiers();

    Dossier findDossierById(Long dossierId);

    Dossier createNewInscription();

    void deleteInscription(Long dossierId);

    Dossier updateDossier(Dossier dossier);

    InfoParent updateParent(InfoParent infoParent);

    Candidat updateCandidat(Candidat candidat);

    List<InfoParent> findInfoParentByDossierId(Long dossierId);

    List<EtablissementScolaire> findEtablissementByFilter(EtablissementFilterDTO filter);

    List<ScolariteAnterieur> findScolariteAnterieursByDossierId(Long dossierId);

    void createNewScolariteAnterieur(ScolariteAnterieurDTO scolariteAnterieur, Long dossierId);

    void deleteScolariteAnterieur(Long scolariteAnterieurId);

    List<Fraterie> findFraterieByDossierId(Long dossierId);

    void createNewFraterie(FraterieDTO fraterie, Long dossierId);

    void deleteFraterie(Long fraterieId);

    List<String> submitInscription(Long dossierId);
}
