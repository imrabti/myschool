package com.gsr.myschool.back.client.web.application.session;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;

public class SessionView extends ViewWithUiHandlers<SessionUiHandlers> implements SessionPresenter.MyView {
    public interface Binder extends UiBinder<Widget, SessionView> {
    }

    @Inject
    public SessionView(final Binder uiBinder,
                       final UiHandlersStrategy<SessionUiHandlers> uiHandlers) {
        super(uiHandlers);

        initWidget(uiBinder.createAndBindUi(this));
    }
}