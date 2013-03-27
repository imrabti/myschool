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
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.shared.type.DossierStatus;

public class PreInscriptionActionCell extends AbstractCell<DossierProxy> {
    public interface Renderer extends UiRenderer {
        void render(SafeHtmlBuilder sb);

        void onBrowserEvent(PreInscriptionActionCell o, NativeEvent e, Element p);
    }

    private final Renderer uiRenderer;

    private Delegate<DossierProxy> viewDetails;
    private Delegate<DossierProxy> print;

    private DossierProxy selectedObject;

    @Inject
    public PreInscriptionActionCell(final Renderer uiRenderer,
            @Assisted("viewDetails") Delegate<DossierProxy> viewDetails,
            @Assisted("print") Delegate<DossierProxy> print) {
        super(BrowserEvents.CLICK);

        this.uiRenderer = uiRenderer;
        this.viewDetails = viewDetails;
        this.print = print;
    }

    @Override
    public void onBrowserEvent(Context context, Element parent, DossierProxy value, NativeEvent event,
                               ValueUpdater<DossierProxy> valueUpdater) {
        selectedObject = value;
        uiRenderer.onBrowserEvent(this, event, parent);
    }

    @Override
    public void render(Context context, DossierProxy value, SafeHtmlBuilder builder) {
        uiRenderer.render(builder);
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
}
