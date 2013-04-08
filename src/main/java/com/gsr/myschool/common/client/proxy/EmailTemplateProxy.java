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

package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.common.shared.type.EmailType;
import com.gsr.myschool.server.business.EmailTemplate;

import java.util.Date;

@ProxyFor(EmailTemplate.class)
public interface EmailTemplateProxy extends ValueProxy {
    Long getId();

    void setId(Long id);

    EmailType getCode();

    void setCode(EmailType code);

    String getMessage();

    void setMessage(String message);

    String getSubject();

    void setSubject(String subject);

    Date getCreated();

    void setCreated(Date created);

    Date getUpdated();

    void setUpdated(Date updated);

    String getDefinition();

    void setDefinition(String definition);
}
