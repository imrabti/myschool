package com.gsr.myschool.back.client.request;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.gsr.myschool.back.client.request.proxy.ValueListProxy;
import com.gsr.myschool.server.service.impl.ValueListServiceImpl;
import com.gsr.myschool.server.util.SpringServiceLocator;

import java.util.List;

@Service(value = ValueListServiceImpl.class, locator = SpringServiceLocator.class)
public interface ValueListServiceRequest extends RequestContext {
    Request<Void> add(ValueListProxy valueListProxy);

    Request<List<ValueListProxy>> findAll();

    Request<List<ValueListProxy>> findByValueTypeName(String defLovName);

    Request<ValueListProxy> find(Long id);

    Request<Void> add(String value, Long parentId, Long defLovId);

    Request<List<ValueListProxy>> findByValueTypeParentName(String valueTypeParentName);

    Request<Void> delete(Long id);
}
