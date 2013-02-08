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

package com.gsr.myschool.back.client.web.application.user.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.gsr.myschool.common.client.proxy.UserProxy;

public class UserAccountChangedEvent extends GwtEvent<UserAccountChangedEvent.UserAccountChangedHandler> {
    public interface UserAccountChangedHandler extends EventHandler {
        void onUserAccountChanged(UserAccountChangedEvent event);
    }

    public static GwtEvent.Type<UserAccountChangedHandler> TYPE = new GwtEvent.Type<UserAccountChangedHandler>();

    private UserProxy adminUser;

    public UserAccountChangedEvent() {
    }

    public UserAccountChangedEvent(UserProxy adminUser) {
        this.adminUser = adminUser;
    }

    public UserProxy getValueList() {
        return adminUser;
    }

    public static void fire(HasHandlers source) {
        source.fireEvent(new UserAccountChangedEvent());
    }

    public static void fire(HasHandlers source, UserProxy adminUser) {
        source.fireEvent(new UserAccountChangedEvent(adminUser));
    }

    @Override
    public GwtEvent.Type<UserAccountChangedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(UserAccountChangedHandler handler) {
        handler.onUserAccountChanged(this);
    }
}
