package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.common.shared.type.Authority;
import com.gsr.myschool.server.business.User;
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

    @Override
    public void register(User user) {
        user.setAuthority(Authority.ROLE_USER);
        userRepos.save(user);
    }
}
