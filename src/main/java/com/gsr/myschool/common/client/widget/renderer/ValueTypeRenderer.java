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
import com.gsr.myschool.common.client.proxy.ValueTypeProxy;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;

public class ValueTypeRenderer extends AbstractRenderer<ValueTypeProxy> {
    private final SharedMessageBundle messageBundle;

    @Inject
    public ValueTypeRenderer(final SharedMessageBundle messageBundle) {
        this.messageBundle = messageBundle;
    }

    @Override
    public String render(ValueTypeProxy valueTypeProxy) {
        if (valueTypeProxy == null) {
            return messageBundle.emptyValueList();
        }
        return valueTypeProxy.getCode().name();
    }
}
