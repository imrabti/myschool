package com.gsr.myschool.back.client.web.application.usergsr.popup;

import com.gsr.myschool.common.client.proxy.AdminUserProxy;
import com.gwtplatform.mvp.client.UiHandlers;

public interface AdminUserAccountEditUiHandlers extends UiHandlers {
    void saveAccount(AdminUserProxy userProxy);
}
