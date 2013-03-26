package com.gsr.myschool.back.client.web.application.session.renderer;

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
import com.gsr.myschool.common.client.proxy.SessionExamenProxy;

public class SessionActionCell extends AbstractCell<SessionExamenProxy> {
    @UiTemplate("SessionActionCellOpen.ui.xml")
    public interface RendererOpen extends UiRenderer {
        void render(SafeHtmlBuilder sb);

        void onBrowserEvent(SessionActionCell o, NativeEvent e, Element p);
    }

    @UiTemplate("SessionActionCellClosed.ui.xml")
    public interface RendererClosed extends UiRenderer {
        void render(SafeHtmlBuilder sb);

        void onBrowserEvent(SessionActionCell o, NativeEvent e, Element p);
    }

    @UiTemplate("SessionActionCellCanceled.ui.xml")
    public interface RendererCanceled extends UiRenderer {
        void render(SafeHtmlBuilder sb);

        void onBrowserEvent(SessionActionCell o, NativeEvent e, Element p);
    }

    private final RendererOpen uiRendererOpen;
    private final RendererClosed uiRendererClosed;
    private final RendererCanceled uiRendererCanceled;

    private Delegate<SessionExamenProxy> update;
    private Delegate<SessionExamenProxy> close;
    private Delegate<SessionExamenProxy> cancel;
    private Delegate<SessionExamenProxy> remove;

    private SessionExamenProxy selectedObject;

    @Inject
    public SessionActionCell(final RendererOpen uiRendererOpen,
                             final RendererClosed uiRendererClosed,
                             final RendererCanceled uiRendererCanceled,
                             @Assisted("update") Delegate<SessionExamenProxy> update,
                             @Assisted("close")  Delegate<SessionExamenProxy> close,
                             @Assisted("cancel") Delegate<SessionExamenProxy> cancel,
                             @Assisted("remove") Delegate<SessionExamenProxy> remove) {
        super(BrowserEvents.CLICK);

        this.uiRendererOpen = uiRendererOpen;
        this.uiRendererClosed = uiRendererClosed;
        this.uiRendererCanceled = uiRendererCanceled;
        this.update = update;
        this.close = close;
        this.cancel = cancel;
        this.remove = remove;
    }

    @Override
    public void onBrowserEvent(Context context, Element parent, SessionExamenProxy value, NativeEvent event,
                               ValueUpdater<SessionExamenProxy> valueUpdater) {
        selectedObject = value;

        switch (selectedObject.getStatus()) {
            case OPEN:
                uiRendererOpen.onBrowserEvent(this, event, parent);
                break;
            case CANCELED:
                uiRendererCanceled.onBrowserEvent(this, event, parent);
                break;
            case CLOSED:
                uiRendererClosed.onBrowserEvent(this, event, parent);
                break;
        }
    }

    @Override
    public void render(Context context, SessionExamenProxy value, SafeHtmlBuilder builder) {
        switch (value.getStatus()) {
            case OPEN:
                uiRendererOpen.render(builder);
                break;
            case CANCELED:
                uiRendererCanceled.render(builder);
                break;
            case CLOSED:
                uiRendererClosed.render(builder);
                break;
        }
    }

    @UiHandler({"update"})
    void onUpdateClicked(ClickEvent event) {
        update.execute(selectedObject);
    }

    @UiHandler({"close"})
    void onCloseClicked(ClickEvent event) {
        close.execute(selectedObject);
    }

    @UiHandler({"cancel"})
    void onCancelClicked(ClickEvent event) {
        cancel.execute(selectedObject);
    }

    @UiHandler({"remove"})
    void onRemoveClicked(ClickEvent event) {
        remove.execute(selectedObject);
    }
}
