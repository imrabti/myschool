package com.gsr.myschool.back.client.request;


import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.gsr.myschool.back.client.request.proxy.DefLovProxy;
import com.gsr.myschool.server.service.impl.DefLovServiceImpl;
import com.gsr.myschool.server.util.SpringServiceLocator;

import java.util.List;

@Service(value = DefLovServiceImpl.class, locator = SpringServiceLocator.class)
public interface DefLovServiceRequest extends RequestContext {

    public Request<Void> add(DefLovProxy defLovProxy);
    public Request<DefLovProxy> findByName(String name);
    public Request<List<DefLovProxy>> findAll();
    public Request<Void> delete(DefLovProxy toDelete);
}
