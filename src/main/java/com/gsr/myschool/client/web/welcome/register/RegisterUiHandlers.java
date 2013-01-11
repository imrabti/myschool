package com.gsr.myschool.client.web.welcome.register;

import com.gwtplatform.mvp.client.UiHandlers;
import com.gsr.myschool.client.request.proxy.UserProxy;

public interface RegisterUiHandlers extends UiHandlers {
    void register(UserProxy user);
}
