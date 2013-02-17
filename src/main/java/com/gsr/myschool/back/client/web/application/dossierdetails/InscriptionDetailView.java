/**
 * Copyright 2012 Nuvola Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gsr.myschool.back.client.web.application.dossierdetails;

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
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;
import com.gsr.myschool.back.client.resource.message.MessageBundle;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.*;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.EmptyResult;
import com.gsr.myschool.common.client.widget.RowLabelValueFactory;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.common.shared.type.ParentType;

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
                                 final UiHandlersStrategy<InscriptionDetailUiHandlers> uiHandlers,
                                 final RowLabelValueFactory rowLabelValueFactory,
                                 final SharedMessageBundle sharedMessageBundle) {
        super(uiHandlers);

        this.messageBundle = messageBundle;
        this.rowLabelValueFactory = rowLabelValueFactory;

        initWidget(uiBinder.createAndBindUi(this));
        initFraterieDataGrid();
        initScolariteAnterieurDataGrid();

        dateFormat = DateTimeFormat.getFormat(GlobalParameters.DATE_FORMAT);
        fraterieDataProvider = new ListDataProvider<FraterieProxy>();
        scolariteDataProvider = new ListDataProvider<ScolariteAnterieurProxy>();

        fraterieDataProvider.addDataDisplay(fraterieTable);
        scolariteDataProvider.addDataDisplay(etablissementTable);

        fraterieTable.setEmptyTableWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.INFO));
        etablissementTable.setEmptyTableWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.INFO));
    }

    @UiHandler("back")
    public void retour(ClickEvent event) {
        getUiHandlers().retour();
    }

    @Override
    public void setDossier(DossierProxy dossier) {
        dossierTitle.setText(messageBundle.inscriptionDetailTitle(dossier.getGeneratedNumDossier()));

        SafeHtml safeDate = SafeHtmlUtils.fromString(dateFormat.format(dossier.getCreateDate()));
        SafeHtml safeDossierNum = SafeHtmlUtils.fromString(dossier.getGeneratedNumDossier());
        SafeHtml safeDossierStatus = SafeHtmlUtils.fromString(dossier.getStatus().toString());

        dossierPanel.clear();
        dossierPanel.add(rowLabelValueFactory.createValueLabel("N° Dossier : ", safeDossierNum));
        dossierPanel.add(rowLabelValueFactory.createValueLabel("Date de création : ", safeDate));
        dossierPanel.add(rowLabelValueFactory.createValueLabel("Status du dossier : ", safeDossierStatus));

        if (dossier.getFiliere() != null) {
            SafeHtml safeFiliere = SafeHtmlUtils.fromString(dossier.getFiliere().getNom());
            dossierPanel.add(rowLabelValueFactory.createValueLabel("Filière : ", safeFiliere));
        }

        if (dossier.getNiveauEtude() != null) {
            SafeHtml safeNiveauEtude = SafeHtmlUtils.fromString(dossier.getNiveauEtude().getNom());
            dossierPanel.add(rowLabelValueFactory.createValueLabel("Niveau d'étude : ", safeNiveauEtude));
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
        fraterieTable.addColumn(numDossierColumn, "N° dossier GSR");
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
        TextColumn<FraterieProxy> niveauColumn = new TextColumn<FraterieProxy>() {
            @Override
            public String getValue(FraterieProxy object) {
                return object.getNiveau().toString();
            }
        };
        niveauColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        fraterieTable.addColumn(niveauColumn, "Niveau");
        fraterieTable.setColumnWidth(niveauColumn, 20, Style.Unit.PCT);
        TextColumn<FraterieProxy> classeColumn = new TextColumn<FraterieProxy>() {
            @Override
            public String getValue(FraterieProxy object) {
                return object.getClasse().toString();
            }
        };
        classeColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        fraterieTable.addColumn(classeColumn, "Classe");
        fraterieTable.setColumnWidth(classeColumn, 20, Style.Unit.PCT);
        TextColumn<FraterieProxy> etablissementColumn = new TextColumn<FraterieProxy>() {
            @Override
            public String getValue(FraterieProxy object) {
                return object.getEtablissement().toString();
            }
        };
        etablissementColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        fraterieTable.addColumn(etablissementColumn, "Etablissement");
        fraterieTable.setColumnWidth(etablissementColumn, 20, Style.Unit.PCT);
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
        container.add(rowLabelValueFactory.createValueLabel("Téléphone maison : ", safeTelDom));

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
    }

    private Boolean isInfoParentEmpty(InfoParentProxy infoParent) {
        return Strings.isNullOrEmpty(infoParent.getNom())
                && Strings.isNullOrEmpty(infoParent.getPrenom())
                && Strings.isNullOrEmpty(infoParent.getTelDom())
                && Strings.isNullOrEmpty(infoParent.getEmail());
    }
}
