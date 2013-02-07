package com.gsr.myschool.server.process;

import com.gsr.myschool.common.shared.type.EmailType;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.business.EmailTemplate;
import com.gsr.myschool.server.business.User;
import com.gsr.myschool.server.repos.EmailTemplateRepos;
import com.gsr.myschool.server.repos.UserRepos;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static java.lang.Thread.sleep;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:META-INF/applicationContext.xml",
        "classpath*:META-INF/applicationContext-activiti.xml",
        "classpath*:/META-INF/applicationContext-security.xml"
})
@ActiveProfiles("test")
public class ValidationProcessTest {
    Dossier dossier = new Dossier();
    User user = new User();
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private ValidationProcessService validationProcessService;
    @Autowired
    private EmailTemplateRepos emailTemplateRepos;
    @Autowired
    private UserRepos userRepos;

    @Before
    public void startProcess() throws Exception {
        dossier.setId(1l);
        user.setId(1L);
        user.setEmail("kecha.mohamed@gmail.com");
        user.setFirstName("mohamed");
        user.setLastName("kecha");
//        userRepos.save(user);

        dossier.setOwner(user);
        dossier.setGeneratedNumDossier("GSR_2013_hash");

        // populate the email templates
        EmailTemplate email = new EmailTemplate();
        email.setId(3L);
        email.setCode(EmailType.DOSSIER_RECEIVED);
        email.setMessage("<html><body> hello $firstname $lastname," +
                "<br> your dossier with ref : $refdossier was received ..." +
                "<br> myschool.com </body></html>");
        email.setSubject("Reception dossier myschool");
        emailTemplateRepos.save(email);
    }

    @Test
    public void checkService() throws InterruptedException {
        validationProcessService.startProcess(dossier);

        Map<Dossier, Task> x = validationProcessService.getAllNonReceivedDossiers();

        for (Task task : x.values()) {
            System.out.println(task.getId());
            validationProcessService.receiveDossier(task);
        }
        sleep(100000);
    }
}
