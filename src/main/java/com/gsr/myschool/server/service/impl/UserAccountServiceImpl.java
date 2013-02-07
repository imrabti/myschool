package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.common.shared.dto.PasswordDTO;
import com.gsr.myschool.server.business.User;
import com.gsr.myschool.server.repos.UserRepos;
import com.gsr.myschool.server.security.SecurityContextProvider;
import com.gsr.myschool.server.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserAccountServiceImpl implements UserAccountService {
    @Autowired
    private SecurityContextProvider securityContextProvider;
    @Autowired
    private UserRepos userRepos;

    @Override
    public User updateAccount(User user) {
        User currentUser = securityContextProvider.getCurrentUser();
        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setEmail(user.getEmail());
        userRepos.save(currentUser);
        return currentUser;
    }

    @Override
    public void updatePassword(PasswordDTO password) {
        User currentUser = securityContextProvider.getCurrentUser();
        currentUser.setPassword(password.getPassword());
        userRepos.save(currentUser);
    }
}
