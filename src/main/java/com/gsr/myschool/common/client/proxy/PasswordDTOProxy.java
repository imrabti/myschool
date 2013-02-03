package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.common.shared.dto.PasswordDTO;

@ProxyFor(PasswordDTO.class)
public interface PasswordDTOProxy extends ValueProxy {
    String getOldPassword();

    void setOldPassword(String oldPassword);

    String getPassword();

    void setPassword(String password);

    String getPasswordConfirmation();

    void setPasswordConfirmation(String passwordConfirmation);
}
