package com.gsr.myschool.back.client.web.application.reception.renderer;

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

public class ReceptionActionCell extends AbstractCell<DossierProxy> {
    public interface Renderer extends UiRenderer {
        void render(SafeHtmlBuilder sb);

        void onBrowserEvent(ReceptionActionCell o, NativeEvent e, Element p);
    }

    private final Renderer uiRenderer;

    private Delegate<DossierProxy> receive;

    private DossierProxy selectedObject;

    @Inject
    public ReceptionActionCell(final Renderer uiRenderer,
            @Assisted("receive") Delegate<DossierProxy> receive) {
        super(BrowserEvents.CLICK);

        this.uiRenderer = uiRenderer;
        this.receive = receive;
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

    @UiHandler({"receive"})
    void onPreviewClicked(ClickEvent event) {
        receive.execute(selectedObject);
    }
}
