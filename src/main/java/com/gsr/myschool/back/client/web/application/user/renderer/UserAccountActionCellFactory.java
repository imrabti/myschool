package com.gsr.myschool.back.client.web.application.user.renderer;

import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.inject.assistedinject.Assisted;
import com.gsr.myschool.common.client.proxy.UserProxy;

public interface UserAccountActionCellFactory {
    UserAccountActionCell create(@Assisted("editAccount") Delegate<UserProxy> editAccount);
}
