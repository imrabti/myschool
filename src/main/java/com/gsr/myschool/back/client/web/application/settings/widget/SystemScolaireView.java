package com.gsr.myschool.back.client.web.application.settings.widget;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.back.client.web.application.settings.renderer.NiveauEtudeInfosTreeFactory;
import com.gsr.myschool.back.client.web.application.settings.widget.SystemScolairePresenter.MyView;
import com.gsr.myschool.common.client.mvp.ViewWithUiHandlers;
import com.gsr.myschool.common.client.mvp.uihandler.UiHandlersStrategy;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;

public class SystemScolaireView extends ViewWithUiHandlers<SystemScolaireUiHandlers> implements MyView {
    public interface Binder extends UiBinder<Widget, SystemScolaireView> {
    }

    @UiField(provided = true)
    CellTree myTree;

    @Inject
    public SystemScolaireView(final Binder uiBinder,
                              final UiHandlersStrategy<SystemScolaireUiHandlers> uiHandlersStrategy,
                              final NiveauEtudeInfosTreeFactory niveauEtudeInfosTreeFactory) {
        super(uiHandlersStrategy);

        myTree = new CellTree(niveauEtudeInfosTreeFactory.create(setupShowDetails()), null);

        initWidget(uiBinder.createAndBindUi(this));
    }

    private ActionCell.Delegate<NiveauEtudeProxy> setupShowDetails(){
        return new ActionCell.Delegate<NiveauEtudeProxy>() {
            @Override
            public void execute(NiveauEtudeProxy object) {
                getUiHandlers().showDetails(object);
            }
        };
    }
}
