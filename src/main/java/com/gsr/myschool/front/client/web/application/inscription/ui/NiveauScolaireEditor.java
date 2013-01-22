package com.gsr.myschool.front.client.web.application.inscription.ui;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class NiveauScolaireEditor extends Composite {
    public interface Binder extends UiBinder<Widget, NiveauScolaireEditor> {
    }

    @Inject
    public NiveauScolaireEditor(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
