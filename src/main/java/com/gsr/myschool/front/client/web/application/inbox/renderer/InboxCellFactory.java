package com.gsr.myschool.front.client.web.application.inbox.renderer;

import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.inject.assistedinject.Assisted;
import com.gsr.myschool.common.client.proxy.InboxProxy;

public interface InboxCellFactory {
    InboxCell create(@Assisted Delegate<InboxProxy> showDetails);
}
