package com.gsr.myschool.front.client.web.application.inscription;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.TabPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;

import static com.google.gwt.query.client.GQuery.$;

public class EditInscriptionView extends ViewWithUiHandlers<EditInscriptionUiHandlers>
        implements EditInscriptionPresenter.MyView {
    public interface Binder extends UiBinder<Widget, EditInscriptionView> {
    }

    @UiField
    TabPanel steps;
    @UiField
    SimplePanel step1;
    @UiField
    SimplePanel step2;
    @UiField
    SimplePanel step3;
    @UiField
    SimplePanel step4;
    @UiField
    SimplePanel step5;
    @UiField
    Button back;
    @UiField
    Button next;
    @UiField
    Button finish;

    private WizardStep currentStep;

    @Inject
    public EditInscriptionView(final Binder uiBinder,
                               final UiHandlersStrategy<EditInscriptionUiHandlers> uiHandlers) {
        super(uiHandlers);

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
            } else if (slot == EditInscriptionPresenter.TYPE_Step_4_Content) {
                step4.setWidget(content);
            } else if (slot == EditInscriptionPresenter.TYPE_Step_5_Content) {
                step5.setWidget(content);
            }
        }
    }

    @Override
    public void goToStep(WizardStep step) {
        switch (step) {
            case STEP_1:
                next.setVisible(true);
                back.setVisible(false);
                finish.setVisible(false);
                break;
            case STEP_2:
                finish.setVisible(false);
                back.setVisible(true);
                next.setVisible(true);
                break;
            case STEP_3:
                finish.setVisible(false);
                back.setVisible(true);
                next.setVisible(true);
                break;
            case STEP_4:
                finish.setVisible(false);
                back.setVisible(true);
                next.setVisible(true);
                break;
            case STEP_5:
                back.setVisible(true);
                next.setVisible(false);
                finish.setVisible(true);
                break;
        }

        currentStep = step;
        steps.selectTab(step.ordinal());
    }

    @UiHandler("next")
    void onNextClicked(ClickEvent event) {
        WizardStep nextStep = WizardStep.value(currentStep.ordinal() + 1);
        getUiHandlers().changeStep(currentStep, nextStep);
    }

    @UiHandler("back")
    void onBackClicked(ClickEvent event) {
        WizardStep previous = WizardStep.value(currentStep.ordinal() - 1);
        goToStep(previous);
    }

    @UiHandler("finish")
    void onFinishClicked(ClickEvent event) {
    }

    private void adjustTabNavHeight() {
        $(".nav-tabs").height(0);
        $(".nav-tabs").height(steps.getOffsetHeight() - 20);
    }
}
