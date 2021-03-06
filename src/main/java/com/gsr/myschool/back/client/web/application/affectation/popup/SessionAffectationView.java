package com.gsr.myschool.back.client.web.application.affectation.popup;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.CellTable;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.resource.style.CellTableStyle;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.SessionExamenProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.EmptyResult;
import com.gsr.myschool.common.client.widget.ModalHeader;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

import java.util.List;

public class SessionAffectationView extends PopupViewWithUiHandlers<SessionAffectationUiHandlers>
        implements SessionAffectationPresenter.MyView {
    public interface Binder extends UiBinder<Widget, SessionAffectationView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;
    @UiField(provided = true)
    CellTable<SessionExamenProxy> etablissementTable;
    @UiField
    Button choose;
    @UiField
    Label subtitle;

    private final ListDataProvider<SessionExamenProxy> dataProvider;
    private final SingleSelectionModel<SessionExamenProxy> selectionModel;

    private SessionExamenProxy selectedValue;
    private final DateTimeFormat dateFormat;

    @Inject
    public SessionAffectationView(final EventBus eventBus, final Binder uiBinder,
                                  final ModalHeader modalHeader,
                                  final SharedMessageBundle messageBundle,
                                  final CellTableStyle cellTableStyle) {
        super(eventBus);

        this.modalHeader = modalHeader;
        this.dataProvider = new ListDataProvider<SessionExamenProxy>();
        this.selectionModel = new SingleSelectionModel<SessionExamenProxy>();
        this.etablissementTable = new CellTable<SessionExamenProxy>(10, cellTableStyle);
        this.dateFormat = DateTimeFormat.getFormat(GlobalParameters.DATE_FORMAT);

        initWidget(uiBinder.createAndBindUi(this));
        initCellTable();

        dataProvider.addDataDisplay(etablissementTable);
        etablissementTable.setEmptyTableWidget(new EmptyResult(messageBundle.noResultFound(), AlertType.WARNING));
        etablissementTable.setSelectionModel(selectionModel);

        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });

        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                selectedValue = selectionModel.getSelectedObject();
                choose.setEnabled(true);
            }
        });
    }

    @Override
    public void setData(List<SessionExamenProxy> data, DossierProxy currentDossier) {
        dataProvider.getList().clear();
        dataProvider.getList().addAll(data);
        etablissementTable.setPageSize(data.size());
        center();

        if (currentDossier != null && currentDossier.getNiveauEtude() != null) {
            modalHeader.setText("Dossier N° " + currentDossier.getGeneratedNumDossier());
            subtitle.setText("Liste des sessions ouvertes pour le Niveau d'étude "
                    + currentDossier.getNiveauEtude().getNom());
        }
    }

    @UiHandler("choose")
    void onChooseClicked(ClickEvent event) {
        if (selectedValue != null) {
            getUiHandlers().valueSelected(selectedValue);
        }
    }

    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event) {
        hide();
    }

    private void initCellTable() {
        TextColumn<SessionExamenProxy> nomColumn = new TextColumn<SessionExamenProxy>() {
            @Override
            public String getValue(SessionExamenProxy object) {
                return object.getNom();
            }
        };
        nomColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        etablissementTable.addColumn(nomColumn, "Session");
        etablissementTable.setColumnWidth(nomColumn, 25, Style.Unit.PCT);

        TextColumn<SessionExamenProxy> dateColumn = new TextColumn<SessionExamenProxy>() {
            @Override
            public String getValue(SessionExamenProxy object) {
                if (object.getDateSession() == null) return "";
                return dateFormat.format(object.getDateSession());
            }
        };
        dateColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        etablissementTable.addColumn(dateColumn, "Date session");
        etablissementTable.setColumnWidth(dateColumn, 25, Style.Unit.PCT);

        TextColumn<SessionExamenProxy> adressColumn = new TextColumn<SessionExamenProxy>() {
            @Override
            public String getValue(SessionExamenProxy object) {
                return object.getAdresse();
            }
        };
        adressColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        etablissementTable.addColumn(adressColumn, "Adresse");
        etablissementTable.setColumnWidth(adressColumn, 30, Style.Unit.PCT);

        TextColumn<SessionExamenProxy> numberColumn = new TextColumn<SessionExamenProxy>() {
            @Override
            public String getValue(SessionExamenProxy object) {
                if (object.getCandidates() == null) return "";
                return object.getCandidates().toString();
            }
        };
        adressColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        etablissementTable.addColumn(numberColumn, "Nombre de candidats affectés");
        etablissementTable.setColumnWidth(numberColumn, 20, Style.Unit.PCT);
    }
}
