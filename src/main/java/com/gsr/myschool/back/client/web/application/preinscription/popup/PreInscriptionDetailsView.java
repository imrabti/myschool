package com.gsr.myschool.back.client.web.application.preinscription.popup;

import com.github.gwtbootstrap.client.ui.CellTable;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.mvp.ValidatedPopupViewImplWithUiHandlers;
import com.gsr.myschool.common.client.mvp.ValidationErrorPopup;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.CandidatProxy;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.InfoParentProxy;
import com.gsr.myschool.common.client.proxy.ScolariteAnterieurProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.ui.dossier.CandidatEditor;
import com.gsr.myschool.common.client.ui.dossier.NiveauScolaireEditor;
import com.gsr.myschool.common.client.ui.dossier.ParentEditor;
import com.gsr.myschool.common.client.widget.EmptyResult;
import com.gsr.myschool.common.client.widget.ModalHeader;

import java.util.List;

public class PreInscriptionDetailsView extends ValidatedPopupViewImplWithUiHandlers<PreInscriptionDetailsUiHandlers>
        implements PreInscriptionDetailsPresenter.MyView {
    public interface Binder extends UiBinder<PopupPanel, PreInscriptionDetailsView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;
    @UiField(provided = true)
    ParentEditor parentEditor;
    @UiField(provided = true)
    CandidatEditor candidatEditor;
    @UiField(provided = true)
    NiveauScolaireEditor niveauScolaireEditor;
    @UiField
    CellTable<ScolariteAnterieurProxy> etablissementTable;
    private final ListDataProvider<ScolariteAnterieurProxy> scolariteDataProvider;

    @Inject
    public PreInscriptionDetailsView(final EventBus eventBus, final Binder uiBinder,
            final UiHandlersStrategy<PreInscriptionDetailsUiHandlers> uiHandlers,
            final ValidationErrorPopup errorPopup, final ModalHeader modalHeader,
            final ParentEditor parentEditor, final CandidatEditor candidatEditor,
            final NiveauScolaireEditor niveauScolaireEditor, final SharedMessageBundle sharedMessageBundle) {
        super(eventBus, errorPopup, uiHandlers);

        this.modalHeader = modalHeader;
        this.parentEditor = parentEditor;
        this.candidatEditor = candidatEditor;
        this.niveauScolaireEditor = niveauScolaireEditor;

        initWidget(uiBinder.createAndBindUi(this));

        initScolariteDataGrid();
        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                hide();
            }
        });

        scolariteDataProvider = new ListDataProvider<ScolariteAnterieurProxy>();
        scolariteDataProvider.addDataDisplay(etablissementTable);
        etablissementTable.setEmptyTableWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.INFO));
    }

    @Override
    public void editParent(InfoParentProxy infoParent) {
        parentEditor.edit(infoParent);
    }

    @Override
    public void editCandidat(CandidatProxy candidat) {
        candidatEditor.edit(candidat);
    }

    @Override
    public void editScolarite(DossierProxy dossierProxy) {
        niveauScolaireEditor.edit(dossierProxy);
    }

    @Override
    public void setScolariteData(List<ScolariteAnterieurProxy> data) {
        scolariteDataProvider.getList().clear();
        scolariteDataProvider.getList().addAll(data);
    }

    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event) {
        hide();
    }

    private void initScolariteDataGrid() {
        TextColumn<ScolariteAnterieurProxy> etablissementColumn = new TextColumn<ScolariteAnterieurProxy>() {
            @Override
            public String getValue(ScolariteAnterieurProxy object) {
                return object.getEtablissement().getNom();
            }
        };
        etablissementColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        etablissementTable.addColumn(etablissementColumn, "Etablissement");
        etablissementTable.setColumnWidth(etablissementColumn, 30, Style.Unit.PCT);

        TextColumn<ScolariteAnterieurProxy> niveauEtudeColumn = new TextColumn<ScolariteAnterieurProxy>() {
            @Override
            public String getValue(ScolariteAnterieurProxy object) {
                return object.getTypeNiveauEtude().toString();
            }
        };
        etablissementColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        etablissementTable.addColumn(niveauEtudeColumn, "Niveau etude");
        etablissementTable.setColumnWidth(niveauEtudeColumn, 15, Style.Unit.PCT);

        TextColumn<ScolariteAnterieurProxy> classeColumn = new TextColumn<ScolariteAnterieurProxy>() {
            @Override
            public String getValue(ScolariteAnterieurProxy object) {
                return object.getClasse();
            }
        };
        etablissementColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        etablissementTable.addColumn(classeColumn, "Classe");
        etablissementTable.setColumnWidth(classeColumn, 20, Style.Unit.PCT);

        TextColumn<ScolariteAnterieurProxy> anneeScolaireColumn = new TextColumn<ScolariteAnterieurProxy>() {
            @Override
            public String getValue(ScolariteAnterieurProxy object) {
                return object.getAnneeScolaire().getValue();
            }
        };
        etablissementColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        etablissementTable.addColumn(anneeScolaireColumn, "Année scolaire");
        etablissementTable.setColumnWidth(anneeScolaireColumn, 20, Style.Unit.PCT);
    }
}
