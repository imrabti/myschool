package com.gsr.myschool.back.client.web.application.reporting.widget;

import com.github.gwtbootstrap.client.ui.CellTable;
import com.github.gwtbootstrap.client.ui.SimplePager;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.inject.Inject;
import com.gsr.myschool.back.client.web.application.reporting.widget.convocationui.DossierFilterEditor;
import com.gsr.myschool.common.client.proxy.DossierConvocationDTOProxy;
import com.gsr.myschool.common.client.proxy.DossierFilterDTOProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.EmptyResult;
import com.gsr.myschool.common.client.widget.LoadingIndicator;
import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import java.util.List;

public class ConvocationReportView extends ViewWithUiHandlers<ConvocationReportUiHandlers>
        implements ConvocationReportPresenter.MyView {
    public interface Binder extends UiBinder<Widget, ConvocationReportView> {
    }

    @UiField(provided = true)
    DossierFilterEditor dossierFilterEditor;
    @UiField
    CellTable<DossierConvocationDTOProxy> convocationsTable;
    @UiField(provided = true)
    SimplePager pager;

    private final DateTimeFormat dateFormat;
    private final AsyncDataProvider<DossierConvocationDTOProxy> dataProvider;
    private Delegate<DossierConvocationDTOProxy> printAction;

    @Inject
    public ConvocationReportView(final Binder uiBinder, final SharedMessageBundle sharedMessageBundle,
            final DossierFilterEditor dossierFilterEditor,
            final LoadingIndicator loadingIndicator,
            final SimplePager.Resources pagerResources) {
        this.dossierFilterEditor = dossierFilterEditor;
        this.dataProvider = setupDataProvider();
        this.pager = new SimplePager(SimplePager.TextLocation.RIGHT, pagerResources, false, 0, true);

        initWidget(uiBinder.createAndBindUi(this));
        initDataGrid();

        dataProvider.addDataDisplay(convocationsTable);
        pager.setDisplay(convocationsTable);
        pager.setPageSize(GlobalParameters.PAGE_SIZE);

        dateFormat = DateTimeFormat.getFormat(GlobalParameters.DATE_FORMAT);
        convocationsTable.setLoadingIndicator(loadingIndicator);
        convocationsTable.setEmptyTableWidget(new EmptyResult(sharedMessageBundle.noResultFound(), AlertType.WARNING));
    }

	@Override
	public void reloadData() {
		convocationsTable.setVisibleRangeAndClearData(convocationsTable.getVisibleRange(), true);
	}

	@Override
	public void setDossierCount(Integer result) {
		dataProvider.updateRowCount(result, true);
	}

	@Override
	public void displayDossiers(Integer offset, List<DossierConvocationDTOProxy> cars) {
		dataProvider.updateRowData(offset, cars);
	}

	@Override
	public void editDossierFilter(DossierFilterDTOProxy dossierFilter) {
		dossierFilterEditor.edit(dossierFilter);
	}

	@UiHandler("search")
	void onSearch(ClickEvent event) {
		getUiHandlers().searchWithFilter(dossierFilterEditor.get());
	}

	@UiHandler("export")
	void onExport(ClickEvent event) {
		getUiHandlers().export(dossierFilterEditor.get());
	}

	@UiHandler("initialize")
	void onInitialize(ClickEvent event) {
		getUiHandlers().init();
	}

	private AsyncDataProvider<DossierConvocationDTOProxy> setupDataProvider() {
		return new AsyncDataProvider<DossierConvocationDTOProxy>() {
			@Override
			protected void onRangeChanged(HasData<DossierConvocationDTOProxy> display) {
				Range range = display.getVisibleRange();
				if (getUiHandlers() != null) {
					getUiHandlers().fetchData(range.getStart(), range.getLength());
				}
			}
		};
	}

	private void initDataGrid() {
        TextColumn<DossierConvocationDTOProxy> refColumn = new TextColumn<DossierConvocationDTOProxy>() {
            @Override
            public String getValue(DossierConvocationDTOProxy object) {
                return object.getDossierSession().getDossier().getGeneratedNumDossier();
            }
        };
        refColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        convocationsTable.addColumn(refColumn, "N° Dossier");
        convocationsTable.setColumnWidth(refColumn, 10, Style.Unit.PCT);

        TextColumn<DossierConvocationDTOProxy> nomColumn = new TextColumn<DossierConvocationDTOProxy>() {
			@Override
			public String getValue(DossierConvocationDTOProxy object) {
				if (object.getDossierSession().getDossier().getCandidat() == null) return "";
				return object.getDossierSession().getDossier().getCandidat().getLastname() + " " +
                        object.getDossierSession().getDossier().getCandidat().getFirstname();
			}
		};
		nomColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		convocationsTable.addColumn(nomColumn, "Nom prénom");
		convocationsTable.setColumnWidth(nomColumn, 15, Style.Unit.PCT);

		TextColumn<DossierConvocationDTOProxy> dateColumn = new TextColumn<DossierConvocationDTOProxy>() {
			@Override
			public String getValue(DossierConvocationDTOProxy object) {
				if (object.getDossierSession().getDossier().getCandidat().getBirthDate() == null) return "";
				return dateFormat.format(object.getDossierSession().getDossier().getCandidat().getBirthDate());
			}
		};
		dateColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		convocationsTable.addColumn(dateColumn, "Date de naissance");
		convocationsTable.setColumnWidth(dateColumn, 10, Style.Unit.PCT);

		TextColumn<DossierConvocationDTOProxy> etsColumn = new TextColumn<DossierConvocationDTOProxy>() {
			@Override
			public String getValue(DossierConvocationDTOProxy object) {
				if (object.getDossierSession().getDossier().getScolariteActuelle() == null) return "";
				return object.getDossierSession().getDossier().getScolariteActuelle().getEtablissement().getNom();
			}
		};
        etsColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		convocationsTable.addColumn(etsColumn, "Etablissement D'origine");
		convocationsTable.setColumnWidth(etsColumn, 10, Style.Unit.PCT);

        TextColumn<DossierConvocationDTOProxy> sessionColumn = new TextColumn<DossierConvocationDTOProxy>() {
            @Override
            public String getValue(DossierConvocationDTOProxy object) {
                return object.getDossierSession().getSessionExamen().getNom();
            }
        };
        sessionColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        convocationsTable.addColumn(sessionColumn, "Session");
        convocationsTable.setColumnWidth(sessionColumn, 10, Style.Unit.PCT);

        TextColumn<DossierConvocationDTOProxy> fraterieGsrColumn = new TextColumn<DossierConvocationDTOProxy>() {
            @Override
            public String getValue(DossierConvocationDTOProxy object) {
                return object.getHaveFraterie();
            }
        };
        fraterieGsrColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        convocationsTable.addColumn(fraterieGsrColumn, "Fraterie-GSR");
        convocationsTable.setColumnWidth(fraterieGsrColumn, 10, Style.Unit.PCT);

        TextColumn<DossierConvocationDTOProxy> parentGsrColumn = new TextColumn<DossierConvocationDTOProxy>() {
            @Override
            public String getValue(DossierConvocationDTOProxy object) {
                return object.getHaveParentGsr();
            }
        };
        parentGsrColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        convocationsTable.addColumn(parentGsrColumn, "Parent-GSR");
        convocationsTable.setColumnWidth(parentGsrColumn, 10, Style.Unit.PCT);
	}
}