package com.gsr.myschool.front.client.request;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.gsr.myschool.front.client.request.proxy.InscriptionProxy;
import com.gsr.myschool.server.service.impl.InscriptionServiceImpl;
import com.gsr.myschool.server.util.SpringServiceLocator;

import java.util.List;

@Service(value = InscriptionServiceImpl.class, locator = SpringServiceLocator.class)
public interface InscriptionRequest extends RequestContext {
    Request<List<InscriptionProxy>> findAllInscriptionsByUser(Long userId);
}
