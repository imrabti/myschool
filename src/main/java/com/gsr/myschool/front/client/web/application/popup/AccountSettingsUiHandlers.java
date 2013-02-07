package com.gsr.myschool.front.client.web.application.popup;

import com.gsr.myschool.common.client.proxy.PasswordDTOProxy;
import com.gsr.myschool.common.client.proxy.UserProxy;
import com.gwtplatform.mvp.client.UiHandlers;

public interface AccountSettingsUiHandlers extends UiHandlers {
    void saveUser(UserProxy user);

    void savePassword(PasswordDTOProxy password);
}
