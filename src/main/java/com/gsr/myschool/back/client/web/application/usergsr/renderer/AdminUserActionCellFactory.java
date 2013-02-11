package com.gsr.myschool.back.client.web.application.usergsr.renderer;

import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.inject.assistedinject.Assisted;
import com.gsr.myschool.common.client.proxy.AdminUserProxy;

public interface AdminUserActionCellFactory {
    AdminUserActionCell create(@Assisted("editAccount") Delegate<AdminUserProxy> editAccount,
                               @Assisted("delete") Delegate<AdminUserProxy> editStatus);
}
