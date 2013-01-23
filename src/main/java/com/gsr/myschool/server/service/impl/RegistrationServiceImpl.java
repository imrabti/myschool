package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.common.shared.type.Authority;
import com.gsr.myschool.server.business.User;
import com.gsr.myschool.server.process.RegisterProcessService;
import com.gsr.myschool.server.repos.UserRepos;
import com.gsr.myschool.server.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    private UserRepos userRepos;
    @Autowired
    private RegisterProcessService registerProcessService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean register(User user, String link) {
        try {
            user.setAuthority(Authority.ROLE_USER);
            user.setActive(false);
            user = userRepos.save(user);
            registerProcessService.register(user, link);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean activateAccount(String token) {
        try {
            registerProcessService.activateAccount(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
