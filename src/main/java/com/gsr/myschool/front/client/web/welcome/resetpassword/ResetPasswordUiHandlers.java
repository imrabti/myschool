package com.gsr.myschool.front.client.web.welcome.resetpassword;

import com.gsr.myschool.common.client.proxy.ResetPasswordDTOProxy;
import com.gwtplatform.mvp.client.UiHandlers;

public interface ResetPasswordUiHandlers extends UiHandlers {
    void saveNewPassword(ResetPasswordDTOProxy resetPassword);
}
