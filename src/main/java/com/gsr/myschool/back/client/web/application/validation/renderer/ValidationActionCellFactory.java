package com.gsr.myschool.back.client.web.application.validation.renderer;

import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.inject.assistedinject.Assisted;
import com.gsr.myschool.common.client.proxy.DossierProxy;

public interface ValidationActionCellFactory {
    ValidationActionCell create(@Assisted("verify") Delegate<DossierProxy> receive,
            @Assisted("viewDetails") Delegate<DossierProxy> viewDetails);
}
