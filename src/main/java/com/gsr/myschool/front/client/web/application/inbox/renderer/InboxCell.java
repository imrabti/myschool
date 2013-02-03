/**
 * Copyright 2012 Nuvola Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gsr.myschool.front.client.web.application.inbox.renderer;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiRenderer;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.proxy.InboxProxy;
import com.gsr.myschool.common.shared.type.InboxMessageStatus;
import com.gsr.myschool.front.client.resource.FrontResources;

public class InboxCell extends AbstractCell<InboxProxy> {
    public interface Renderer extends UiRenderer {
        void render(SafeHtmlBuilder sb, String subject, String content, String date, String status);
    }

    private final Renderer uiRenderer;
    private final FrontResources resources;

    @Inject
    public InboxCell(final Renderer uiRenderer, final FrontResources resources) {
        super(BrowserEvents.CLICK);

        this.uiRenderer = uiRenderer;
        this.resources = resources;
    }

    @Override
    public void render(Context context, InboxProxy value, SafeHtmlBuilder safeHtmlBuilder) {
        String msgStyle = value.getMsgStatus() == InboxMessageStatus.READ ?
                resources.frontStyleCss().msgRead() : resources.frontStyleCss().msgUnread();

        uiRenderer.render(safeHtmlBuilder, value.getSubject(),
                value.getContent(), value.getMsgDate().toString(),
                msgStyle);
    }
}
