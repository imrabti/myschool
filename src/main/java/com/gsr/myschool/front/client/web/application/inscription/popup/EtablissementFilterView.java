package com.gsr.myschool.front.client.web.application.inscription.popup;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.CellTable;
import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.dom.client.Style;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.common.client.widget.renderer.ValueListRendererFactory;
import com.gsr.myschool.front.client.resource.style.CellTableStyle;
import com.gsr.myschool.common.client.mvp.PopupViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.EtablissementFilterDTOProxy;
import com.gsr.myschool.common.client.proxy.EtablissementScolaireProxy;
import com.gsr.myschool.common.client.proxy.ValueListProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.util.EditorView;
import com.gsr.myschool.common.client.util.ValueList;
import com.gsr.myschool.common.client.widget.EmptyResult;
import com.gsr.myschool.common.client.widget.ModalHeader;
import com.gsr.myschool.common.client.widget.renderer.EnumRenderer;
import com.gsr.myschool.common.shared.type.EtablissementType;
import com.gsr.myschool.common.shared.type.ValueTypeCode;

import java.util.Arrays;
import java.util.List;

public class EtablissementFilterView extends PopupViewWithUiHandlers<EtablissementFilterUiHandlers>
        implements EtablissementFilterPresenter.MyView, EditorView<EtablissementFilterDTOProxy> {
    public interface Binder extends UiBinder<Widget, EtablissementFilterView> {
    }

    public interface Driver extends SimpleBeanEditorDriver<EtablissementFilterDTOProxy, EtablissementFilterView> {
    }

    @UiField(provided = true)
    ModalHeader modalHeader;
    @UiField(provided = true)
    ValueListBox<ValueListProxy> ville;
    @UiField(provided = true)
    ValueListBox<EtablissementType> type;
    @UiField(provided = true)
    CellTable<EtablissementScolaireProxy> etablissementTable;
    @UiField
    Button choose;

    private final Driver driver;
    private final ListDataProvider<EtablissementScolaireProxy> dataProvider;
    private final SingleSelectionModel<EtablissementScolaireProxy> selectionModel;

    private EtablissementScolaireProxy selectedValue;

    @Inject
    public EtablissementFilterView(final EventBus eventBus, final Binder uiBinder,
                                   final Driver driver, final ModalHeader modalHeader,
                                   final SharedMessageBundle messageBundle,
                                   final CellTableStyle cellTableStyle,
                                   final ValueList valueList,
                                   final ValueListRendererFactory valueListRendererFactory,
                                   final UiHandlersStrategy<EtablissementFilterUiHandlers> uiHandlersStrategy) {
        super(eventBus, uiHandlersStrategy);

        this.driver = driver;
        this.modalHeader = modalHeader;
        this.ville = new ValueListBox<ValueListProxy>(valueListRendererFactory.create(messageBundle.allValueList()));
        this.type = new ValueListBox<EtablissementType>(new EnumRenderer<EtablissementType>(
                messageBundle.allValueList()));
        this.dataProvider = new ListDataProvider<EtablissementScolaireProxy>();
        this.selectionModel = new SingleSelectionModel<EtablissementScolaireProxy>();
        this.etablissementTable = new CellTable<EtablissementScolaireProxy>(10,cellTableStyle);

        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);
        initCellTable();

        ville.setAcceptableValues(valueList.getValueListByCode(ValueTypeCode.CITY, true));
        type.setAcceptableValues(Arrays.asList(EtablissementType.values()));
        dataProvider.addDataDisplay(etablissementTable);
        etablissementTable.setEmptyTableWidget(new EmptyResult(messageBundle.noResultFound(), AlertType.WARNING));
        etablissementTable.setSelectionModel(selectionModel);

        modalHeader.addCloseHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });

        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                selectedValue = selectionModel.getSelectedObject();
                choose.setEnabled(true);
            }
        });
    }

    @Override
    public void edit(EtablissementFilterDTOProxy object) {
        driver.edit(object);
        selectedValue = null;
        choose.setEnabled(false);
    }

    @Override
    public EtablissementFilterDTOProxy get() {
        EtablissementFilterDTOProxy object = driver.flush();
        if (driver.hasErrors()) {
            return null;
        } else {
            return object;
        }
    }

    @Override
    public void setData(List<EtablissementScolaireProxy> data) {
        dataProvider.getList().clear();
        dataProvider.getList().addAll(data);
        etablissementTable.setPageSize(data.size());
        center();
    }

    @UiHandler("search")
    void onSearchClicked(ClickEvent event) {
        getUiHandlers().search(get());
    }

    @UiHandler("choose")
    void onChooseClicked(ClickEvent event) {
        if (selectedValue != null) {
            getUiHandlers().valueSelected(selectedValue);
        }
    }

    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event) {
        hide();
    }

    private void initCellTable() {
        TextColumn<EtablissementScolaireProxy> nomColumn = new TextColumn<EtablissementScolaireProxy>() {
            @Override
            public String getValue(EtablissementScolaireProxy object) {
                return object.getNom();
            }
        };
        nomColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        etablissementTable.addColumn(nomColumn, "Nom");
        etablissementTable.setColumnWidth(nomColumn, 200, Style.Unit.PX);


        TextColumn<EtablissementScolaireProxy> villeColumn = new TextColumn<EtablissementScolaireProxy>() {
            @Override
            public String getValue(EtablissementScolaireProxy object) {
                return object.getVille().getLabel();
            }
        };
        villeColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        etablissementTable.addColumn(villeColumn, "Ville");
        etablissementTable.setColumnWidth(villeColumn, 120, Style.Unit.PX);
    }
}
