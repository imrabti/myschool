package com.gsr.myschool.back.client.request;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.gsr.myschool.back.client.request.proxy.LOVProxy;
import com.gsr.myschool.server.service.impl.LOVServiceImpl;
import com.gsr.myschool.server.util.SpringServiceLocator;

import java.util.List;

@Service(value = LOVServiceImpl.class, locator = SpringServiceLocator.class)
public interface LOVServiceRequest extends RequestContext {

    public Request<Void> add(LOVProxy lovProxy);
    public Request<List<LOVProxy>> findAll();
    public Request<List<LOVProxy>> findByDefLovName(String defLovName);
    public Request<LOVProxy> find(Long id);
    public Request<Void> add(String value, Long parentId, Long defLovId);
    public Request<List<LOVProxy>> findByDefLovParentName(String defLovName);
    public Request<Void> delete(Long id);

}
