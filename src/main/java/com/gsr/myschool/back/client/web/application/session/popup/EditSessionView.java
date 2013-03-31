package com.gsr.myschool.back.client.web.application.session.popup;

import com.github.gwtbootstrap.client.ui.TextArea;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedPopupViewImplWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.SessionExamenProxy;
import com.gsr.myschool.common.client.resource.SharedResources;
import com.gsr.myschool.common.client.util.DateUtilsClient;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.client.widget.ModalHeader;
import com.gsr.myschool.common.client.widget.TimeInput;
import com.gsr.myschool.common.client.widget.ValidationErrorPopup;
import com.gsr.myschool.common.shared.constants.GlobalParameters;

import static com.google.gwt.query.client.GQuery.$;

public class EditSessionView extends ValidatedPopupViewImplWithUiHandlers<EditSessionUiHandlers>
        implements EditSessionPresenter.MyView, EditorView<SessionExamenProxy> {
    public interface Binder extends UiBinder<Widget, EditSessionView> {
    }

    public interface Driver extends SimpleBeanEditorDriver<SessionExamenProxy, EditSessionView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;
    @UiField
    TextBox nom;
    @UiField
    DateBox dateSession;
    @UiField(provided = true)
    TimeInput welcomKids;
    @UiField(provided = true)
    TimeInput debutTest;
    @UiField(provided = true)
    TimeInput gatherKids;
    @UiField
    TextBox telephone;
    @UiField
    TextArea adresse;

    private final Driver driver;

    @Inject
    public EditSessionView(final EventBus eventBus, final Binder uiBinder,
                           final TimeInput welcomKids, final TimeInput debutTest,
                           final TimeInput gatherKids, final ModalHeader modalHeader,
                           final UiHandlersStrategy<EditSessionUiHandlers> uiHandlers,
                           final Driver driver, final ValidationErrorPopup errorPopup, 
                           final SharedResources sharedResources) {
        super(eventBus, errorPopup, uiHandlers);

        this.driver = driver;
        this.modalHeader = modalHeader;
        this.welcomKids = welcomKids;
        this.debutTest = debutTest;
        this.gatherKids = gatherKids;

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);

        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                hide();
            }
        });

        dateSession.getDatePicker().setStyleName(sharedResources.datePickerStyle().gwtDatePicker());
        dateSession.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat(GlobalParameters.DATE_FORMAT)));

        $(nom).id("nom");
        $(dateSession).id("dateSession");
        $(welcomKids).id("welcomKids");
        $(debutTest).id("debutTest");
        $(gatherKids).id("gatherKids");
        $(telephone).id("telephone");
        $(adresse).id("adresse");
    }

    @Override
    public void edit(SessionExamenProxy object) {
        nom.setFocus(true);
        driver.edit(object);
    }

    @Override
    public SessionExamenProxy get() {
        SessionExamenProxy session = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            if (session.getDateSession() != null) {
                session.setDateSession(DateUtilsClient.correctDate(session.getDateSession()));
            }

            return session;
        }
    }

    @UiHandler("save")
    void onSaveClicked(ClickEvent event) {
        getUiHandlers().saveSession(get());
    }

    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event) {
        hide();
    }
}
