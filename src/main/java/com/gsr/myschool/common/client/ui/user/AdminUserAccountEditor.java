package com.gsr.myschool.common.client.ui.user;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class AdminUserAccountEditor extends Composite {
    public interface Binder extends UiBinder<Widget, AdminUserAccountEditor> {
    }



    @Inject
    public AdminUserAccountEditor(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }


}