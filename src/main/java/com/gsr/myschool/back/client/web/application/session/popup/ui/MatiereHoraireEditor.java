package com.gsr.myschool.back.client.web.application.session.popup.ui;

import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.SessionNiveauEtudeProxy;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.client.widget.TimeInput;

public class MatiereHoraireEditor extends Composite implements EditorView<SessionNiveauEtudeProxy> {
    public interface Binder extends UiBinder<Widget, MatiereHoraireEditor> {
    }

    public interface Driver extends SimpleBeanEditorDriver<SessionNiveauEtudeProxy, MatiereHoraireEditor> {
    }

    @UiField
    Label matiere;
    @UiField(provided = true)
    TimeInput horaireDe;
    @UiField(provided = true)
    TimeInput horaireA;

    private final Driver driver;

    @Inject
    public MatiereHoraireEditor(final Binder uiBinder, final Driver driver,
                                final TimeInput horaireDe, final TimeInput horaireA) {
        this.driver = driver;
        this.horaireDe = horaireDe;
        this.horaireA = horaireA;

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);
    }

    public void setEnabled(Boolean enabled) {
        horaireDe.setEnabled(enabled);
        horaireA.setEnabled(enabled);
    }

    @Override
    public void edit(SessionNiveauEtudeProxy object) {
        driver.edit(object);
    }

    @Override
    public SessionNiveauEtudeProxy get() {
        SessionNiveauEtudeProxy horaire = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            return horaire;
        }
    }
}