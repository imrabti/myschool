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

package com.gsr.myschool.front.client.web.application.inbox;

import com.gsr.myschool.common.client.proxy.InboxProxy;
import com.gsr.myschool.common.shared.type.InboxMessageStatus;
import com.gwtplatform.mvp.client.UiHandlers;

import java.util.List;

public interface InboxUiHandlers extends UiHandlers {
    void delete(List<InboxProxy> toDelete);

    void update(List<InboxProxy> toUpdate, InboxMessageStatus status);

    void showDetails(InboxProxy value);
}
