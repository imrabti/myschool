package com.gsr.myschool.back.client.web.application.admission.renderer;

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

public class AdmissionActionCell extends AbstractCell<DossierProxy> {
    @UiTemplate("AdmissionDefaultActionCell.ui.xml")
    public interface RendererDefault extends UiRenderer {
        void render(SafeHtmlBuilder sb);

        void onBrowserEvent(AdmissionActionCell o, NativeEvent e, Element p);
    }

    @UiTemplate("AdmissionClosedActionCell.ui.xml")
    public interface RendererClosed extends UiRenderer {
        void render(SafeHtmlBuilder sb, String comment);

        void onBrowserEvent(AdmissionActionCell o, NativeEvent e, Element p);
    }

    private final RendererDefault rendererDefault;
    private final RendererClosed rendererClosed;

    private Delegate<DossierProxy> accept;
    private Delegate<DossierProxy> deny;
    private Delegate<DossierProxy> viewDetails;

    private DossierProxy selectedObject;

    @Inject
    public AdmissionActionCell(final RendererDefault rendererDefault,
            final RendererClosed rendererClosed,
            @Assisted("accept") Delegate<DossierProxy> accept,
            @Assisted("deny") Delegate<DossierProxy> deny,
            @Assisted("viewDetails") Delegate<DossierProxy> viewDetails) {
        super(BrowserEvents.CLICK);

        this.rendererDefault = rendererDefault;
        this.rendererClosed = rendererClosed;
        this.accept = accept;
        this.deny = deny;
        this.viewDetails = viewDetails;
    }

    @Override
    public void onBrowserEvent(Context context, Element parent, DossierProxy value, NativeEvent event,
                               ValueUpdater<DossierProxy> valueUpdater) {
        selectedObject = value;

        switch (selectedObject.getStatus()) {
            case INVITED_TO_TEST:
                rendererDefault.onBrowserEvent(this, event, parent);
                break;
            case TO_BE_REGISTERED:
                rendererClosed.onBrowserEvent(this, event, parent);
                break;
            case NOT_ADMITTED:
                rendererClosed.onBrowserEvent(this, event, parent);
                break;
            default:
                rendererDefault.onBrowserEvent(this, event, parent);
                break;
        }
    }

    @Override
    public void render(Context context, DossierProxy value, SafeHtmlBuilder builder) {
        switch (value.getStatus()) {
            case INVITED_TO_TEST:
                rendererDefault.render(builder);
                break;
            case TO_BE_REGISTERED:
                rendererClosed.render(builder, value.getCommentaire());
                break;
            case NOT_ADMITTED:
                rendererClosed.render(builder, value.getCommentaire());
                break;
            default:
                rendererDefault.render(builder);
                break;
        }
    }

    @UiHandler({"accept"})
    void onAcceptClicked(ClickEvent event) {
        accept.execute(selectedObject);
    }

    @UiHandler({"deny"})
    void onDenyClicked(ClickEvent event) {
        deny.execute(selectedObject);
    }

    @UiHandler({"viewDetails"})
    void onPreviewClicked(ClickEvent event) {
        viewDetails.execute(selectedObject);
    }
}
