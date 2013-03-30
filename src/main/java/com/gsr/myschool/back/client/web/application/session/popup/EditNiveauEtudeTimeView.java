package com.gsr.myschool.back.client.web.application.session.popup;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.web.application.session.popup.ui.MatiereHoraireEditor;
import com.gsr.myschool.back.client.web.application.session.popup.ui.MatiereHoraireEditorFactory;
import com.gsr.myschool.common.client.mvp.PopupViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.SessionNiveauEtudeProxy;
import com.gsr.myschool.common.client.widget.ModalHeader;

import java.util.ArrayList;
import java.util.List;

public class EditNiveauEtudeTimeView extends PopupViewWithUiHandlers<EditNiveauEtudeTimeUiHandlers>
        implements EditNiveauEtudeTimePresenter.MyView {
    interface Binder extends UiBinder<Widget, EditNiveauEtudeTimeView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;
    @UiField
    HTMLPanel horairePanel;

    private final MatiereHoraireEditorFactory matiereHoraireEditorFactory;
    private final List<MatiereHoraireEditor> editors;

    @Inject
    public EditNiveauEtudeTimeView(final EventBus eventBus, final Binder uiBinder,
                                   final UiHandlersStrategy<EditNiveauEtudeTimeUiHandlers> uiHandlers,
                                   final MatiereHoraireEditorFactory matiereHoraireEditorFactory,
                                   final ModalHeader modalHeader) {
        super(eventBus, uiHandlers);

        this.modalHeader = modalHeader;
        this.matiereHoraireEditorFactory = matiereHoraireEditorFactory;
        this.editors = new ArrayList<MatiereHoraireEditor>();

        initWidget(uiBinder.createAndBindUi(this));

        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                hide();
            }
        });
    }

    @Override
    public void editHoraires(List<SessionNiveauEtudeProxy> data, Boolean enabled) {
        editors.clear();
        horairePanel.clear();

        for (SessionNiveauEtudeProxy horaire : data) {
            MatiereHoraireEditor editor = matiereHoraireEditorFactory.create();
            editors.add(editor);
            horairePanel.add(editor);
            editor.setEnabled(enabled);
            editor.edit(horaire);
        }
    }

    @UiHandler("save")
    void onSaveClicked(ClickEvent event) {
        List<SessionNiveauEtudeProxy> horaires = new ArrayList<SessionNiveauEtudeProxy>();
        for (MatiereHoraireEditor editor : editors) {
            horaires.add(editor.get());
        }
        getUiHandlers().saveHoraires(horaires);
    }

    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event) {
        hide();
    }
}
