package com.gsr.myschool.back.client.web.application.user.popup;

import com.gsr.myschool.common.client.proxy.UserProxy;
import com.gwtplatform.mvp.client.UiHandlers;

public interface UserAccountEditUiHandlers extends UiHandlers {
    void saveAccount(UserProxy userProxy);
}
