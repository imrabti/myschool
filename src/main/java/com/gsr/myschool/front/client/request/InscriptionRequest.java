package com.gsr.myschool.front.client.request;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.gsr.myschool.common.client.proxy.*;
import com.gsr.myschool.common.shared.dto.EtablissementFilterDTO;
import com.gsr.myschool.server.business.EtablissementScolaire;
import com.gsr.myschool.server.service.impl.InscriptionServiceImpl;
import com.gsr.myschool.server.util.SpringServiceLocator;

import java.util.List;

@Service(value = InscriptionServiceImpl.class, locator = SpringServiceLocator.class)
public interface InscriptionRequest extends RequestContext {
    Request<List<DossierProxy>> findAllDossiers();

    Request<DossierProxy> findDossierById(Long dossierId);

    Request<DossierProxy> createNewInscription();

    Request<Void> deleteInscription(Long dossierId);

    Request<DossierProxy> updateDossier(DossierProxy dossier);

    Request<InfoParentProxy> updateParent(InfoParentProxy infoParent);

    Request<CandidatProxy> updateCandidat(CandidatProxy candidat);

    Request<List<InfoParentProxy>> findInfoParentByDossierId(Long dossierId);

    Request<List<EtablissementScolaireProxy>> findEtablissementByFilter(EtablissementFilterDTOProxy filter);

    Request<List<ScolariteAnterieurProxy>> findScolariteAnterieursByDossierId(Long dossierId);

    Request<Void> createNewScolariteAnterieur(ScolariteAnterieurDTOProxy scolariteAnterieur, Long dossierId);

    Request<Void> deleteScolariteAnterieur(Long scolariteAnterieurId);

    Request<List<FraterieProxy>> findFraterieByDossierId(Long dossierId);

    Request<Void> createNewFraterie(FraterieDTOProxy fraterie, Long dossierId);

    Request<Void> deleteFraterie(Long fraterieId);

    Request<List<String>> submitInscription(Long dossierId);
}
