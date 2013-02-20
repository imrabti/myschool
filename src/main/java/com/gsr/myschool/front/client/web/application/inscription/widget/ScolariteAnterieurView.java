package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.github.gwtbootstrap.client.ui.CellTable;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.ActionCell.Delegate;
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
import com.gsr.myschool.common.client.proxy.ScolariteActuelleProxy;
import com.gsr.myschool.common.client.proxy.ScolariteActuelleDTOProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.ui.dossier.ScolariteAnterieurEditor;
import com.gsr.myschool.common.client.widget.EmptyResult;

import java.util.List;

public class ScolariteAnterieurView extends ValidatedViewWithUiHandlers<ScolariteAnterieurUiHandlers>
        implements ScolariteAnterieurPresenter.MyView {
    public interface Binder extends UiBinder<Widget, ScolariteAnterieurView> {
    }

    @UiField(provided = true)
    ScolariteAnterieurEditor scolariteAnterieurEditor;
    @UiField
    CellTable<ScolariteActuelleProxy> etablissementTable;

    private final ListDataProvider<ScolariteActuelleProxy> dataProvider;

    @Inject
    public ScolariteAnterieurView(final Binder uiBinder, final ValidationErrorPopup errorPopup,
                                  final UiHandlersStrategy<ScolariteAnterieurUiHandlers> uiHandlersStrategy,
                                  final ScolariteAnterieurEditor scolariteAnterieurEditor,
                                  final SharedMessageBundle sharedMessageBundle) {
        super(uiHandlersStrategy, errorPopup);

        this.scolariteAnterieurEditor = scolariteAnterieurEditor;

        initWidget(uiBinder.createAndBindUi(this));
        initDataGrid();

        dataProvider = new ListDataProvider<ScolariteActuelleProxy>();
        dataProvider.addDataDisplay(etablissementTable);
        etablissementTable.setEmptyTableWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.INFO));
    }

    @Override
    public void setData(List<ScolariteActuelleProxy> data) {
        dataProvider.getList().clear();
        dataProvider.getList().addAll(data);
    }

    @Override
    public void editScolariteAnterieur(ScolariteActuelleDTOProxy scolariteAnterieur) {
        scolariteAnterieurEditor.edit(scolariteAnterieur);
    }

    private void initDataGrid() {
        TextColumn<ScolariteActuelleProxy> etablissementColumn = new TextColumn<ScolariteActuelleProxy>() {
            @Override
            public String getValue(ScolariteActuelleProxy object) {
                return object.getEtablissement().getNom();
            }
        };
        etablissementColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        etablissementTable.addColumn(etablissementColumn, "Etablissement");
        etablissementTable.setColumnWidth(etablissementColumn, 30, Style.Unit.PCT);

        TextColumn<ScolariteActuelleProxy> niveauEtudeColumn = new TextColumn<ScolariteActuelleProxy>() {
            @Override
            public String getValue(ScolariteActuelleProxy object) {
                return object.getTypeNiveauEtude().toString();
            }
        };
        etablissementColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        etablissementTable.addColumn(niveauEtudeColumn, "Niveau etude");
        etablissementTable.setColumnWidth(niveauEtudeColumn, 15, Style.Unit.PCT);

        TextColumn<ScolariteActuelleProxy> classeColumn = new TextColumn<ScolariteActuelleProxy>() {
            @Override
            public String getValue(ScolariteActuelleProxy object) {
                return object.getClasse();
            }
        };
        etablissementColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        etablissementTable.addColumn(classeColumn, "Classe");
        etablissementTable.setColumnWidth(classeColumn, 20, Style.Unit.PCT);

        TextColumn<ScolariteActuelleProxy> anneeScolaireColumn = new TextColumn<ScolariteActuelleProxy>() {
            @Override
            public String getValue(ScolariteActuelleProxy object) {
                return object.getAnneeScolaire().getValue();
            }
        };
        etablissementColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        etablissementTable.addColumn(anneeScolaireColumn, "Année scolaire");
        etablissementTable.setColumnWidth(anneeScolaireColumn, 20, Style.Unit.PCT);

        ActionCell<ScolariteActuelleProxy> actionCell = new ActionCell<ScolariteActuelleProxy>("Supprimer",
                new Delegate<ScolariteActuelleProxy>(){
            @Override
            public void execute(ScolariteActuelleProxy object) {
                getUiHandlers().deleteScolariteAnterieur(object);
            }
        });
        Column<ScolariteActuelleProxy, ScolariteActuelleProxy> actionColumn = new
                Column<ScolariteActuelleProxy, ScolariteActuelleProxy>(actionCell) {
            @Override
            public ScolariteActuelleProxy getValue(ScolariteActuelleProxy object) {
                return object;
            }
        };
        etablissementColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        etablissementTable.addColumn(actionColumn, "Action");
        etablissementTable.setColumnWidth(actionColumn, 15, Style.Unit.PCT);
    }

    @UiHandler("addEtablissement")
    void onAddEtablissement(ClickEvent event) {
        getUiHandlers().addScolariteAnterieur(scolariteAnterieurEditor.get());
    }
}
