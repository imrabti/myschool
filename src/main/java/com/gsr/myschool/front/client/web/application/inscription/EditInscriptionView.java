package com.gsr.myschool.front.client.web.application.inscription;

import com.github.gwtbootstrap.client.ui.TabPanel;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ViewImpl;

import static com.google.gwt.query.client.GQuery.$;

public class EditInscriptionView extends ViewImpl implements EditInscriptionPresenter.MyView {
    public interface Binder extends UiBinder<Widget, EditInscriptionView> {
    }

    public enum WizardStep {
        STEP_1, STEP_2, STEP_3;
    }

    @UiField
    TabPanel steps;
    @UiField
    SimplePanel step1;
    @UiField
    SimplePanel step2;
    @UiField
    SimplePanel step3;

    @Inject
    public EditInscriptionView(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
        adjustTabNavHeight();

        steps.addShownHandler(new TabPanel.ShownEvent.Handler() {
            @Override
            public void onShow(TabPanel.ShownEvent shownEvent) {
                adjustTabNavHeight();
            }
        });
    }

    @Override
    public void setInSlot(Object slot, Widget content) {
        if (content != null) {
            if (slot == EditInscriptionPresenter.TYPE_Step_1_Content) {
                step1.setWidget(content);
            } else if (slot == EditInscriptionPresenter.TYPE_Step_2_Content) {
                step2.setWidget(content);
            } else if (slot == EditInscriptionPresenter.TYPE_Step_3_Content) {
                step3.setWidget(content);
            }
        }
    }

    private void adjustTabNavHeight() {
        $(".nav-tabs").height(0);
        $(".nav-tabs").height(steps.getOffsetHeight() + 20);
    }
}
