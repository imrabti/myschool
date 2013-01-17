package com.gsr.myschool.front.client.web.application.inscription.renderer;

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
import com.gsr.myschool.common.client.proxy.InscriptionProxy;

public class InscriptionActionCell extends AbstractCell<InscriptionProxy> {
    public interface Renderer extends UiRenderer {
        void render(SafeHtmlBuilder sb);

        void onBrowserEvent(InscriptionActionCell o, NativeEvent e, Element p);
    }

    private final Renderer uiRenderer;

    private Delegate<InscriptionProxy> preview;
    private Delegate<InscriptionProxy> edit;
    private Delegate<InscriptionProxy> delete;
    private Delegate<InscriptionProxy> submit;
    private Delegate<InscriptionProxy> print;

    private InscriptionProxy selectedObject;

    @Inject
    public InscriptionActionCell(final Renderer uiRenderer,
                                 @Assisted("preview") Delegate<InscriptionProxy> preview,
                                 @Assisted("edit") Delegate<InscriptionProxy> edit,
                                 @Assisted("delete") Delegate<InscriptionProxy> delete,
                                 @Assisted("submit") Delegate<InscriptionProxy> submit,
                                 @Assisted("print") Delegate<InscriptionProxy> print) {
        super(BrowserEvents.CLICK);

        this.uiRenderer = uiRenderer;
        this.preview = preview;
        this.edit = edit;
        this.delete = delete;
        this.submit = submit;
        this.print = print;
    }

    @Override
    public void onBrowserEvent(Context context, Element parent, InscriptionProxy value, NativeEvent event,
                               ValueUpdater<InscriptionProxy> valueUpdater) {
        selectedObject = value;
        uiRenderer.onBrowserEvent(this, event, parent);
    }

    @Override
    public void render(Context context, InscriptionProxy value, SafeHtmlBuilder builder) {
        uiRenderer.render(builder);
    }

    @UiHandler({"preview"})
    void onPreviewClicked(ClickEvent event) {
        preview.execute(selectedObject);
    }

    @UiHandler({"edit"})
    void onEditClicked(ClickEvent event) {
        edit.execute(selectedObject);
    }

    @UiHandler({"delete"})
    void onDeleteClicked(ClickEvent event) {
        delete.execute(selectedObject);
    }

    @UiHandler({"submit"})
    void onSubmitClicked(ClickEvent event) {
        submit.execute(selectedObject);
    }

    @UiHandler({"print"})
    void onPrintClicked(ClickEvent event) {
        print.execute(selectedObject);
    }
}
