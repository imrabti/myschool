package com.gsr.myschool.back.client.web.application.validation.popup;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.web.application.validation.popup.ui.PieceJustificatifEditor;
import com.gsr.myschool.back.client.web.application.validation.popup.ui.PieceJustificatifEditorFactory;
import com.gsr.myschool.common.client.mvp.PopupViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.PieceJustifProxy;
import com.gsr.myschool.common.client.widget.ModalHeader;

import java.util.ArrayList;
import java.util.List;

public class PiecesJustifcatifView extends PopupViewWithUiHandlers<PiecesJustificatifUiHandlers>
        implements PiecesJustificatifPresenter.MyView {
    interface Binder extends UiBinder<Widget, PiecesJustifcatifView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;
    @UiField
    HTMLPanel piecesPanel;

    private final PieceJustificatifEditorFactory pieceJustificatifFactory;
    private final List<PieceJustificatifEditor> editors;

    @Inject
    public PiecesJustifcatifView(final Binder uiBinder, final EventBus eventBus,
                                 final ModalHeader modalHeader,
                                 final PieceJustificatifEditorFactory pieceJustificatifFactory,
                                 final UiHandlersStrategy<PiecesJustificatifUiHandlers> uiHandlers) {
        super(eventBus, uiHandlers);

        this.modalHeader = modalHeader;
        this.pieceJustificatifFactory = pieceJustificatifFactory;
        this.editors = new ArrayList<PieceJustificatifEditor>();

        initWidget(uiBinder.createAndBindUi(this));

        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });
    }

    public void editPieces(List<PieceJustifProxy> data) {
        editors.clear();
        piecesPanel.clear();

        for (PieceJustifProxy piece : data) {
            PieceJustificatifEditor editor = pieceJustificatifFactory.create();
            editors.add(editor);
            editor.edit(piece);
        }
    }

    @UiHandler("validate")
    void onValidateClicked(ClickEvent event) {
        List<PieceJustifProxy> pieces = new ArrayList<PieceJustifProxy>();
        for (PieceJustificatifEditor editor : editors) {
            pieces.add(editor.get());
        }
        getUiHandlers().checkPieces(pieces);
    }

    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event) {
        hide();
    }
}
