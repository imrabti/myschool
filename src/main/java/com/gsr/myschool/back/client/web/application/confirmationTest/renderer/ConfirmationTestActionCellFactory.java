package com.gsr.myschool.back.client.web.application.confirmationTest.renderer;

import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.inject.assistedinject.Assisted;
import com.gsr.myschool.common.client.proxy.DossierProxy;

public interface ConfirmationTestActionCellFactory {
    ConfirmationTestActionCell create(@Assisted("accept") Delegate<DossierProxy> accept,
			@Assisted("deny") Delegate<DossierProxy> deny,
			@Assisted("viewDetails") Delegate<DossierProxy> viewDetails);
}
