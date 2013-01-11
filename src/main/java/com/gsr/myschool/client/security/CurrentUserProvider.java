package com.gsr.myschool.client.security;

import com.gsr.myschool.client.request.MyRequestFactory;
import com.gsr.myschool.client.request.ReceiverImpl;
import com.gsr.myschool.client.request.proxy.UserProxy;
import com.gsr.myschool.client.util.CallbackImpl;

import javax.inject.Inject;

public class CurrentUserProvider {
    private final MyRequestFactory requestFactory;

    private UserProxy currentUser;

    @Inject
    public CurrentUserProvider(final MyRequestFactory requestFactory) {
        this.requestFactory = requestFactory;
    }

    public void load(final CallbackImpl<UserProxy> callback) {
        requestFactory.authenticationService().currentUser().fire(new ReceiverImpl<UserProxy>() {
            @Override
            public void onSuccess(UserProxy userProxy) {
                currentUser = userProxy;
                callback.onSuccess(userProxy);
            }
        });
    }

    public UserProxy get() {
        return currentUser;
    }
}
