package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ValidatedViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.ValidationErrorPopup;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;

public class ScolariteAnterieurView extends ValidatedViewWithUiHandlers<ScolariteAnterieurUiHandlers>
        implements ScolariteAnterieurPresenter.MyView {
    public interface Binder extends UiBinder<Widget, ScolariteAnterieurView> {
    }

    @Inject
    public ScolariteAnterieurView(final Binder uiBinder, final ValidationErrorPopup errorPopup,
                                  final UiHandlersStrategy<ScolariteAnterieurUiHandlers> uiHandlersStrategy) {
        super(uiHandlersStrategy, errorPopup);

        initWidget(uiBinder.createAndBindUi(this));
    }
}
