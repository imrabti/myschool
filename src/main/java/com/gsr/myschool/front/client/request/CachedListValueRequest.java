package com.gsr.myschool.front.client.request;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.gsr.myschool.common.client.proxy.EtablissementScolaireProxy;
import com.gsr.myschool.common.client.proxy.FiliereProxy;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gsr.myschool.common.client.proxy.ValueListProxy;
import com.gsr.myschool.server.service.impl.CachedListValueServiceServiceImpl;
import com.gsr.myschool.server.util.SpringServiceLocator;

import java.util.List;

@Service(value = CachedListValueServiceServiceImpl.class, locator = SpringServiceLocator.class)
public interface CachedListValueRequest extends RequestContext {
    Request<List<FiliereProxy>> findAllFiliere();

    Request<List<EtablissementScolaireProxy>> findAllEtablissementScolaire();

    Request<List<NiveauEtudeProxy>> findAllNiveauEtude();

    Request<List<ValueListProxy>> findAllValueList();

    Request<List<FiliereProxy>> findFilieres();
}
