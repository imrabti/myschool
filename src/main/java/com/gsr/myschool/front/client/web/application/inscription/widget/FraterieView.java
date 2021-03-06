package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.github.gwtbootstrap.client.ui.CellTable;
import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.Well;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ValidatedViewWithUiHandlers;
import com.gsr.myschool.common.client.widget.ValidationErrorPopup;
import com.gsr.myschool.common.client.proxy.EtablissementScolaireProxy;
import com.gsr.myschool.common.client.proxy.FraterieDTOProxy;
import com.gsr.myschool.common.client.proxy.FraterieProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.ui.dossier.FraterieEditor;
import com.gsr.myschool.common.client.widget.EmptyResult;
import com.gsr.myschool.common.shared.type.TypeEnseignement;

import java.util.List;

public class FraterieView extends ValidatedViewWithUiHandlers<FraterieUiHandlers>
        implements FrateriePresenter.MyView, FraterieEditor.Handler {
    public interface Binder extends UiBinder<Widget, FraterieView> {
    }

    @UiField(provided = true)
    FraterieEditor fraterieEditor;
    @UiField
    CellTable<FraterieProxy> fraterieTable;
    @UiField
    CheckBox isFraterie;
    @UiField
    Well fraterieFields;

    private final ListDataProvider<FraterieProxy> dataProvider;

    @Inject
    public FraterieView(final Binder uiBinder, final ValidationErrorPopup errorPopup,
                        final SharedMessageBundle sharedMessageBundle,
                        final FraterieEditor fraterieEditor) {
        super(errorPopup);

        this.fraterieEditor = fraterieEditor;

        initWidget(uiBinder.createAndBindUi(this));
        fraterieEditor.setHandler(this);
        initDataGrid();

        dataProvider = new ListDataProvider<FraterieProxy>();
        dataProvider.addDataDisplay(fraterieTable);
        fraterieTable.setEmptyTableWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.WARNING));

        setFraterieVisible(dataProvider.getList().size() > 0);
        isFraterie.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                setFraterieVisible(event.getValue());
            }
        });
    }

    @Override
    public Boolean checkFraterie() {
        return isFraterie.getValue() ? dataProvider.getList().size() > 0 : true;
    }

    @Override
    public void setData(List<FraterieProxy> data) {
        dataProvider.getList().clear();
        dataProvider.getList().addAll(data);

        isFraterie.setValue(dataProvider.getList().size() > 0);
        setFraterieVisible(dataProvider.getList().size() > 0);
    }

    @Override
    public void editFraterie(FraterieDTOProxy fraterie) {
        fraterieEditor.edit(fraterie);
    }

    @Override
    public void onSelectEtablissement() {
        getUiHandlers().selectEtablissement();
    }

    @Override
    public void setEtablissement(EtablissementScolaireProxy selectedEtablissement) {
        fraterieEditor.setEtablissementLabel(selectedEtablissement != null ? selectedEtablissement.getNom() : "");
    }

    private void initDataGrid() {
        TextColumn<FraterieProxy> nomPrenomColumn = new TextColumn<FraterieProxy>() {
            @Override
            public String getValue(FraterieProxy object) {
                return object.getNom() + " " + object.getPrenom();
            }
        };
        nomPrenomColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        fraterieTable.addColumn(nomPrenomColumn, "Nom et prénom");
        fraterieTable.setColumnWidth(nomPrenomColumn, 25, Style.Unit.PCT);

        TextColumn<FraterieProxy> filiereColumn = new TextColumn<FraterieProxy>() {
            @Override
            public String getValue(FraterieProxy object) {
                if (object.getFiliere() == null) return "";
                return TypeEnseignement.BILINGUE.getId() == object.getFiliere().getId() ?
                        TypeEnseignement.BILINGUE.toString() : TypeEnseignement.MISSION.toString();
            }
        };
        filiereColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        fraterieTable.addColumn(filiereColumn, "Type d'enseignement");
        fraterieTable.setColumnWidth(filiereColumn, 15, Style.Unit.PCT);

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

        ActionCell<FraterieProxy> actionCell = new ActionCell<FraterieProxy>("Supprimer",
                new ActionCell.Delegate<FraterieProxy>() {
                    @Override
                    public void execute(FraterieProxy object) {
                        getUiHandlers().deleteFraterie(object);
                    }
                });
        Column<FraterieProxy, FraterieProxy> actionColumn = new Column<FraterieProxy, FraterieProxy>(actionCell) {
            @Override
            public FraterieProxy getValue(FraterieProxy object) {
                return object;
            }
        };
        actionColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        fraterieTable.addColumn(actionColumn, "Action");
        fraterieTable.setColumnWidth(actionColumn, 10, Style.Unit.PCT);
    }

    @UiHandler("addFraterie")
    void onAddFraterie(ClickEvent event) {
        getUiHandlers().addFraterie(fraterieEditor.get());
    }

    private void setFraterieVisible(boolean b) {
        fraterieFields.setVisible(b);
        fraterieTable.setVisible(b);
    }
}
