package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ViewImpl;
import com.gsr.myschool.front.client.web.application.inscription.ui.CandidatEditor;

public class CandidatView extends ViewImpl implements CandidatPresenter.MyView {
    public interface Binder extends UiBinder<Widget, CandidatView> {
    }

    @UiField(provided = true)
    CandidatEditor candidatEditor;

    @Inject
    public CandidatView(final Binder uiBinder, final CandidatEditor candidatEditor) {
        this.candidatEditor = candidatEditor;

        initWidget(uiBinder.createAndBindUi(this));
    }
}
