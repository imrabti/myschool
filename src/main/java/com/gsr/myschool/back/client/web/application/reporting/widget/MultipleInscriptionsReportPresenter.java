package com.gsr.myschool.back.client.web.application.reporting.widget;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gsr.myschool.back.client.web.application.reporting.widget.MultipleInscriptionsReportPresenter.MyView;

public class MultipleInscriptionsReportPresenter extends PresenterWidget<MyView>
        implements MultipleInscriptionsReportUiHandlers  {
    public interface MyView extends View, HasUiHandlers<MultipleInscriptionsReportUiHandlers> {
    }

    @Inject
    public MultipleInscriptionsReportPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);

        getView().setUiHandlers(this);
    }
}
