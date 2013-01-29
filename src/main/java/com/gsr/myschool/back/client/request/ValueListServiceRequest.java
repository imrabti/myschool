package com.gsr.myschool.back.client.request;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.gsr.myschool.common.client.proxy.ValueListProxy;
import com.gsr.myschool.common.shared.type.ValueTypeCode;
import com.gsr.myschool.server.service.impl.ValueListServiceImpl;
import com.gsr.myschool.server.util.SpringServiceLocator;

import java.util.List;

@Service(value = ValueListServiceImpl.class, locator = SpringServiceLocator.class)
public interface ValueListServiceRequest extends RequestContext {
    Request<Void> addValueList(ValueListProxy valueList);

    Request<Void> deleteValueList(Long id);

    Request<List<ValueListProxy>> findAll();

    Request<List<ValueListProxy>> findByValueTypeCode(ValueTypeCode valueTypeCode);
}
