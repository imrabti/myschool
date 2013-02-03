package com.gsr.myschool.server.process;

import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.business.User;
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
    private TaskService taskService;
    @Autowired
    private UserRepos userRepos;

    @Before
    public void startProcess() throws Exception {
        dossier.setId(1l);
        user.setId(1L);
        user.setEmail("kecha.mohamed@gmail.com");
        userRepos.save(user);

        dossier.setOwner(user);
    }

    @Test
    public void checkService() {
        validationProcessService.startProcess(dossier);

        Map x = validationProcessService.getAllNonReceivedDossiers();

        for (Object task : x.values()) {
            System.out.println(((Task) task).getId());
            System.out.println(x.get(task));
        }
    }
}
