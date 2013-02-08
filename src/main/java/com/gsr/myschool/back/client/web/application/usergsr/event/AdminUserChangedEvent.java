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

package com.gsr.myschool.back.client.web.application.usergsr.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.gsr.myschool.common.client.proxy.AdminUserProxy;

public class AdminUserChangedEvent extends GwtEvent<AdminUserChangedEvent.AdminUserChangedHandler> {
    public interface AdminUserChangedHandler extends EventHandler {
        void onAdminUserChanged(AdminUserChangedEvent event);
    }

    public static GwtEvent.Type<AdminUserChangedHandler> TYPE = new GwtEvent.Type<AdminUserChangedHandler>();

    private AdminUserProxy adminUser;

    public AdminUserChangedEvent() {
    }

    public AdminUserChangedEvent(AdminUserProxy adminUser) {
        this.adminUser = adminUser;
    }

    public AdminUserProxy getValueList() {
        return adminUser;
    }

    public static void fire(HasHandlers source) {
        source.fireEvent(new AdminUserChangedEvent());
    }

    public static void fire(HasHandlers source, AdminUserProxy adminUser) {
        source.fireEvent(new AdminUserChangedEvent(adminUser));
    }

    @Override
    public GwtEvent.Type<AdminUserChangedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AdminUserChangedHandler handler) {
        handler.onAdminUserChanged(this);
    }
}
