package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ViewImpl;
import com.gsr.myschool.front.client.web.application.inscription.ui.ParentEditor;

public class ParentView extends ViewImpl implements ParentPresenter.MyView  {
    public interface Binder extends UiBinder<Widget, ParentView> {
    }

    @UiField(provided = true)
    ParentEditor parentEditor;

    @Inject
    public ParentView(final Binder uiBinder, final ParentEditor parentEditor) {
        this.parentEditor = parentEditor;

        initWidget(uiBinder.createAndBindUi(this));
    }
}
