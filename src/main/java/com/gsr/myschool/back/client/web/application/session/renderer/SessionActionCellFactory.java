package com.gsr.myschool.back.client.web.application.session.renderer;

import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.inject.assistedinject.Assisted;
import com.gsr.myschool.common.client.proxy.SessionExamenProxy;

public interface SessionActionCellFactory {
    SessionActionCell create(@Assisted("update") Delegate<SessionExamenProxy> update,
                             @Assisted("close")  Delegate<SessionExamenProxy> close,
                             @Assisted("cancel") Delegate<SessionExamenProxy> cancel,
                             @Assisted("remove") Delegate<SessionExamenProxy> remove);
}
