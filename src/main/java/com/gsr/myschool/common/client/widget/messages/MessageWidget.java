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

package com.gsr.myschool.common.client.widget.messages;

import com.github.gwtbootstrap.client.ui.Alert;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class MessageWidget extends Alert {
    private final Message message;
    private Timer closeTimer = new Timer() {
        @Override
        public void run() {
            close();
        }
    };

    @Inject
    public MessageWidget(@Assisted Message message) {
        super();

        this.message = message;

        initContent();
        initTimeout();
    }

    public Message getMessage() {
        return message;
    }

    private void initContent() {
        setText(message.getMessage().asString());
        setClose(message.useDefaultCloseButton());
        setType(message.getMessageStyle());
        setAnimation(true);
    }

    private void initTimeout() {
        if (!message.getCloseDelay().equals(CloseDelay.NEVER)) {
            closeTimer.schedule(message.getCloseDelay().getDelay());
        }
    }
}

