package com.gsr.myschool.back.client.web.application.settings.popup;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.PopupViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.widget.ModalHeader;

public class AddNiveauEtudeView extends PopupViewWithUiHandlers<AddNiveauEtudeUiHandlers>
        implements AddNiveauEtudePresenter.MyView {
    public interface Binder extends UiBinder<Widget, AddNiveauEtudeView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;

    @Inject
    public AddNiveauEtudeView(final EventBus eventBus, final Binder uiBinder, final ModalHeader modalHeader,
                              final UiHandlersStrategy<AddNiveauEtudeUiHandlers> uiHandlersStrategy) {
        super(eventBus, uiHandlersStrategy);

        this.modalHeader = modalHeader;

        initWidget(uiBinder.createAndBindUi(this));

        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });
    }
}
