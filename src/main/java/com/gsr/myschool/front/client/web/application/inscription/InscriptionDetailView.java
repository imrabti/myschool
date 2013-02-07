package com.gsr.myschool.front.client.web.application.inscription;

import com.github.gwtbootstrap.client.ui.CellTable;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.common.base.Strings;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.dom.client.Style;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ViewImpl;
import com.gsr.myschool.common.client.proxy.CandidatProxy;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.FraterieProxy;
import com.gsr.myschool.common.client.proxy.InfoParentProxy;
import com.gsr.myschool.common.client.proxy.ScolariteAnterieurProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.EmptyResult;
import com.gsr.myschool.common.client.widget.RowLabelValueFactory;
import com.gsr.myschool.front.client.resource.message.MessageBundle;

import java.util.List;

public class InscriptionDetailView extends ViewImpl implements InscriptionDetailPresenter.MyView {
    public interface Binder extends UiBinder<Widget, InscriptionDetailView> {
    }

    @UiField
    Label dossierTitle;
    @UiField
    HTMLPanel dossierPanel;
    @UiField
    HTMLPanel responsablePanel;
    @UiField
    HTMLPanel candidatPanel;
    @UiField
    CellTable<FraterieProxy> fraterieTable;
    @UiField
    CellTable<ScolariteAnterieurProxy> etablissementTable;

    private final DateTimeFormat dateFormat;
    private final MessageBundle messageBundle;
    private final ListDataProvider<FraterieProxy> fraterieDataProvider;
    private final ListDataProvider<ScolariteAnterieurProxy> scolariteDataProvider;
    private final RowLabelValueFactory rowLabelValueFactory;

