package com.gsr.myschool.back.client.web.application.reception.renderer;

import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.inject.assistedinject.Assisted;
import com.gsr.myschool.common.client.proxy.DossierProxy;

public interface ReceptionActionCellFactory {
    ReceptionActionCell create(@Assisted("receive") Delegate<DossierProxy> receive);
}
