package com.gsr.myschool.common.client.widget;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class RowHeader extends Composite {
    public interface Binder extends UiBinder<Widget, RowHeader> {
    }

    @UiField
    Label label;

    @Inject
    public RowHeader(final Binder uiBinder, @Assisted String labelTxt) {
        initWidget(uiBinder.createAndBindUi(this));

        label.setText(labelTxt);
    }
}