    @Inject
    public InscriptionDetailView(final Binder uiBinder, final MessageBundle messageBundle,
                                 final RowLabelValueFactory rowLabelValueFactory,
                                 final SharedMessageBundle sharedMessageBundle) {
        this.messageBundle = messageBundle;
        this.rowLabelValueFactory = rowLabelValueFactory;

        initWidget(uiBinder.createAndBindUi(this));
        initFraterieDataGrid();
        initScolariteAnterieurDataGrid();

        dateFormat = DateTimeFormat.getFormat("LLL d yyyy");
        fraterieDataProvider = new ListDataProvider<FraterieProxy>();
        scolariteDataProvider = new ListDataProvider<ScolariteAnterieurProxy>();

        fraterieDataProvider.addDataDisplay(fraterieTable);
        scolariteDataProvider.addDataDisplay(etablissementTable);

        fraterieTable.setEmptyTableWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.INFO));
        etablissementTable.setEmptyTableWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.INFO));
    }

    @Override
    public void setDossier(DossierProxy dossier) {
        dossierTitle.setText(messageBundle.inscriptionDetailTitle(dossier.getGeneratedNumDossier()));

        SafeHtml safeDate = SafeHtmlUtils.fromString(dateFormat.format(dossier.getCreateDate()));
        SafeHtml safeDossierNum = SafeHtmlUtils.fromString(dossier.getGeneratedNumDossier());
        SafeHtml safeDossierStatus = SafeHtmlUtils.fromString(dossier.getStatus().toString());

        dossierPanel.clear();
        dossierPanel.add(rowLabelValueFactory.create("N° Dossier : ", safeDossierNum));
        dossierPanel.add(rowLabelValueFactory.create("Date de création : ", safeDate));
        dossierPanel.add(rowLabelValueFactory.create("Status du dossier : ", safeDossierStatus));

        if (dossier.getFiliere() != null) {
            SafeHtml safeFiliere = SafeHtmlUtils.fromString(dossier.getFiliere().getNom());
            dossierPanel.add(rowLabelValueFactory.create("Filière : ", safeFiliere));
        }

        if (dossier.getNiveauEtude() != null) {
            SafeHtml safeNiveauEtude = SafeHtmlUtils.fromString(dossier.getNiveauEtude().getNom());
            dossierPanel.add(rowLabelValueFactory.create("Niveau d'étude : ", safeNiveauEtude));
        }
    }

    @Override
    public void setResponsable(InfoParentProxy infoParent) {
        responsablePanel.clear();

        SafeHtml safeNom = SafeHtmlUtils.fromString(infoParent.getNom());
        SafeHtml safePrenom = SafeHtmlUtils.fromString(infoParent.getPrenom());
        SafeHtml safeTelDom = SafeHtmlUtils.fromString(infoParent.getTelDom());
        SafeHtml safeEmail = SafeHtmlUtils.fromString(infoParent.getEmail());
        SafeHtml safeTypeParent = SafeHtmlUtils.fromString(infoParent.getParentType().toString());

        responsablePanel.add(rowLabelValueFactory.create("Nom : ", safeNom));
        responsablePanel.add(rowLabelValueFactory.create("Prénom : ", safePrenom));
        responsablePanel.add(rowLabelValueFactory.create("Lien de parenté : ", safeTypeParent));
        responsablePanel.add(rowLabelValueFactory.create("Email : ", safeEmail));
        responsablePanel.add(rowLabelValueFactory.create("Téléphone maison : ", safeTelDom));

        if (!Strings.isNullOrEmpty(infoParent.getTelGsm())) {
            SafeHtml safeTelGsm = SafeHtmlUtils.fromString(infoParent.getTelGsm());
            responsablePanel.add(rowLabelValueFactory.create("Téléphone GSM : ", safeTelGsm));
        }

        if (!Strings.isNullOrEmpty(infoParent.getTelBureau())) {
            SafeHtml safeTelBureau = SafeHtmlUtils.fromString(infoParent.getTelBureau());
            responsablePanel.add(rowLabelValueFactory.create("Téléphone Bureau : ", safeTelBureau));
        }

        if (!Strings.isNullOrEmpty(infoParent.getFonction())) {
            SafeHtml safeFonction = SafeHtmlUtils.fromString(infoParent.getFonction());
            responsablePanel.add(rowLabelValueFactory.create("Fonction : ", safeFonction));
        }

        if (!Strings.isNullOrEmpty(infoParent.getInstitution())) {
            SafeHtml safeInstitution = SafeHtmlUtils.fromString(infoParent.getInstitution());
            responsablePanel.add(rowLabelValueFactory.create("Institution : ", safeInstitution));
        }

        if (!Strings.isNullOrEmpty(infoParent.getAddress())) {
            SafeHtml safeAdresse = SafeHtmlUtils.fromString(infoParent.getAddress());
            responsablePanel.add(rowLabelValueFactory.create("Adresse : ", safeAdresse));
        }
    }

    @Override
    public void setCandidat(CandidatProxy candidat) {
        candidatPanel.clear();

        SafeHtml safeFirstName = SafeHtmlUtils.fromString(candidat.getFirstname());
        SafeHtml safeLastName = SafeHtmlUtils.fromString(candidat.getLastname());
        SafeHtml safeBirthDate = SafeHtmlUtils.fromString(dateFormat.format(candidat.getBirthDate()));
        SafeHtml safeBirthLocation = SafeHtmlUtils.fromString(candidat.getBirthLocation());

        candidatPanel.add(rowLabelValueFactory.create("Nom : ", safeFirstName));
        candidatPanel.add(rowLabelValueFactory.create("Prénom : ", safeLastName));
        candidatPanel.add(rowLabelValueFactory.create("Date de naissance : ", safeBirthDate));
        candidatPanel.add(rowLabelValueFactory.create("Lieu de naissance : ", safeBirthLocation));

        if (!Strings.isNullOrEmpty(candidat.getPhone())) {
            SafeHtml safeTel = SafeHtmlUtils.fromString(candidat.getPhone());
            candidatPanel.add(rowLabelValueFactory.create("Téléphone : ", safeTel));
        }

        if (!Strings.isNullOrEmpty(candidat.getCin())) {
            SafeHtml safeCin = SafeHtmlUtils.fromString(candidat.getCin());
            candidatPanel.add(rowLabelValueFactory.create("CIN : ", safeCin));
        }

        if (!Strings.isNullOrEmpty(candidat.getCne())) {
            SafeHtml safeCne = SafeHtmlUtils.fromString(candidat.getCne());
            candidatPanel.add(rowLabelValueFactory.create("CNE : ", safeCne));
        }

        if (!Strings.isNullOrEmpty(candidat.getEmail())) {
            SafeHtml safeEmail = SafeHtmlUtils.fromString(candidat.getEmail());
            candidatPanel.add(rowLabelValueFactory.create("Email : ", safeEmail));
        }

        if (candidat.getNationality() != null) {
            SafeHtml safeNationality = SafeHtmlUtils.fromString(candidat.getNationality().getLabel());
            candidatPanel.add(rowLabelValueFactory.create("Nationnalité : ", safeNationality));
        }

        if (candidat.getBacSerie() != null) {
            SafeHtml safeBacSerie = SafeHtmlUtils.fromString(candidat.getBacSerie().getLabel());
            candidatPanel.add(rowLabelValueFactory.create("Série du baccalauréat : ", safeBacSerie));
        }

        if (candidat.getBacYear() != null) {
            SafeHtml safeBacYear = SafeHtmlUtils.fromString(candidat.getBacYear().getLabel());
            candidatPanel.add(rowLabelValueFactory.create("Année du baccalauréat : ", safeBacYear));
        }
    }

    @Override
    public void setScolariteAnterieur(List<ScolariteAnterieurProxy> data) {
        scolariteDataProvider.getList().clear();
        scolariteDataProvider.getList().addAll(data);
    }

    @Override
    public void setFraterie(List<FraterieProxy> data) {
        fraterieDataProvider.getList().clear();
        fraterieDataProvider.getList().addAll(data);
    }

    private void initFraterieDataGrid() {
        TextColumn<FraterieProxy> numDossierColumn = new TextColumn<FraterieProxy>() {
            @Override
            public String getValue(FraterieProxy object) {
                return object.getNumDossierGSR();
            }
        };
        numDossierColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        fraterieTable.addColumn(numDossierColumn, "#");
        fraterieTable.setColumnWidth(numDossierColumn, 25, Style.Unit.PCT);

        TextColumn<FraterieProxy> nomPrenomColumn = new TextColumn<FraterieProxy>() {
            @Override
            public String getValue(FraterieProxy object) {
                return object.getNom() + " " + object.getPrenom();
            }
        };
        nomPrenomColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        fraterieTable.addColumn(nomPrenomColumn, "Nom et prénom");
        fraterieTable.setColumnWidth(nomPrenomColumn, 45, Style.Unit.PCT);

        TextColumn<FraterieProxy> typeFraterieColumn = new TextColumn<FraterieProxy>() {
            @Override
            public String getValue(FraterieProxy object) {
                return object.getTypeFraterie().toString();
            }
        };
        typeFraterieColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        fraterieTable.addColumn(typeFraterieColumn, "Type fraterie");
        fraterieTable.setColumnWidth(typeFraterieColumn, 30, Style.Unit.PCT);
    }

    private void initScolariteAnterieurDataGrid() {
        TextColumn<ScolariteAnterieurProxy> etablissementColumn = new TextColumn<ScolariteAnterieurProxy>() {
            @Override
            public String getValue(ScolariteAnterieurProxy object) {
                return object.getEtablissement().getNom();
            }
        };
        etablissementColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        etablissementTable.addColumn(etablissementColumn, "Etablissement");
        etablissementTable.setColumnWidth(etablissementColumn, 35, Style.Unit.PCT);

        TextColumn<ScolariteAnterieurProxy> niveauEtudeColumn = new TextColumn<ScolariteAnterieurProxy>() {
            @Override
            public String getValue(ScolariteAnterieurProxy object) {
                return object.getTypeNiveauEtude().toString();
            }
        };
        etablissementColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        etablissementTable.addColumn(niveauEtudeColumn, "Niveau etude");
        etablissementTable.setColumnWidth(niveauEtudeColumn, 20, Style.Unit.PCT);

        TextColumn<ScolariteAnterieurProxy> classeColumn = new TextColumn<ScolariteAnterieurProxy>() {
            @Override
            public String getValue(ScolariteAnterieurProxy object) {
                return object.getClasse();
            }
        };
        etablissementColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        etablissementTable.addColumn(classeColumn, "Classe");
        etablissementTable.setColumnWidth(classeColumn, 25, Style.Unit.PCT);

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
