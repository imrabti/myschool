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

import com.gsr.myschool.common.shared.type.EmailType;
import com.gsr.myschool.server.business.EmailTemplate;
import com.gsr.myschool.server.repos.EmailTemplateRepos;
import com.gsr.myschool.server.repos.UserRepos;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"" +
        "classpath*:META-INF/applicationContext.xml",
        "classpath*:META-INF/applicationContext-activiti.xml",
        "classpath*:/META-INF/applicationContext-security.xml"
})
@ActiveProfiles("test")
public class ForgottenPasswordServiceTest {
    @Autowired
    private ForgottenPasswordService forgottenPasswordService;
    @Autowired
    private EmailTemplateRepos emailTemplateRepos;

    @Before
    public void startProcess() throws Exception {
        EmailTemplate email = new EmailTemplate();
        email.setId(4L);
        email.setCode(EmailType.FORGOTTEN_PASSWORD);
        email.setMessage("<html><body> hello ," +
                "<br> to complete your registration please visit this url: $link " +
                "<br> best regards," +
                "<br> myschool.com </body></html>");
        email.setSubject("Relance myschool");
        emailTemplateRepos.save(email);
    }

    @Test
    public void checkService() throws Exception {
        forgottenPasswordService.startProcessWithValidEmail("kecha.mohamed@gmail.com", "127.0.0.1");
    }
}
