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
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.gsr.myschool.common.client.proxy.DossierProxy;

public class InscriptionActionCell extends AbstractCell<DossierProxy> {
    @UiTemplate("InscriptionActionCellCreated.ui.xml")
    public interface RendererCreated extends UiRenderer {
        void render(SafeHtmlBuilder sb);

        void onBrowserEvent(InscriptionActionCell o, NativeEvent e, Element p);
    }

    @UiTemplate("InscriptionActionCellSubmitted.ui.xml")
    public interface RendererSubmitted extends UiRenderer {
        void render(SafeHtmlBuilder sb);

        void onBrowserEvent(InscriptionActionCell o, NativeEvent e, Element p);
    }

    @UiTemplate("InscriptionActionCellOther.ui.xml")
    public interface RendererOther extends UiRenderer {
        void render(SafeHtmlBuilder sb);

        void onBrowserEvent(InscriptionActionCell o, NativeEvent e, Element p);
    }

    private final RendererCreated uiRendererCreated;
    private final RendererSubmitted uiRendererSubmitted;
    private final RendererOther uiRendererOther;

    private Delegate<DossierProxy> preview;
    private Delegate<DossierProxy> edit;
    private Delegate<DossierProxy> delete;
    private Delegate<DossierProxy> submit;
    private Delegate<DossierProxy> print;

    private DossierProxy selectedObject;

    @Inject
    public InscriptionActionCell(final RendererCreated uiRendererCreated,
                                 final RendererSubmitted uiRendererSubmitted,
                                 final RendererOther uiRendererOther,
                                 @Assisted("preview") Delegate<DossierProxy> preview,
                                 @Assisted("edit") Delegate<DossierProxy> edit,
                                 @Assisted("delete") Delegate<DossierProxy> delete,
                                 @Assisted("submit") Delegate<DossierProxy> submit,
                                 @Assisted("print") Delegate<DossierProxy> print) {
        super(BrowserEvents.CLICK);

        this.uiRendererCreated = uiRendererCreated;
        this.uiRendererSubmitted = uiRendererSubmitted;
        this.uiRendererOther = uiRendererOther;
        this.preview = preview;
        this.edit = edit;
        this.delete = delete;
        this.submit = submit;
        this.print = print;
    }

    @Override
    public void onBrowserEvent(Context context, Element parent, DossierProxy value, NativeEvent event,
                               ValueUpdater<DossierProxy> valueUpdater) {
        selectedObject = value;
        switch (selectedObject.getStatus()) {
            case CREATED:
                uiRendererCreated.onBrowserEvent(this, event, parent);
                break;
            case SUBMITTED:
                uiRendererSubmitted.onBrowserEvent(this, event, parent);
                break;
            default:
                uiRendererOther.onBrowserEvent(this, event, parent);
                break;
        }
    }

    @Override
    public void render(Context context, DossierProxy value, SafeHtmlBuilder builder) {
        switch (value.getStatus()) {
            case CREATED:
                uiRendererCreated.render(builder);
                break;
            case SUBMITTED:
                uiRendererSubmitted.render(builder);
                break;
            default:
                uiRendererOther.render(builder);
                break;
        }
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
