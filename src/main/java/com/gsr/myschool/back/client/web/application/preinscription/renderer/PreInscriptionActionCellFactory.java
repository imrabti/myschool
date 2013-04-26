package com.gsr.myschool.back.client.web.application.preinscription.renderer;

import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.inject.assistedinject.Assisted;
import com.gsr.myschool.common.client.proxy.DossierProxy;

public interface PreInscriptionActionCellFactory {
    PreInscriptionActionCell create(@Assisted("viewDetails") Delegate<DossierProxy> viewDetails,
                                    @Assisted("print") Delegate<DossierProxy> print,
                                    @Assisted("delete") Delegate<DossierProxy> delete,
                                    @Assisted("printConvocation") Delegate<DossierProxy> printConvocation,
                                    @Assisted("sendConvocation") Delegate<DossierProxy> sendConvocation);
}
