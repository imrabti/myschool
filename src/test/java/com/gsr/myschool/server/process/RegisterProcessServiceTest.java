package com.gsr.myschool.server.process;

import com.gsr.myschool.common.shared.type.EmailType;
import com.gsr.myschool.common.shared.type.UserStatus;
import com.gsr.myschool.server.business.EmailTemplate;
import com.gsr.myschool.server.business.User;
import com.gsr.myschool.server.repos.EmailTemplateRepos;
import com.gsr.myschool.server.repos.UserRepos;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"" +
        "classpath*:META-INF/applicationContext.xml",
        "classpath*:META-INF/applicationContext-activiti.xml",
        "classpath*:/META-INF/applicationContext-security.xml"
})
public class RegisterProcessServiceTest {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RegisterProcessService registerProcessService;
    @Autowired
    private EmailTemplateRepos emailTemplateRepos;
    @Autowired
    private UserRepos userRepos;
    private User user = new User();
    private String mail = "kecha.mohamed@gmail.com";
    private String fname = "mohamed";
    private String lname = "kecha";

  //  @Test
    public void init() {
        // populate the email templates
        EmailTemplate email = new EmailTemplate();
        email.setId(1L);
        email.setCode(EmailType.REGISTRATION);
        email.setMessage("<html><body> hello $firstname $lastname," +
                "<br> to complete your registration please visit this url : $link <br> best regards, " +
                "<br> myschool.com</body></html>");
        email.setSubject("registration myschool");
        emailTemplateRepos.save(email);

        email.setId(2L);
        email.setCode(EmailType.ACTIVATION);
        email.setMessage("<html><body> hello $firstname $lastname," +
                "<br> Your account has been succesfully activated, " +
                "<br> best regards, <br> myschool.com</body></html>");
        email.setSubject("Account activated myschool");
        emailTemplateRepos.save(email);

        email.setId(3L);
        email.setCode(EmailType.RELANCE);
        email.setMessage("<html><body> hello $firstname $lastname," +
                "<br> to complete your registration please visit this url: $link " +
                "<br> best regards," +
                "<br> <b>You have two days to do so, otherwise your account will be deleted </b> " +
                "<br> myschool.com </body></html>");
        email.setSubject("Relance myschool");
        emailTemplateRepos.save(email);

        // after normal registration the user would be in database
        user.setStatus(UserStatus.INACTIVE);
        user.setEmail(mail);
        user.setId(1L);
        user.setFirstName(fname);
        user.setLastName(lname);
        userRepos.save(user);
    }

//    @After
//    public void terminate() {
//        userRepos.deleteAll();
//        emailTemplateRepos.deleteAll();
//    }

    // tests the second scenario in with the user activates his account
//    @Test
//    @Deployment
//    public void testScenarion2() throws Exception {
//        String token = "test";
//        registerProcessService.register(user, token);
//        registerProcessService.activateAccount(token);
//        Thread.sleep(10000);
//    }

    // tests the first scenario witch sends another mail, and finaly deletes the account
//    @Test
//    @Deployment
//    public void testScenario1() throws Exception {
//        user.setId(2L);
//        userRepos.save(user);
//        registerProcessService.register(user);
//        Thread.sleep(40000);
//    }

    @Test
    public void myTest() throws Exception {
        user.setStatus(UserStatus.INACTIVE);
        user.setEmail(mail);
        user.setId(9L);
        user.setFirstName(fname);
        user.setLastName(lname);
        userRepos.save(user);

        registerProcessService.register(user);

        List<Execution> executions = runtimeService.createExecutionQuery()
                .signalEventSubscriptionName("mailNotReceived")
                .list();

        registerProcessService.mailNotReceived(user);
        Thread.sleep(10000);
        registerProcessService.mailNotReceived(user);
    }
}
