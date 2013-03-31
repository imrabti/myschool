package com.gsr.myschool.server.service;

import com.gsr.myschool.common.shared.dto.EmailDTO;

public interface EmailService {
    void send(EmailDTO mail) throws Exception;
}
