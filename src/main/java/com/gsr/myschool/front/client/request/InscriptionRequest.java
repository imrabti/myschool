package com.gsr.myschool.front.client.request;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.gsr.myschool.common.client.proxy.CandidatProxy;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.InfoParentProxy;
import com.gsr.myschool.server.service.impl.InscriptionServiceImpl;
import com.gsr.myschool.server.util.SpringServiceLocator;

import java.util.List;

@Service(value = InscriptionServiceImpl.class, locator = SpringServiceLocator.class)
public interface InscriptionRequest extends RequestContext {
    Request<List<DossierProxy>> findAllDossiers();

    Request<DossierProxy> findDossierById(Long id);

    Request<DossierProxy> createNewInscription();

    Request<DossierProxy> updateDossier(DossierProxy dossier);

    Request<InfoParentProxy> updateParent(InfoParentProxy infoParent);

    Request<CandidatProxy> updateCandidat(CandidatProxy candidat);
}
