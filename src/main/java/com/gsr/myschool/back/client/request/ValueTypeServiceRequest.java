package com.gsr.myschool.back.client.request;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.gsr.myschool.back.client.request.proxy.ValueTypeProxy;
import com.gsr.myschool.server.service.impl.ValueTypeServiceImpl;
import com.gsr.myschool.server.util.SpringServiceLocator;

import java.util.List;

@Service(value = ValueTypeServiceImpl.class, locator = SpringServiceLocator.class)
public interface ValueTypeServiceRequest extends RequestContext {
    Request<Void> add(ValueTypeProxy valueTypeProxy);

    Request<ValueTypeProxy> findByName(String name);

    Request<List<ValueTypeProxy>> findAll();

    Request<Void> delete(ValueTypeProxy toDelete);
}
