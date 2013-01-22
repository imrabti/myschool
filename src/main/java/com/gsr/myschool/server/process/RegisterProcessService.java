package com.gsr.myschool.server.process;

import com.gsr.myschool.common.shared.dto.EmailDTO;
import com.gsr.myschool.server.business.User;

public interface RegisterProcessService {
    void register(User user) throws Exception;

    void deleteAccount(Long id);

    EmailDTO getActivationMail(Long id, EmailDTO email) throws Exception;

    EmailDTO getRelanceMail(Long id, String token, EmailDTO email) throws Exception;

    void activateAccount(String token) throws Exception;

    void register(User user, String link) throws Exception;
}
