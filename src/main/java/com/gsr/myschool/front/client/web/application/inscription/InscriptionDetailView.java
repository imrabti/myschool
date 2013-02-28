package com.gsr.myschool.front.client.web.application.inscription;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.CellTable;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.CandidatProxy;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.FraterieProxy;
import com.gsr.myschool.common.client.proxy.InfoParentProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.EmptyResult;
import com.gsr.myschool.common.client.widget.RowLabelValueFactory;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.common.shared.type.ParentType;
import com.gsr.myschool.common.shared.type.TypeEnseignement;
import com.gsr.myschool.front.client.resource.message.MessageBundle;

import java.util.List;
import java.util.Map;

public class InscriptionDetailView extends ViewWithUiHandlers<InscriptionDetailUiHandlers>
        implements InscriptionDetailPresenter.MyView {
    public interface Binder extends UiBinder<Widget, InscriptionDetailView> {
    }

    @UiField
    Label dossierTitle;
    @UiField
    HTMLPanel dossierPanel;
    @UiField
    HTMLPanel perePanel;
    @UiField
    HTMLPanel merePanel;
    @UiField
    HTMLPanel tuteurPanel;
    @UiField
    HTMLPanel candidatPanel;
    @UiField
    Button submit;
    @UiField
    Button print;
    @UiField
    HTML errors;
    @UiField
    CellTable<FraterieProxy> fraterieTable;

    private final DateTimeFormat dateFormat;
    private final MessageBundle messageBundle;
    private final ListDataProvider<FraterieProxy> fraterieDataProvider;
    private final RowLabelValueFactory rowLabelValueFactory;

    @Inject
    public InscriptionDetailView(final Binder uiBinder, final MessageBundle messageBundle,
                                 final UiHandlersStrategy<InscriptionDetailUiHandlers> uiHandlersStrategy,
                                 final RowLabelValueFactory rowLabelValueFactory,
                                 final SharedMessageBundle sharedMessageBundle) {
        super(uiHandlersStrategy);

        this.messageBundle = messageBundle;
        this.rowLabelValueFactory = rowLabelValueFactory;

        initWidget(uiBinder.createAndBindUi(this));
        initFraterieDataGrid();

        dateFormat = DateTimeFormat.getFormat(GlobalParameters.DATE_FORMAT);
        fraterieDataProvider = new ListDataProvider<FraterieProxy>();

        fraterieDataProvider.addDataDisplay(fraterieTable);
        fraterieTable.setEmptyTableWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.INFO));
    }

    @Override
    public void setDossier(DossierProxy dossier) {
        dossierTitle.setText(messageBundle.inscriptionDetailTitle(dossier.getGeneratedNumDossier()));
        submit.setVisible(dossier.getStatus() == DossierStatus.CREATED);
        print.setVisible(dossier.getStatus() != DossierStatus.CREATED);

        SafeHtml safeDate = SafeHtmlUtils.fromString(dateFormat.format(dossier.getCreateDate()));
        SafeHtml safeDossierNum = SafeHtmlUtils.fromString(dossier.getGeneratedNumDossier());
        SafeHtml safeDossierStatus = SafeHtmlUtils.fromString(dossier.getStatus().toString());

        dossierPanel.clear();
        dossierPanel.add(rowLabelValueFactory.createValueLabel("N° Dossier : ", safeDossierNum));
        dossierPanel.add(rowLabelValueFactory.createValueLabel("Date de création : ", safeDate));
        dossierPanel.add(rowLabelValueFactory.createValueLabel("Statut du dossier : ", safeDossierStatus));

        if (dossier.getFiliere2() != null) {
            dossierPanel.add(rowLabelValueFactory.createHeader("Choix 1"));
        }

        if (dossier.getFiliere() != null) {
            SafeHtml safeFiliere = SafeHtmlUtils.fromString(dossier.getFiliere().getNom());
            dossierPanel.add(rowLabelValueFactory.createValueLabel("Formation : ", safeFiliere));
        }

        if (dossier.getNiveauEtude() != null) {
            SafeHtml safeNiveauEtude = SafeHtmlUtils.fromString(dossier.getNiveauEtude().getNom());
            dossierPanel.add(rowLabelValueFactory.createValueLabel("Niveau demandé : ", safeNiveauEtude));
        }

        if (dossier.getFiliere2() != null) {
            dossierPanel.add(rowLabelValueFactory.createHeader("Choix 2"));
            SafeHtml safeFiliere = SafeHtmlUtils.fromString(dossier.getFiliere2().getNom());
            dossierPanel.add(rowLabelValueFactory.createValueLabel("Formation : ", safeFiliere));
        }

        if (dossier.getNiveauEtude2() != null) {
            SafeHtml safeNiveauEtude = SafeHtmlUtils.fromString(dossier.getNiveauEtude2().getNom());
            dossierPanel.add(rowLabelValueFactory.createValueLabel("Niveau demandé : ", safeNiveauEtude));
        }
    }

    @Override
    public void setResponsable(Map<ParentType, InfoParentProxy> infoParents) {
        if (isInfoParentEmpty(infoParents.get(ParentType.PERE))) {
            perePanel.setVisible(false);
        } else {
            perePanel.setVisible(true);
            setupParentType(perePanel, infoParents.get(ParentType.PERE));
        }

        if (isInfoParentEmpty(infoParents.get(ParentType.MERE))) {
            merePanel.setVisible(false);
        } else {
            merePanel.setVisible(true);
            setupParentType(merePanel, infoParents.get(ParentType.MERE));
        }

        if (isInfoParentEmpty(infoParents.get(ParentType.TUTEUR))) {
            tuteurPanel.setVisible(false);
        } else {
            tuteurPanel.setVisible(true);
            setupParentType(tuteurPanel, infoParents.get(ParentType.TUTEUR));
        }
    }

    @Override
    public void setCandidat(CandidatProxy candidat) {
        candidatPanel.clear();

        SafeHtml safeFirstName = SafeHtmlUtils.fromString(Objects.firstNonNull(candidat.getFirstname(), ""));
        SafeHtml safeLastName = SafeHtmlUtils.fromString(Objects.firstNonNull(candidat.getLastname(), ""));
        SafeHtml safeBirthLocation = SafeHtmlUtils.fromString(Objects.firstNonNull(candidat.getBirthLocation(), ""));

        SafeHtml safeBirthDate;
        if (candidat.getBirthDate() == null) {
            safeBirthDate = SafeHtmlUtils.fromString("");
        } else {
            safeBirthDate = SafeHtmlUtils.fromString(dateFormat.format(candidat.getBirthDate()));
        }

        candidatPanel.add(rowLabelValueFactory.createValueLabel("Nom : ", safeFirstName));
        candidatPanel.add(rowLabelValueFactory.createValueLabel("Prénom : ", safeLastName));
        candidatPanel.add(rowLabelValueFactory.createValueLabel("Date de naissance : ", safeBirthDate));
        candidatPanel.add(rowLabelValueFactory.createValueLabel("Lieu de naissance : ", safeBirthLocation));

        if (candidat.getNationality() != null) {
            SafeHtml safeNationality = SafeHtmlUtils.fromString(candidat.getNationality().getLabel());
            candidatPanel.add(rowLabelValueFactory.createValueLabel("Nationnalité : ", safeNationality));
        }

        if (!Strings.isNullOrEmpty(candidat.getPhone())) {
            SafeHtml safeTel = SafeHtmlUtils.fromString(candidat.getPhone());
            candidatPanel.add(rowLabelValueFactory.createValueLabel("Téléphone : ", safeTel));
        }

        if (!Strings.isNullOrEmpty(candidat.getCin())) {
            SafeHtml safeCin = SafeHtmlUtils.fromString(candidat.getCin());
            candidatPanel.add(rowLabelValueFactory.createValueLabel("CIN : ", safeCin));
        }

        if (!Strings.isNullOrEmpty(candidat.getCne())) {
            SafeHtml safeCne = SafeHtmlUtils.fromString(candidat.getCne());
            candidatPanel.add(rowLabelValueFactory.createValueLabel("CNE/INE : ", safeCne));
        }

        if (!Strings.isNullOrEmpty(candidat.getEmail())) {
            SafeHtml safeEmail = SafeHtmlUtils.fromString(candidat.getEmail());
            candidatPanel.add(rowLabelValueFactory.createValueLabel("Email : ", safeEmail));
        }

        if (candidat.getBacSerie() != null) {
            SafeHtml safeBacSerie = SafeHtmlUtils.fromString(candidat.getBacSerie().getLabel());
            candidatPanel.add(rowLabelValueFactory.createValueLabel("Série du baccalauréat : ", safeBacSerie));
        }

        if (candidat.getBacYear() != null) {
            SafeHtml safeBacYear = SafeHtmlUtils.fromString(candidat.getBacYear().getLabel());
            candidatPanel.add(rowLabelValueFactory.createValueLabel("Année du baccalauréat : ", safeBacYear));
        }
    }

    @Override
    public void setFraterie(List<FraterieProxy> data) {
        fraterieDataProvider.getList().clear();
        fraterieDataProvider.getList().addAll(data);
    }

    @Override
    public void showErrors(List<String> errorsList) {
        errors.setVisible(true);
        StringBuilder builder = new StringBuilder();
        builder.append("<ul>");
        for (String violation : errorsList) {
            builder.append("<li class=\"error\">");
            builder.append(violation);
            builder.append("</li>");
        }
        builder.append("</ul>");
        errors.setHTML(builder.toString());
        errors.getElement().scrollIntoView();
    }

    @Override
    public void clearErrors() {
        errors.setHTML("");
        errors.setVisible(false);
    }

    @UiHandler("submit")
    void onSubmitClicked(ClickEvent event) {
        getUiHandlers().submitInscription();
    }

    @UiHandler("print")
    void onPrintClicked(ClickEvent event) {
        getUiHandlers().printInscription();
    }

    private void initFraterieDataGrid() {
        TextColumn<FraterieProxy> nomPrenomColumn = new TextColumn<FraterieProxy>() {
            @Override
            public String getValue(FraterieProxy object) {
                return object.getNom() + " " + object.getPrenom();
            }
        };
        nomPrenomColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        fraterieTable.addColumn(nomPrenomColumn, "Nom et prénom");
        fraterieTable.setColumnWidth(nomPrenomColumn, 30, Style.Unit.PCT);

        TextColumn<FraterieProxy> filiereColumn = new TextColumn<FraterieProxy>() {
            @Override
            public String getValue(FraterieProxy object) {
                if (object.getFiliere() == null) return "";
                return TypeEnseignement.BILINGUE.getId() == object.getFiliere().getId()?
                        TypeEnseignement.BILINGUE.toString() : TypeEnseignement.MISSION.toString();
            }
        };
        filiereColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        fraterieTable.addColumn(filiereColumn, "Type d'enseignement");
        fraterieTable.setColumnWidth(filiereColumn, 20, Style.Unit.PCT);

        TextColumn<FraterieProxy> classeColumn = new TextColumn<FraterieProxy>() {
            @Override
            public String getValue(FraterieProxy object) {
                if (object.getNiveau() == null) return "";
                return object.getNiveau().getNom();
            }
        };
        classeColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        fraterieTable.addColumn(classeColumn, "Niveau actuel");
        fraterieTable.setColumnWidth(classeColumn, 20, Style.Unit.PCT);

        TextColumn<FraterieProxy> etablissementColumn = new TextColumn<FraterieProxy>() {
            @Override
            public String getValue(FraterieProxy object) {
                if (object.getEtablissement() == null) return "";
                return object.getEtablissement().getNom();
            }
        };
        etablissementColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        fraterieTable.addColumn(etablissementColumn, "Etablissement");
        fraterieTable.setColumnWidth(etablissementColumn, 30, Style.Unit.PCT);
    }

    private void setupParentType(HTMLPanel container, InfoParentProxy infoParent) {
        container.clear();

        SafeHtml safeNom = SafeHtmlUtils.fromString(Objects.firstNonNull(infoParent.getNom(), ""));
        SafeHtml safePrenom = SafeHtmlUtils.fromString(Objects.firstNonNull(infoParent.getPrenom(), ""));
        SafeHtml safeTelDom = SafeHtmlUtils.fromString(Objects.firstNonNull(infoParent.getTelDom(), ""));
        SafeHtml safeEmail = SafeHtmlUtils.fromString(Objects.firstNonNull(infoParent.getEmail(), ""));
        SafeHtml safeBirthLocation = SafeHtmlUtils.fromString(Objects.firstNonNull(infoParent.getBirthLocation(), ""));

        SafeHtml safeBirthDate;
        if (infoParent.getBirthDate() == null) {
            safeBirthDate = SafeHtmlUtils.fromString("");
        } else {
            safeBirthDate = SafeHtmlUtils.fromString(dateFormat.format(infoParent.getBirthDate()));
        }

        container.add(rowLabelValueFactory.createHeader(infoParent.getParentType().toString()));
        container.add(rowLabelValueFactory.createValueLabel("Nom : ", safeNom));
        container.add(rowLabelValueFactory.createValueLabel("Prénom : ", safePrenom));
        container.add(rowLabelValueFactory.createValueLabel("Date de naissance : ", safeBirthDate));
        container.add(rowLabelValueFactory.createValueLabel("Lieu de naissance : ", safeBirthLocation));

        if (infoParent.getNationality() != null) {
            SafeHtml safeNationality = SafeHtmlUtils.fromString(infoParent.getNationality().getLabel());
            container.add(rowLabelValueFactory.createValueLabel("Nationnalité : ", safeNationality));
        }

        if (infoParent.getParentType() == ParentType.TUTEUR) {
            SafeHtml safeCivilite = SafeHtmlUtils.fromString(infoParent.getCivilite().toString());
            container.add(rowLabelValueFactory.createValueLabel("Civilité : ", safeCivilite));

            if (!Strings.isNullOrEmpty(infoParent.getLientParente())) {
                SafeHtml safelienParent = SafeHtmlUtils.fromString(infoParent.getLientParente());
                container.add(rowLabelValueFactory.createValueLabel("Lien de parenté : ", safelienParent));
            }
        }

        container.add(rowLabelValueFactory.createValueLabel("Email : ", safeEmail));
        container.add(rowLabelValueFactory.createValueLabel("Téléphone domicile : ", safeTelDom));

        if (!Strings.isNullOrEmpty(infoParent.getTelGsm())) {
            SafeHtml safeTelGsm = SafeHtmlUtils.fromString(infoParent.getTelGsm());
            container.add(rowLabelValueFactory.createValueLabel("Téléphone GSM : ", safeTelGsm));
        }

        if (!Strings.isNullOrEmpty(infoParent.getTelBureau())) {
            SafeHtml safeTelBureau = SafeHtmlUtils.fromString(infoParent.getTelBureau());
            container.add(rowLabelValueFactory.createValueLabel("Téléphone Bureau : ", safeTelBureau));
        }

        if (!Strings.isNullOrEmpty(infoParent.getFonction())) {
            SafeHtml safeFonction = SafeHtmlUtils.fromString(infoParent.getFonction());
            container.add(rowLabelValueFactory.createValueLabel("Fonction : ", safeFonction));
        }

        if (!Strings.isNullOrEmpty(infoParent.getInstitution())) {
            SafeHtml safeInstitution = SafeHtmlUtils.fromString(infoParent.getInstitution());
            container.add(rowLabelValueFactory.createValueLabel("Institution : ", safeInstitution));
        }

        if (!Strings.isNullOrEmpty(infoParent.getAddress())) {
            SafeHtml safeAdresse = SafeHtmlUtils.fromString(infoParent.getAddress());
            container.add(rowLabelValueFactory.createValueLabel("Adresse : ", safeAdresse));
        }

        if (infoParent.getParentGsr()) {
            SafeHtml safe = SafeHtmlUtils.fromString("oui");
            container.add(rowLabelValueFactory.createValueLabel("Ancien du GSR : ", safe));
        }

        if (!Strings.isNullOrEmpty(infoParent.getPromotionGsr())) {
            SafeHtml safe = SafeHtmlUtils.fromString(infoParent.getPromotionGsr().toString());
            container.add(rowLabelValueFactory.createValueLabel("Promotion : ", safe));
        }

        if (!Strings.isNullOrEmpty(infoParent.getFormationGsr())) {
            SafeHtml safe = SafeHtmlUtils.fromString(infoParent.getFormationGsr());
            container.add(rowLabelValueFactory.createValueLabel("Formation : ", safe));
        }
    }

    private Boolean isInfoParentEmpty(InfoParentProxy infoParent) {
        return Strings.isNullOrEmpty(infoParent.getNom())
                && Strings.isNullOrEmpty(infoParent.getPrenom())
                && Strings.isNullOrEmpty(infoParent.getTelDom())
                && Strings.isNullOrEmpty(infoParent.getEmail());
    }
}
