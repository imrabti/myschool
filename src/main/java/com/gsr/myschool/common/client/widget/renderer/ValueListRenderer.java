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

package com.gsr.myschool.common.client.widget.renderer;

import com.google.gwt.text.shared.AbstractRenderer;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.gsr.myschool.common.client.proxy.ValueListProxy;

public class ValueListRenderer extends AbstractRenderer<ValueListProxy> {
    private String emptyMessage;

    @Inject
    public ValueListRenderer(@Assisted String emptyMessage) {
        this.emptyMessage = emptyMessage;
    }

    @Override
    public String render(ValueListProxy valueListProxy) {
        if (valueListProxy == null) {
            return emptyMessage;
        }
        return valueListProxy.getLabel();
    }
}
