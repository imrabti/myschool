package com.gsr.myschool.back.client.web.application.confirmationTest.renderer;

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

public class ConfirmationTestActionCell extends AbstractCell<DossierProxy> {
    @UiTemplate("ConfirmationTestDefaultActionCell.ui.xml")
    public interface RendererDefault extends UiRenderer {
        void render(SafeHtmlBuilder sb);

        void onBrowserEvent(ConfirmationTestActionCell o, NativeEvent e, Element p);
    }

    @UiTemplate("ConfirmationTestRejectedActionCell.ui.xml")
    public interface RendererRejected extends UiRenderer {
        void render(SafeHtmlBuilder sb, String reason);

        void onBrowserEvent(ConfirmationTestActionCell o, NativeEvent e, Element p);
    }

    private final RendererDefault rendererDefault;
    private final RendererRejected rendererRejected;

    private Delegate<DossierProxy> accept;
    private Delegate<DossierProxy> deny;
    private Delegate<DossierProxy> viewDetails;

    private DossierProxy selectedObject;

    @Inject
    public ConfirmationTestActionCell(final RendererDefault rendererDefault,
            final RendererRejected rendererRejected,
			@Assisted("accept") Delegate<DossierProxy> accept,
            @Assisted("deny") Delegate<DossierProxy> deny,
			@Assisted("viewDetails") Delegate<DossierProxy> viewDetails) {
        super(BrowserEvents.CLICK);

        this.rendererDefault = rendererDefault;
        this.rendererRejected = rendererRejected;
        this.accept = accept;
        this.deny = deny;
        this.viewDetails = viewDetails;
    }

    @Override
    public void onBrowserEvent(Context context, Element parent, DossierProxy value, NativeEvent event,
                               ValueUpdater<DossierProxy> valueUpdater) {
        selectedObject = value;

        switch (selectedObject.getStatus()) {
            case ACCEPTED_FOR_STUDY:
                rendererDefault.onBrowserEvent(this, event, parent);
                break;
            case NOT_ACCEPTED_FOR_TEST:
                rendererRejected.onBrowserEvent(this, event, parent);
                break;
            default:
                rendererDefault.onBrowserEvent(this, event, parent);
                break;
        }
    }

    @Override
    public void render(Context context, DossierProxy value, SafeHtmlBuilder builder) {
        switch (value.getStatus()) {
            case ACCEPTED_FOR_STUDY:
                rendererDefault.render(builder);
                break;
            case NOT_ACCEPTED_FOR_TEST:
                rendererRejected.render(builder, value.getMotifRefus());
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
