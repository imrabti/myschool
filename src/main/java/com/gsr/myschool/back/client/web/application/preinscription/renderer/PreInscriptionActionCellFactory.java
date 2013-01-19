package com.gsr.myschool.back.client.web.application.preinscription.renderer;

import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.inject.assistedinject.Assisted;
import com.gsr.myschool.common.client.proxy.DossierProxy;

public interface PreInscriptionActionCellFactory {
    PreInscriptionActionCell create(@Assisted("viewDetails") Delegate<DossierProxy> viewDetails);
}
