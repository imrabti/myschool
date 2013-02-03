package com.gsr.myschool.back.client.web.application.preinscription.popup;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedPopupViewImplWithUiHandlers;
import com.gsr.myschool.common.client.mvp.ValidationErrorPopup;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.CandidatProxy;
import com.gsr.myschool.common.client.proxy.InfoParentProxy;
import com.gsr.myschool.common.client.ui.dossier.CandidatEditor;
import com.gsr.myschool.common.client.ui.dossier.NiveauScolaireEditor;
import com.gsr.myschool.common.client.ui.dossier.ParentEditor;
import com.gsr.myschool.common.client.widget.ModalHeader;

public class PreInscriptionDetailsView extends ValidatedPopupViewImplWithUiHandlers<PreInscriptionDetailsUiHandlers>
        implements PreInscriptionDetailsPresenter.MyView {
    public interface Binder extends UiBinder<PopupPanel, PreInscriptionDetailsView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;
    @UiField(provided = true)
    ParentEditor parentEditor;
    @UiField(provided = true)
    CandidatEditor candidatEditor;
    @UiField(provided = true)
    NiveauScolaireEditor niveauScolaireEditor;

    @Inject
    public PreInscriptionDetailsView(final EventBus eventBus, final Binder uiBinder,
            final UiHandlersStrategy<PreInscriptionDetailsUiHandlers> uiHandlers,
            final ValidationErrorPopup errorPopup, final ModalHeader modalHeader,
            final ParentEditor parentEditor, final CandidatEditor candidatEditor,
            final NiveauScolaireEditor niveauScolaireEditor) {
        super(eventBus, errorPopup, uiHandlers);

        this.modalHeader = modalHeader;
        this.parentEditor = parentEditor;
        this.candidatEditor = candidatEditor;
        this.niveauScolaireEditor = niveauScolaireEditor;

        initWidget(uiBinder.createAndBindUi(this));

        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                hide();
            }
        });
    }

    @Override
    public void editParent(InfoParentProxy infoParent) {
        parentEditor.edit(infoParent);
    }

    @Override
    public void editCandidat(CandidatProxy candidat) {
        candidatEditor.edit(candidat);
    }

    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event) {
        hide();
    }
}
