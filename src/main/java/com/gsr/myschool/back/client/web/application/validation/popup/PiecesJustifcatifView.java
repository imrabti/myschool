package com.gsr.myschool.back.client.web.application.validation.popup;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.web.application.validation.popup.ui.PieceJustificatifEditor;
import com.gsr.myschool.back.client.web.application.validation.popup.ui.PieceJustificatifEditorFactory;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.PiecejustifDTOProxy;
import com.gsr.myschool.common.client.resource.style.DetailsListStyle;
import com.gsr.myschool.common.client.widget.ModalHeader;
import com.gsr.myschool.common.client.widget.renderer.LabelValueCell;
import com.gsr.myschool.common.shared.dto.LabelValue;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

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
    @UiField(provided = true)
    CellList<LabelValue> candidatDetails;

    private final PieceJustificatifEditorFactory pieceJustificatifFactory;
    private final List<PieceJustificatifEditor> editors;
    private final ListDataProvider<LabelValue> dataProvider;

    @Inject
    public PiecesJustifcatifView(final Binder uiBinder, final EventBus eventBus,
                                 final ModalHeader modalHeader,
                                 final DetailsListStyle listStyle,
                                 final LabelValueCell labelValueCell,
                                 final PieceJustificatifEditorFactory pieceJustificatifFactory) {
        super(eventBus);

        this.modalHeader = modalHeader;
        this.pieceJustificatifFactory = pieceJustificatifFactory;
        this.editors = new ArrayList<PieceJustificatifEditor>();
        this.dataProvider = new ListDataProvider<LabelValue>();
        this.candidatDetails = new CellList(labelValueCell, listStyle);

        initWidget(uiBinder.createAndBindUi(this));

        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });

        dataProvider.addDataDisplay(candidatDetails);
    }

    @Override
    public void displayDossierDetails(DossierProxy dossier, List<LabelValue> details) {
        modalHeader.setText("Dossier N° " + dossier.getGeneratedNumDossier());
        dataProvider.getList().clear();
        dataProvider.getList().addAll(details);
    }

    @Override
    public void editPieces(List<PiecejustifDTOProxy> data) {
        editors.clear();
        piecesPanel.clear();

        for (PiecejustifDTOProxy piece : data) {
            PieceJustificatifEditor editor = pieceJustificatifFactory.create();
            editors.add(editor);
            piecesPanel.add(editor);
            editor.edit(piece);
        }
    }

    @UiHandler("validate")
    void onValidateClicked(ClickEvent event) {
        List<PiecejustifDTOProxy> pieces = new ArrayList<PiecejustifDTOProxy>();
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
