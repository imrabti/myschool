package com.gsr.myschool.client.util;

import com.google.gwt.core.client.Callback;

import java.util.logging.Logger;

public abstract class CallbackImpl<T> implements Callback<T, String> {
    private final static Logger logger = Logger.getLogger(CallbackImpl.class.getName());

    @Override
    public void onFailure(String s) {
        logger.severe("Error during the callback.");
    }
}
