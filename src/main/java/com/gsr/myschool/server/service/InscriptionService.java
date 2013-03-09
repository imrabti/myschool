package com.gsr.myschool.server.service;

import com.gsr.myschool.common.shared.dto.EtablissementFilterDTO;
import com.gsr.myschool.common.shared.dto.FraterieDTO;
import com.gsr.myschool.common.shared.dto.ScolariteActuelleDTO;
import com.gsr.myschool.common.shared.exception.UnAuthorizedException;
import com.gsr.myschool.server.business.*;
import com.gsr.myschool.common.shared.exception.InscriptionClosedException;

import java.util.List;

public interface InscriptionService {
    List<Dossier> findAllDossiers();

    Dossier findDossierById(Long dossierId) throws UnAuthorizedException;

    Dossier createNewInscription() throws InscriptionClosedException;

    void deleteInscription(Long dossierId);

    Dossier updateDossier(Dossier dossier);

    InfoParent updateParent(InfoParent infoParent);

    Candidat updateCandidat(Candidat candidat);

    ScolariteActuelle updateScolariteActuelle(ScolariteActuelleDTO scolariteActuelle);

    List<InfoParent> findInfoParentByDossierId(Long dossierId);

    List<EtablissementScolaire> findEtablissementByFilter(EtablissementFilterDTO filter);

    List<Fraterie> findFraterieByDossierId(Long dossierId);

    void createNewFraterie(FraterieDTO fraterie, Long dossierId);

    void deleteFraterie(Long fraterieId);

    List<String> submitInscription(Long dossierId) throws InscriptionClosedException;

    Boolean statusInscriptionOpened();
}
