package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.common.shared.dto.ForgotPasswordDTO;

@ProxyFor(ForgotPasswordDTO.class)
public interface ForgotPasswordDTOProxy extends ValueProxy {
    String getEmail();

    void setEmail(String email);
}
