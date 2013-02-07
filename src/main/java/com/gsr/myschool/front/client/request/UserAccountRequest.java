package com.gsr.myschool.front.client.request;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.gsr.myschool.common.client.proxy.PasswordDTOProxy;
import com.gsr.myschool.common.client.proxy.UserProxy;
import com.gsr.myschool.server.service.impl.UserAccountServiceImpl;
import com.gsr.myschool.server.util.SpringServiceLocator;

@Service(value = UserAccountServiceImpl.class, locator = SpringServiceLocator.class)
public interface UserAccountRequest extends RequestContext {
    Request<UserProxy> updateAccount(UserProxy user);

    Request<Void> updatePassword(PasswordDTOProxy password);
}
