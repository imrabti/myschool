package com.gsr.myschool.front.client.web.application.inscription.ui;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class CandidatEditor extends Composite {
    public interface Binder extends UiBinder<Widget, CandidatEditor> {
    }

    @Inject
    public CandidatEditor(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
