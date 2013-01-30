package com.gsr.myschool.front.client.web.application.inscription.renderer;

import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.inject.assistedinject.Assisted;
import com.gsr.myschool.common.client.proxy.DossierProxy;

public interface InscriptionActionCellFactory {
    InscriptionActionCell create(@Assisted("preview") Delegate<DossierProxy> preview,
                                 @Assisted("edit") Delegate<DossierProxy> edit,
                                 @Assisted("delete") Delegate<DossierProxy> delete,
                                 @Assisted("submit") Delegate<DossierProxy> submit,
                                 @Assisted("print") Delegate<DossierProxy> print);
}
