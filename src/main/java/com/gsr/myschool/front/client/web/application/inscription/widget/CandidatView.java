package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ValidatedViewImpl;
import com.gsr.myschool.common.client.mvp.ValidationErrorPopup;
import com.gsr.myschool.common.client.proxy.CandidatProxy;
import com.gsr.myschool.common.client.ui.dossier.CandidatEditor;

public class CandidatView extends ValidatedViewImpl implements CandidatPresenter.MyView {
    public interface Binder extends UiBinder<Widget, CandidatView> {
    }

    @UiField(provided = true)
    CandidatEditor candidatEditor;

    @Inject
    public CandidatView(final Binder uiBinder, final CandidatEditor candidatEditor,
                        final ValidationErrorPopup validationErrorPopup) {
        super(validationErrorPopup);

        this.candidatEditor = candidatEditor;

        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void editCandidat(CandidatProxy candidat) {
        candidatEditor.edit(candidat);
    }

    @Override
    public void flushCandidat() {
        candidatEditor.get();
    }
}
