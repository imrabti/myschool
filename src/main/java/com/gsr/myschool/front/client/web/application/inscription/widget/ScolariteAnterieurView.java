package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.github.gwtbootstrap.client.ui.CellTable;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ValidatedViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.ValidationErrorPopup;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.ScolariteAnterieurProxy;
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
    CellTable<ScolariteAnterieurProxy> etablissementTable;

    private final ListDataProvider<ScolariteAnterieurProxy> dataProvider;

    @Inject
    public ScolariteAnterieurView(final Binder uiBinder, final ValidationErrorPopup errorPopup,
                                  final UiHandlersStrategy<ScolariteAnterieurUiHandlers> uiHandlersStrategy,
                                  final ScolariteAnterieurEditor scolariteAnterieurEditor,
                                  final SharedMessageBundle sharedMessageBundle) {
        super(uiHandlersStrategy, errorPopup);

        this.scolariteAnterieurEditor = scolariteAnterieurEditor;

        initWidget(uiBinder.createAndBindUi(this));
        initDataGrid();

        dataProvider = new ListDataProvider<ScolariteAnterieurProxy>();
        dataProvider.addDataDisplay(etablissementTable);
        etablissementTable.setEmptyTableWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.INFO));
    }

    @Override
    public void setData(List<ScolariteAnterieurProxy> data) {
        dataProvider.getList().clear();
        dataProvider.getList().addAll(data);
    }

    private void initDataGrid() {
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

        ActionCell<ScolariteAnterieurProxy> actionCell = new ActionCell<ScolariteAnterieurProxy>("Supprimer",
                new Delegate<ScolariteAnterieurProxy>(){
            @Override
            public void execute(ScolariteAnterieurProxy object) {
                // TODO : UiHandler to remove the object in here...
            }
        });
        Column<ScolariteAnterieurProxy, ScolariteAnterieurProxy> actionColumn = new
                Column<ScolariteAnterieurProxy, ScolariteAnterieurProxy>(actionCell) {
            @Override
            public ScolariteAnterieurProxy getValue(ScolariteAnterieurProxy object) {
                return object;
            }
        };
        etablissementColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        etablissementTable.addColumn(actionColumn, "Action");
        etablissementTable.setColumnWidth(actionColumn, 15, Style.Unit.PCT);
    }
}
