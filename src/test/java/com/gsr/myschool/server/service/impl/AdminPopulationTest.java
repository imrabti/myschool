package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.common.shared.type.Authority;
import com.gsr.myschool.common.shared.type.UserStatus;
import com.gsr.myschool.server.business.AdminUser;
import com.gsr.myschool.server.repos.AdminUserRepos;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:META-INF/applicationContext.xml",
        "classpath*:/META-INF/applicationContext-security.xml",
        "classpath*:/META-INF/applicationContext-activiti.xml"
})
@ActiveProfiles("default")
@TransactionConfiguration(defaultRollback = false)
public class AdminPopulationTest {
    @Autowired
    private AdminUserRepos adminUserRepos;

    @Test
    public void populateAdmin() {
        AdminUser adminUser = new AdminUser();
        adminUser.setFirstName("admin");
        adminUser.setLastName("gsr");
        adminUser.setPassword("admingsr1");
        adminUser.setCreated(new Date());
        adminUser.setStatus(UserStatus.ACTIVE);
        adminUser.setAuthority(Authority.ROLE_ADMIN);
        adminUser.setEmail("admin@gsr.ma");
        adminUserRepos.save(adminUser);
    }
}
