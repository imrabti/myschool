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

package com.gsr.myschool.server.process;

import com.gsr.myschool.common.shared.dto.EmailDTO;

public interface RegisterProcessNestedService {
    void deleteAccount(Long id);

    EmailDTO getActivationMail(Long id, EmailDTO email) throws Exception;

    EmailDTO getRelanceMail(Long id, String token, EmailDTO email) throws Exception;

    void sendRelanceMail(Long id, String link, EmailDTO email);

    void sendActivationMail(Long id, EmailDTO email);

    void sendActivationMailAgain(EmailDTO email);
}
