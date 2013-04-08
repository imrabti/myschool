package com.gsr.myschool.back.client.util;

import com.google.inject.Inject;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.util.CallbackImpl;

import java.util.List;

public class SuggestionListFactory {
    private final BackRequestFactory requestFactory;

    @Inject
    public SuggestionListFactory(final BackRequestFactory requestFactory) {
        this.requestFactory = requestFactory;
    }

    public void getListNumDossier(final CallbackImpl<List<String>> callback) {
        requestFactory.cachedListValueService().findAllNumDossier().fire(new ReceiverImpl<List<String>>() {
            @Override
            public void onSuccess(List<String> response) {
                callback.onSuccess(response);
            }
        });
    }

    public void getListPieces(final CallbackImpl<List<String>> callback) {
        requestFactory.cachedListValueService().findPieces().fire(new ReceiverImpl<List<String>>() {
            @Override
            public void onSuccess(List<String> response) {
                callback.onSuccess(response);
            }
        });
    }

    public void getListMatieres(final CallbackImpl<List<String>> callback) {
        requestFactory.cachedListValueService().findMatieres().fire(new ReceiverImpl<List<String>>() {
            @Override
            public void onSuccess(List<String> response) {
                callback.onSuccess(response);
            }
        });
    }

    public void getListUserMain(CallbackImpl<List<String>> callback) {
        // TODO : Add a new suggestion method...
    }
}
