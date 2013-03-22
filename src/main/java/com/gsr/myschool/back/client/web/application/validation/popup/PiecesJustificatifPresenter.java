package com.gsr.myschool.back.client.web.application.validation.popup;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.DossierServiceRequest;
import com.gsr.myschool.back.client.web.application.validation.event.VerificationCompletedEvent;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.PiecejustifDTOProxy;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.common.shared.dto.LabelValue;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gsr.myschool.back.client.web.application.validation.popup.PiecesJustificatifPresenter.MyView;

import java.util.ArrayList;
import java.util.List;

public class PiecesJustificatifPresenter extends PresenterWidget<MyView> implements PiecesJustificatifUiHandlers {
    public interface MyView extends PopupView, HasUiHandlers<PiecesJustificatifUiHandlers> {
        void displayDossierDetails(DossierProxy dossier, List<LabelValue> details);

        void editPieces(List<PiecejustifDTOProxy> data);
    }

    private final BackRequestFactory requestFactory;
    private final SharedMessageBundle messageBundle;
    private final DateTimeFormat dateFormat;

    private List<PiecejustifDTOProxy> pieces;
    private DossierProxy currentDossier;

    @Inject
    public PiecesJustificatifPresenter(final EventBus eventBus, final MyView view,
                                       final BackRequestFactory requestFactory,
                                       final SharedMessageBundle messageBundle) {
        super(eventBus, view);

        this.requestFactory = requestFactory;
        this.messageBundle = messageBundle;
        this.dateFormat = DateTimeFormat.getFormat(GlobalParameters.DATE_FORMAT);

        getView().setUiHandlers(this);
    }

    public void setCurrentDossier(DossierProxy currentDossier) {
        this.currentDossier = currentDossier;
    }

    @Override
    public void checkPieces(List<PiecejustifDTOProxy> pieces) {
        Long dossierId = currentDossier.getId();
        List<String> notChecked = new ArrayList<String>();
        for (PiecejustifDTOProxy piece : pieces) {
            if (!piece.getAvailable()) {
                notChecked.add(piece.getId() + "#" + piece.getMotif());
            }
        }

        requestFactory.dossierService().verify(dossierId, notChecked).fire(new ReceiverImpl<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                String messageString = response ? messageBundle.operationSuccess() : messageBundle.operationFailure();
                AlertType alertType = response ? AlertType.SUCCESS : AlertType.ERROR;
                Message message = new Message.Builder(messageString).style(alertType).build();
                MessageEvent.fire(this, message);

                if (response) {
                    getView().hide();
                    VerificationCompletedEvent.fire(this);
                }
            }
        });
    }

    @Override
    protected void onReveal() {
        List<LabelValue> candidatDetails = new ArrayList<LabelValue>();
        candidatDetails.add(new LabelValue("Nom prénom", currentDossier.getCandidat().getLastname() +
                " " + currentDossier.getCandidat().getFirstname()));
        candidatDetails.add(new LabelValue("Date de naissance", dateFormat.format(currentDossier.getCandidat().getBirthDate())));
        candidatDetails.add(new LabelValue("Filière", currentDossier.getFiliere().getNom()));
        candidatDetails.add(new LabelValue("Niveau demandé", currentDossier.getNiveauEtude().getNom()));
        getView().displayDossierDetails(currentDossier, candidatDetails);

        requestFactory.dossierService().getPieceJustifFromProcess(currentDossier)
                .fire(new ReceiverImpl<List<PiecejustifDTOProxy>>() {
                    @Override
                    public void onSuccess(List<PiecejustifDTOProxy> result) {
                        pieces = new ArrayList<PiecejustifDTOProxy>();
                        for (PiecejustifDTOProxy piece : result) {
                            DossierServiceRequest context = requestFactory.dossierService();
                            pieces.add(context.edit(piece));
                        }

                        getView().editPieces(pieces);
                    }
                });
    }
}
