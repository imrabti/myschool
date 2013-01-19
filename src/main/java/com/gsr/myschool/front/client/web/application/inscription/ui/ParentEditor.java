package com.gsr.myschool.front.client.web.application.inscription.ui;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class ParentEditor extends Composite {
    public interface Binder extends UiBinder<Widget, ParentEditor> {
    }

    @Inject
    public ParentEditor(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
