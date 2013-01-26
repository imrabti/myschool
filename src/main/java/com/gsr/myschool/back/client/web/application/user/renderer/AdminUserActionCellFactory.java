package com.gsr.myschool.back.client.web.application.user.renderer;

import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.inject.assistedinject.Assisted;
import com.gsr.myschool.common.client.proxy.AdminUserProxy;

public interface AdminUserActionCellFactory {
    AdminUserActionCell create(@Assisted("editAccount") Delegate<AdminUserProxy> editAccount,
            @Assisted("editStatus") Delegate<AdminUserProxy> editStatus);
}
