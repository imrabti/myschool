package com.gsr.myschool.front.client.request;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.gsr.myschool.common.client.proxy.CandidatProxy;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.FraterieProxy;
import com.gsr.myschool.common.client.proxy.InfoParentProxy;
import com.gsr.myschool.common.client.proxy.ScolariteAnterieurDTOProxy;
import com.gsr.myschool.common.client.proxy.ScolariteAnterieurProxy;
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

    Request<List<ScolariteAnterieurProxy>> findScolariteAnterieursByDossierId(Long dossierId);

    Request<Void> createNewScolariteAnterieur(ScolariteAnterieurDTOProxy scolariteAnterieur, Long dossierId);

    Request<Void> deleteScolariteAnterieur(Long scolariteAnterieurId);

    Request<List<FraterieProxy>> findFraterieByDossierId(Long dossierId);

    Request<Void> createNewFraterie(FraterieProxy fraterie, Long dossierId);

    Request<Void> deleteFraterie(Long fraterieId);

    Request<List<String>> submitInscription(Long dossierId);
}
