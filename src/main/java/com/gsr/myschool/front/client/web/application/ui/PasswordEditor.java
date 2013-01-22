package com.gsr.myschool.front.client.web.application.ui;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class PasswordEditor extends Composite {
    public interface Binder extends UiBinder<Widget, PasswordEditor> {
    }

    @Inject
    public PasswordEditor(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
