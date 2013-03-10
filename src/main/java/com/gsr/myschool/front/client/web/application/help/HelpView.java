package com.gsr.myschool.front.client.web.application.help;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ViewImpl;
import com.gsr.myschool.front.client.web.application.help.ui.HelpTutorial;

public class HelpView extends ViewImpl implements HelpPresenter.MyView {
    public interface Binder extends UiBinder<Widget, HelpView> {
    }

    final HelpTutorial helpTutorial;

    @Inject
    public HelpView(final Binder uiBinder, final HelpTutorial helpTutorial) {
        this.helpTutorial = helpTutorial;

        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("tutorial")
    void onTutorialClicked(ClickEvent event) {
        helpTutorial.show();
        helpTutorial.center();
    }
}
