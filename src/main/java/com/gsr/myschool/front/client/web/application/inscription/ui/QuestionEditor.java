package com.gsr.myschool.front.client.web.application.inscription.ui;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.ReponseQuestionProxy;
import com.gsr.myschool.common.client.util.EditorView;

public class QuestionEditor extends Composite implements EditorView<ReponseQuestionProxy> {
    public interface Binder extends UiBinder<Widget, QuestionEditor> {
    }

    @Inject
    public QuestionEditor(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void edit(ReponseQuestionProxy object) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ReponseQuestionProxy get() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}