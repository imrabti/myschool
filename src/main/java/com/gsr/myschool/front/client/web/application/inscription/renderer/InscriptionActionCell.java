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
import com.gsr.myschool.common.client.security.SecurityUtils;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.common.shared.type.Authority;

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

    @UiTemplate("InscriptionActionCellInscriptionClosed.ui.xml")
    public interface RendererInscriptionClosed extends UiRenderer {
        void render(SafeHtmlBuilder sb);

        void onBrowserEvent(InscriptionActionCell o, NativeEvent e, Element p);
    }

    @UiTemplate("InscriptionActionCellSU.ui.xml")
    public interface RendererSU extends UiRenderer {
        void render(SafeHtmlBuilder sb);

        void onBrowserEvent(InscriptionActionCell o, NativeEvent e, Element p);
    }

    @UiTemplate("InscriptionActionCellSUOther.ui.xml")
    public interface RendererSUOther extends UiRenderer {
        void render(SafeHtmlBuilder sb);

        void onBrowserEvent(InscriptionActionCell o, NativeEvent e, Element p);
    }

    private final RendererCreated uiRendererCreated;
    private final RendererSubmitted uiRendererSubmitted;
    private final RendererOther uiRendererOther;
    private final RendererInscriptionClosed rendererInscriptionClosed;
    private final RendererSU uiRendererSU;
    private final RendererSUOther uiRendererSUOther;

    private final SecurityUtils securityUtils;

    private Delegate<DossierProxy> preview;
    private Delegate<DossierProxy> edit;
    private Delegate<DossierProxy> delete;
    private Delegate<DossierProxy> submit;
    private Delegate<DossierProxy> print;

    private DossierProxy selectedObject;
    private Boolean inscriptionOpened = true;
    private Boolean filieresGeneralesOpened = true;

    @Inject
    public InscriptionActionCell(final RendererCreated uiRendererCreated,
                                 final RendererSubmitted uiRendererSubmitted,
                                 final RendererOther uiRendererOther,
                                 final RendererInscriptionClosed rendererInscriptionClosed,
                                 final RendererSU uiRendererSU,
                                 final RendererSUOther uiRendererSUOther,
                                 final SecurityUtils securityUtils,
                                 @Assisted("preview") Delegate<DossierProxy> preview,
                                 @Assisted("edit") Delegate<DossierProxy> edit,
                                 @Assisted("delete") Delegate<DossierProxy> delete,
                                 @Assisted("submit") Delegate<DossierProxy> submit,
                                 @Assisted("print") Delegate<DossierProxy> print) {
        super(BrowserEvents.CLICK);

        this.uiRendererCreated = uiRendererCreated;
        this.uiRendererSubmitted = uiRendererSubmitted;
        this.uiRendererOther = uiRendererOther;
        this.rendererInscriptionClosed = rendererInscriptionClosed;
        this.uiRendererSU = uiRendererSU;
        this.uiRendererSUOther = uiRendererSUOther;
        this.securityUtils = securityUtils;
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

        if (securityUtils.isSuperUser()) {
            switch (value.getStatus()) {
                case CREATED:
                    uiRendererSU.onBrowserEvent(this, event, parent);
                    break;
                default:
                    uiRendererSUOther.onBrowserEvent(this, event, parent);
                    break;
            }
        } else if (securityUtils.hasAuthority(Authority.ROLE_USER_VIP.name())) {
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
        } else {
            switch (selectedObject.getStatus()) {
                case CREATED:
                    if (filieresGeneralesOpened && inscriptionOpened ||
                            !filieresGeneralesOpened
                                    && inscriptionOpened
                                    && value.getNiveauEtude() != null
                                    && value.getNiveauEtude().getFiliere() != null
                                    && value.getNiveauEtude().getFiliere().getId() >= GlobalParameters.PREPA_FILIERE_FROM
                            || !filieresGeneralesOpened
                            && inscriptionOpened
                            && value.getNiveauEtude() == null) {
                        uiRendererCreated.onBrowserEvent(this, event, parent);
                        break;
                    } else {
                        rendererInscriptionClosed.onBrowserEvent(this, event, parent);
                        break;
                    }
                case SUBMITTED:
                    uiRendererSubmitted.onBrowserEvent(this, event, parent);
                    break;
                default:
                    uiRendererOther.onBrowserEvent(this, event, parent);
                    break;
            }
        }
    }

    @Override
    public void render(Context context, DossierProxy value, SafeHtmlBuilder builder) {
        if (securityUtils.isSuperUser()) {
            switch (value.getStatus()) {
                case CREATED:
                    uiRendererSU.render(builder);
                    break;
                default:
                    uiRendererSUOther.render(builder);
                    break;
            }
        } else if (securityUtils.hasAuthority(Authority.ROLE_USER_VIP.name())) {
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
        } else {
            switch (value.getStatus()) {
                case CREATED:
                    if (filieresGeneralesOpened && inscriptionOpened ||
                            !filieresGeneralesOpened
                                    && inscriptionOpened
                                    && value.getNiveauEtude() != null
                                    && value.getNiveauEtude().getFiliere() != null
                                    && value.getNiveauEtude().getFiliere().getId() >= GlobalParameters.PREPA_FILIERE_FROM
                            || !filieresGeneralesOpened
                            && inscriptionOpened
                            && value.getNiveauEtude() == null) {
                        uiRendererCreated.render(builder);
                        break;
                    } else {
                        rendererInscriptionClosed.render(builder);
                        break;
                    }
                case SUBMITTED:
                    uiRendererSubmitted.render(builder);
                    break;
                default:
                    uiRendererOther.render(builder);
                    break;
            }
        }
    }

    public void setInscriptionOpened(Boolean opened) {
        this.inscriptionOpened = opened;
    }

    public void setFilieresGeneralesOpened(Boolean opened) {
        this.filieresGeneralesOpened = opened;
    }

    @UiHandler({"preview"})
    void onPreviewClicked(ClickEvent event) {
        preview.execute(selectedObject);
    }

    @UiHandler({"edit"})
    void onEditClicked(ClickEvent event) {
        DossierProxy value = selectedObject;
        if (securityUtils.isSuperUser() || securityUtils.hasAuthority(Authority.ROLE_USER_VIP.name())) {
            edit.execute(selectedObject);
        } else {
            if (filieresGeneralesOpened && inscriptionOpened ||
                    !filieresGeneralesOpened
                            && inscriptionOpened
                            && value.getNiveauEtude() != null
                            && value.getNiveauEtude().getFiliere() != null
                            && value.getNiveauEtude().getFiliere().getId() >= GlobalParameters.PREPA_FILIERE_FROM
                    || !filieresGeneralesOpened
                    && inscriptionOpened
                    && value.getNiveauEtude() == null) {
                edit.execute(selectedObject);
            }
        }
    }

    @UiHandler({"delete"})
    void onDeleteClicked(ClickEvent event) {
        DossierProxy value = selectedObject;
        if (securityUtils.isSuperUser() || securityUtils.hasAuthority(Authority.ROLE_USER_VIP.name())) {
            delete.execute(selectedObject);
        } else {
            if (filieresGeneralesOpened && inscriptionOpened ||
                    !filieresGeneralesOpened
                            && inscriptionOpened
                            && value.getNiveauEtude() != null
                            && value.getNiveauEtude().getFiliere() != null
                            && value.getNiveauEtude().getFiliere().getId() >= GlobalParameters.PREPA_FILIERE_FROM
                    || !filieresGeneralesOpened
                    && inscriptionOpened
                    && value.getNiveauEtude() == null) {
                delete.execute(selectedObject);
            }
        }
    }

    @UiHandler({"submit"})
    void onSubmitClicked(ClickEvent event) {
        DossierProxy value = selectedObject;
        if (securityUtils.isSuperUser() || securityUtils.hasAuthority(Authority.ROLE_USER_VIP.name())) {
            submit.execute(selectedObject);
        } else {
            if (filieresGeneralesOpened && inscriptionOpened ||
                    !filieresGeneralesOpened
                            && inscriptionOpened
                            && value.getNiveauEtude() != null
                            && value.getNiveauEtude().getFiliere() != null
                            && value.getNiveauEtude().getFiliere().getId() >= GlobalParameters.PREPA_FILIERE_FROM
                    || !filieresGeneralesOpened
                    && inscriptionOpened
                    && value.getNiveauEtude() == null) {
                submit.execute(selectedObject);
            }
        }
    }

    @UiHandler({"print"})
    void onPrintClicked(ClickEvent event) {
        print.execute(selectedObject);
    }
}
