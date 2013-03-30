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
import com.gsr.myschool.common.shared.dto.NiveauEtudeNode;

public class NiveauEtudeNodeCell extends AbstractCell<NiveauEtudeNode> {
    @UiTemplate("NiveauEtudeNodeRenderer.ui.xml")
    public interface NiveauEtudeNodeRenderer extends UiRenderer {
        void render(SafeHtmlBuilder sb, String name, String warning);

        void onBrowserEvent(NiveauEtudeNodeCell o, NativeEvent e, Element p);
    }

    @UiTemplate("NiveauEtudeNodeReadOnlyRenderer.ui.xml")
    public interface NiveauEtudeNodeReadOnlyRenderer extends UiRenderer {
        void render(SafeHtmlBuilder sb, String name, String warning);

        void onBrowserEvent(NiveauEtudeNodeCell o, NativeEvent e, Element p);
    }

    private static final String WARNING = "icon-warning-sign";

    private final NiveauEtudeNodeRenderer uiRenderer;
    private final NiveauEtudeNodeReadOnlyRenderer uiRendererReadOnly;

    private Delegate<NiveauEtudeNode> detail;
    private Delegate<NiveauEtudeNode> delete;
    private Boolean readOnly;

    private NiveauEtudeNode selectedObject;

    @Inject
    public NiveauEtudeNodeCell(final NiveauEtudeNodeRenderer uiRenderer,
                               final NiveauEtudeNodeReadOnlyRenderer uiRendererReadOnly,
                               @Assisted("readyOnly") Boolean readOnly,
                               @Assisted("detail") Delegate<NiveauEtudeNode> detail,
                               @Assisted("delete") Delegate<NiveauEtudeNode> delete) {
        super(BrowserEvents.CLICK);

        this.uiRenderer = uiRenderer;
        this.uiRendererReadOnly = uiRendererReadOnly;
        this.readOnly = readOnly;
        this.detail = detail;
        this.delete = delete;
    }

    @Override
    public void onBrowserEvent(Context context, Element parent, NiveauEtudeNode value, NativeEvent event,
                               ValueUpdater<NiveauEtudeNode> valueUpdater) {
        selectedObject = value;
        if (readOnly) {
            uiRendererReadOnly.onBrowserEvent(this, event, parent);
        } else {
            uiRenderer.onBrowserEvent(this, event, parent);
        }
    }

    @Override
    public void render(Context context, NiveauEtudeNode value, SafeHtmlBuilder builder) {
        if (readOnly) {
            uiRendererReadOnly.render(builder, value.getName(), value.getComplete() ? "" : WARNING);
        } else {
            uiRenderer.render(builder, value.getName(), value.getComplete() ? "" : WARNING);
        }
    }

    @UiHandler({"detail"})
    void onDetailClicked(ClickEvent event) {
        detail.execute(selectedObject);
    }

    @UiHandler({"delete"})
    void onDeleteClicked(ClickEvent event) {
        delete.execute(selectedObject);
    }
}
