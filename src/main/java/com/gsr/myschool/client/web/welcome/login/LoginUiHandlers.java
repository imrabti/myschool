package com.gsr.myschool.client.web.welcome.login;

import com.gwtplatform.mvp.client.UiHandlers;
import com.gsr.myschool.shared.dto.UserCredentials;

public interface LoginUiHandlers extends UiHandlers {
    void login(UserCredentials credentials);
}
