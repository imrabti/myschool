package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.common.shared.dto.ForgotPasswordDTO;
import com.gsr.myschool.common.shared.dto.ResetPasswordDTO;
import com.gsr.myschool.common.shared.type.Authority;
import com.gsr.myschool.common.shared.type.UserStatus;
import com.gsr.myschool.server.business.User;
import com.gsr.myschool.server.process.ForgottenPasswordService;
import com.gsr.myschool.server.process.RegisterProcessService;
import com.gsr.myschool.server.repos.UserRepos;
import com.gsr.myschool.server.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    private UserRepos userRepos;
    @Autowired
    private RegisterProcessService registerProcessService;
    @Autowired
    private ForgottenPasswordService forgottenPasswordService;

    @Override
    public Boolean register(User user, String link) {
        try {
            user.setAuthority(Authority.ROLE_USER);
            user.setStatus(UserStatus.INACTIVE);;
            user = userRepos.save(user);
            registerProcessService.register(user, link);
            return true;
        } catch (Exception e) {
            userRepos.delete(user);
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

    @Override
    public Boolean startForgotPasswordProcess(ForgotPasswordDTO forgotPassword, String link) {
        try {
            forgottenPasswordService.startProcessWithValidEmail(forgotPassword.getEmail(), link);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String verifyForgotPassword(String token) {
        try {
            Map<String, String> forgotTask = forgottenPasswordService.getTaskAndUserId(token);
            return (String) forgotTask.keySet().toArray()[0];
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean resetPassword(ResetPasswordDTO resetPassword, String token, String email) {
        try {
            Map<String, String> forgotTask = forgottenPasswordService.getTaskAndUserId(token);
            forgottenPasswordService.completeTask(forgotTask.get(email));

            User user = userRepos.findByEmail(email);
            user.setPassword(resetPassword.getPassword());
            userRepos.save(user);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
