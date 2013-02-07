package com.gsr.myschool.server.service;

import com.gsr.myschool.common.shared.dto.PasswordDTO;
import com.gsr.myschool.server.business.User;

public interface UserAccountService {
    User updateAccount(User user);

    void updatePassword(PasswordDTO password);
}
