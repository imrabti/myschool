package com.gsr.myschool.server.process;

import com.gsr.myschool.common.shared.dto.EmailDTO;
import com.gsr.myschool.server.business.User;

public interface RegisterProcessService {
    public void register(User user) throws Exception;

    public void deleteAccount(Long id);

    public EmailDTO getActivationMail(Long id, EmailDTO email) throws Exception;

    public EmailDTO getRelanceMail(Long id, String token, EmailDTO email) throws Exception;

    public void acctivateAccount(String token) throws Exception;

    void register(User user, String token) throws Exception;
}
