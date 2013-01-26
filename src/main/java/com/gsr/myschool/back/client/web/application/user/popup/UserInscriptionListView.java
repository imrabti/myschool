package com.gsr.myschool.back.client.web.application.user.popup;

import com.github.gwtbootstrap.client.ui.CellTable;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedPopupViewImplWithUiHandlers;
import com.gsr.myschool.common.client.mvp.ValidationErrorPopup;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.widget.ModalHeader;

import java.util.List;

public class UserInscriptionListView extends ValidatedPopupViewImplWithUiHandlers<UserInscriptionListUiHandlers>
        implements UserInscriptionListPresenter.MyView {
    public interface Binder extends UiBinder<PopupPanel, UserInscriptionListView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;
    @UiField
    CellTable<DossierProxy> userInscriptionsTable;

    private final DateTimeFormat dateFormat;
    private final ListDataProvider<DossierProxy> dataProvider;

    @Inject
    public UserInscriptionListView(final EventBus eventBus, final Binder uiBinder,
            final UiHandlersStrategy<UserInscriptionListUiHandlers> uiHandlers,
            final ValidationErrorPopup errorPopup, final ModalHeader modalHeader) {
        super(eventBus, errorPopup, uiHandlers);

        this.modalHeader = modalHeader;
        initWidget(uiBinder.createAndBindUi(this));

        initDataGrid();
        dataProvider = new ListDataProvider<DossierProxy>();
        dataProvider.addDataDisplay(userInscriptionsTable);
        dateFormat = DateTimeFormat.getFormat("LLL d yyyy");

        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                hide();
            }
        });
    }

    @Override
    public void setData(List<DossierProxy> data) {
        dataProvider.getList().clear();
        dataProvider.getList().addAll(data);
    }

    private void initDataGrid() {
        TextColumn<DossierProxy> refColumn = new TextColumn<DossierProxy>() {
            @Override
            public String getValue(DossierProxy object) {
                return object.getGeneratedNumDossier();
            }
        };
        refColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        userInscriptionsTable.addColumn(refColumn, "N° Dossier");
        userInscriptionsTable.setColumnWidth(refColumn, 20, Style.Unit.PCT);

        TextColumn<DossierProxy> cNameColumn = new TextColumn<DossierProxy>() {
            @Override
            public String getValue(DossierProxy object) {
                return object.getCandidat().getFirstname();
            }
        };
        refColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        userInscriptionsTable.addColumn(cNameColumn, "Nom Candidat");
        userInscriptionsTable.setColumnWidth(cNameColumn, 20, Style.Unit.PCT);

        TextColumn<DossierProxy> cBirthColumn = new TextColumn<DossierProxy>() {
            @Override
            public String getValue(DossierProxy object) {
                return dateFormat.format(object.getCandidat().getBirthDate());
            }
        };
        cBirthColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        userInscriptionsTable.addColumn(cBirthColumn, "Naissance Candidat");
        userInscriptionsTable.setColumnWidth(cBirthColumn, 30, Style.Unit.PCT);

        TextColumn<DossierProxy> statusColumn = new TextColumn<DossierProxy>() {
            @Override
            public String getValue(DossierProxy object) {
                return object.getStatus().name();
            }
        };
        statusColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        userInscriptionsTable.addColumn(statusColumn, "Status");
        userInscriptionsTable.setColumnWidth(statusColumn, 30, Style.Unit.PCT);

        TextColumn<DossierProxy> submittedColumn = new TextColumn<DossierProxy>() {
            @Override
            public String getValue(DossierProxy object) {
                return dateFormat.format(object.getCreateDate());
            }
        };
        submittedColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        userInscriptionsTable.addColumn(submittedColumn, "Date de soumission");
        userInscriptionsTable.setColumnWidth(submittedColumn, 30, Style.Unit.PCT);
    }
}
