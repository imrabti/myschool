package com.gsr.myschool.back.client.web.application.preinscription.renderer;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiRenderer;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.shared.type.DossierStatus;

public class PreInscriptionActionCell extends AbstractCell<DossierProxy> {
    @UiTemplate("PreInscriptionActionCellCreated.ui.xml")
    public interface RendererCreatedDossier extends UiRenderer {
        void render(SafeHtmlBuilder sb);

        void onBrowserEvent(PreInscriptionActionCell o, NativeEvent e, Element p);
    }

    @UiTemplate("PreInscriptionActionCellOther.ui.xml")
    public interface RendererOtherDossier extends UiRenderer {
        void render(SafeHtmlBuilder sb);

        void onBrowserEvent(PreInscriptionActionCell o, NativeEvent e, Element p);
    }

    @UiTemplate("PreInscriptionActionCellInvited.ui.xml")
    public interface RendererInvitedDossier extends UiRenderer {
        void render(SafeHtmlBuilder sb);

        void onBrowserEvent(PreInscriptionActionCell o, NativeEvent e, Element p);
    }

    private final RendererOtherDossier rendererOtherDossier;
    private final RendererCreatedDossier rendererCreatedDossier;
    private final RendererInvitedDossier renderInvitedDossier;

    private Delegate<DossierProxy> viewDetails;
    private Delegate<DossierProxy> print;
    private Delegate<DossierProxy> delete;
    private Delegate<DossierProxy> printConvocation;
    private Delegate<DossierProxy> sendConvocation;

    private DossierProxy selectedObject;

    @Inject
    public PreInscriptionActionCell(final RendererOtherDossier rendererOtherDossier,
                                    final RendererCreatedDossier rendererCreatedDossier,
                                    final RendererInvitedDossier renderInvitedDossier,
                                    @Assisted("viewDetails") Delegate<DossierProxy> viewDetails,
                                    @Assisted("print") Delegate<DossierProxy> print,
                                    @Assisted("delete") Delegate<DossierProxy> delete,
                                    @Assisted("printConvocation") Delegate<DossierProxy> printConvocation,
                                    @Assisted("sendConvocation") Delegate<DossierProxy> sendConvocation) {
        super(BrowserEvents.CLICK);

        this.rendererCreatedDossier = rendererCreatedDossier;
        this.rendererOtherDossier = rendererOtherDossier;
        this.renderInvitedDossier = renderInvitedDossier;
        this.viewDetails = viewDetails;
        this.print = print;
        this.delete = delete;
        this.printConvocation = printConvocation;
        this.sendConvocation = sendConvocation;
    }

    @Override
    public void onBrowserEvent(Context context, Element parent, DossierProxy value, NativeEvent event,
                               ValueUpdater<DossierProxy> valueUpdater) {
        selectedObject = value;
        switch (selectedObject.getStatus()) {
            case CREATED:
                rendererCreatedDossier.onBrowserEvent(this, event, parent);
                break;
            case SUBMITTED:
                rendererCreatedDossier.onBrowserEvent(this, event, parent);
                break;
            case INVITED_TO_TEST:
                renderInvitedDossier.onBrowserEvent(this, event, parent);
            default:
                rendererOtherDossier.onBrowserEvent(this, event, parent);
                break;
        }
    }

    @Override
    public void render(Context context, DossierProxy value, SafeHtmlBuilder builder) {
        switch (value.getStatus()) {
            case CREATED:
                rendererCreatedDossier.render(builder);
                break;
            case SUBMITTED:
                rendererOtherDossier.render(builder);
                break;
            case INVITED_TO_TEST:
                renderInvitedDossier.render(builder);
                break;
            default:
                rendererOtherDossier.render(builder);
                break;
        }
    }

    @UiHandler({"viewDetails"})
    void onPreviewClicked(ClickEvent event) {
        viewDetails.execute(selectedObject);
    }

    @UiHandler({"print"})
    void onPrintClicked(ClickEvent event) {
        if (!selectedObject.getStatus().equals(DossierStatus.CREATED)) {
            print.execute(selectedObject);
        }
    }

    @UiHandler({"delete"})
    void onDeleteClicked(ClickEvent event) {
        delete.execute(selectedObject);
    }

    @UiHandler({"printConvocation"})
    void onPrintconvocationClicked(ClickEvent event) {
        printConvocation.execute(selectedObject);
    }

    @UiHandler({"sendConvocation"})
    void onSendconvocationClicked(ClickEvent event) {
        sendConvocation.execute(selectedObject);
    }
}
