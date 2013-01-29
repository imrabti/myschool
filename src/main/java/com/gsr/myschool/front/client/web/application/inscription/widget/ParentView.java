package com.gsr.myschool.front.client.web.application.inscription.widget;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ValidatedViewImpl;
import com.gsr.myschool.common.client.mvp.ValidationErrorPopup;
import com.gsr.myschool.common.client.proxy.InfoParentProxy;
import com.gsr.myschool.common.client.ui.dossier.ParentEditor;

public class ParentView extends ValidatedViewImpl implements ParentPresenter.MyView  {
    public interface Binder extends UiBinder<Widget, ParentView> {
    }

    @UiField(provided = true)
    ParentEditor parentEditor;

    @Inject
    public ParentView(final Binder uiBinder, final ValidationErrorPopup validationErrorPopup,
                      final ParentEditor parentEditor) {
        super(validationErrorPopup);

        this.parentEditor = parentEditor;

        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void editParent(InfoParentProxy infoParent) {
        parentEditor.edit(infoParent);
    }

    @Override
    public void flushParent() {
        parentEditor.get();
    }
}
