package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.github.gwtbootstrap.client.ui.CellTable;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
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
import com.gsr.myschool.common.client.mvp.ValidationErrorPopup;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.FraterieProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.ui.dossier.FraterieEditor;
import com.gsr.myschool.common.client.widget.EmptyResult;

import java.util.List;

public class FraterieView extends ValidatedViewWithUiHandlers<FraterieUiHandlers> implements FrateriePresenter.MyView {
    public interface Binder extends UiBinder<Widget, FraterieView> {
    }

    @UiField(provided = true)
    FraterieEditor fraterieEditor;
    @UiField
    CellTable<FraterieProxy> fraterieTable;

    private final ListDataProvider<FraterieProxy> dataProvider;

    @Inject
    public FraterieView(final Binder uiBinder, final ValidationErrorPopup errorPopup,
                        final UiHandlersStrategy<FraterieUiHandlers> uiHandlersStrategy,
                        final SharedMessageBundle sharedMessageBundle,
                        final FraterieEditor fraterieEditor) {
        super(uiHandlersStrategy, errorPopup);

        this.fraterieEditor = fraterieEditor;

        initWidget(uiBinder.createAndBindUi(this));
        initDataGrid();

        dataProvider = new ListDataProvider<FraterieProxy>();
        dataProvider.addDataDisplay(fraterieTable);
        fraterieTable.setEmptyTableWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.INFO));
    }

    @Override
    public void setData(List<FraterieProxy> data) {
        dataProvider.getList().clear();
        dataProvider.getList().addAll(data);
    }

    @Override
    public void editFraterie(FraterieProxy fraterie) {
        fraterieEditor.edit(fraterie);
    }

    private void initDataGrid() {
        TextColumn<FraterieProxy> numDossierColumn = new TextColumn<FraterieProxy>() {
            @Override
            public String getValue(FraterieProxy object) {
                return object.getNumDossierGSR();
            }
        };
        numDossierColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        fraterieTable.addColumn(numDossierColumn, "N° dossier GSR");
        fraterieTable.setColumnWidth(numDossierColumn, 15, Style.Unit.PCT);

        TextColumn<FraterieProxy> nomPrenomColumn = new TextColumn<FraterieProxy>() {
            @Override
            public String getValue(FraterieProxy object) {
                return object.getNom() + " " + object.getPrenom();
            }
        };
        nomPrenomColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        fraterieTable.addColumn(nomPrenomColumn, "Nom et prénom");
        fraterieTable.setColumnWidth(nomPrenomColumn, 35, Style.Unit.PCT);

        TextColumn<FraterieProxy> typeFraterieColumn = new TextColumn<FraterieProxy>() {
            @Override
            public String getValue(FraterieProxy object) {
                return object.getTypeFraterie().toString();
            }
        };
        typeFraterieColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        fraterieTable.addColumn(typeFraterieColumn, "Type fraterie");
        fraterieTable.setColumnWidth(typeFraterieColumn, 20, Style.Unit.PCT);

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

        ActionCell<FraterieProxy> actionCell = new ActionCell<FraterieProxy>("Supprimer",
                new ActionCell.Delegate<FraterieProxy>(){
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
        fraterieTable.setColumnWidth(actionColumn, 15, Style.Unit.PCT);
    }

    @UiHandler("addFraterie")
    void onAddFraterie(ClickEvent event) {
        getUiHandlers().addFraterie(fraterieEditor.get());
    }
}
