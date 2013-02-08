package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ValuePicker;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ValidatedViewImpl;
import com.gsr.myschool.common.client.mvp.ValidationErrorPopup;
import com.gsr.myschool.common.client.proxy.InfoParentProxy;
import com.gsr.myschool.common.client.resource.style.TabsListStyle;
import com.gsr.myschool.common.client.ui.dossier.ParentEditor;
import com.gsr.myschool.common.client.widget.renderer.EnumCell;
import com.gsr.myschool.common.shared.type.ParentType;

import java.util.Arrays;

public class ParentView extends ValidatedViewImpl implements ParentPresenter.MyView  {
    public interface Binder extends UiBinder<Widget, ParentView> {
    }

    @UiField(provided = true)
    ValuePicker<ParentType> tabs;
    @UiField
    SimplePanel tabContent;

    private final ParentEditor pereEditor;
    private final ParentEditor mereEditor;
    private final ParentEditor tuteurEditor;

    @Inject
    public ParentView(final Binder uiBinder, final ValidationErrorPopup validationErrorPopup,
                      final TabsListStyle listStyle, final ParentEditor parentEditor,
                      final ParentEditor mereEditor, final ParentEditor tuteurEditor) {
        super(validationErrorPopup);

        this.pereEditor = parentEditor;
        this.mereEditor = mereEditor;
        this.tuteurEditor = tuteurEditor;

        CellList<ParentType> tabsCell = new CellList<ParentType>(new EnumCell<ParentType>(), listStyle);
        tabs = new ValuePicker<ParentType>(tabsCell);

        initWidget(uiBinder.createAndBindUi(this));
        showEditor(ParentType.PERE);

        tabs.setValue(ParentType.PERE);
        tabs.setAcceptableValues(Arrays.asList(ParentType.values()));
    }

    @Override
    public void showEditor(ParentType parentType) {
        switch (parentType) {
            case PERE:
                tabs.setValue(ParentType.PERE);
                tabContent.setWidget(pereEditor);
                break;
            case MERE:
                tabs.setValue(ParentType.MERE);
                tabContent.setWidget(mereEditor);
                break;
            case TUTEUR:
                tabs.setValue(ParentType.TUTEUR);
                tabContent.setWidget(tuteurEditor);
                break;
        }
    }

    public void editPere(InfoParentProxy pere) {
        pereEditor.edit(pere);
    }

    public void editMere(InfoParentProxy mere) {
        mereEditor.edit(mere);
    }

    public void editTuteur(InfoParentProxy tuteur) {
        tuteurEditor.edit(tuteur);
    }

    public void flushPere() {
        pereEditor.get();
    }

    public void flushMere() {
        mereEditor.get();
    }

    public void flushTuteur() {
        tuteurEditor.get();
    }

    @UiHandler("tabs")
    void onTabsChanged(ValueChangeEvent<ParentType> event) {
        showEditor(tabs.getValue());
    }
}
