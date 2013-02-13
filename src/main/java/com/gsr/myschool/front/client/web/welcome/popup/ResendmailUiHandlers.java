package com.gsr.myschool.front.client.web.welcome.popup;

import com.gsr.myschool.common.client.proxy.ForgotPasswordDTOProxy;
import com.gwtplatform.mvp.client.UiHandlers;

public interface ResendmailUiHandlers extends UiHandlers {
    void mailnotreceived(ForgotPasswordDTOProxy forgotPassword);
}
