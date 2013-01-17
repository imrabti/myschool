package com.gsr.myschool.front.client.web.application.inscription.renderer;

import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.inject.assistedinject.Assisted;
import com.gsr.myschool.front.client.request.proxy.InscriptionProxy;

public interface InscriptionActionCellFactory {
    InscriptionActionCell create(@Assisted("preview") Delegate<InscriptionProxy> preview,
                                 @Assisted("edit") Delegate<InscriptionProxy> edit,
                                 @Assisted("delete") Delegate<InscriptionProxy> delete,
                                 @Assisted("submit") Delegate<InscriptionProxy> submit,
                                 @Assisted("print") Delegate<InscriptionProxy> print);
}
