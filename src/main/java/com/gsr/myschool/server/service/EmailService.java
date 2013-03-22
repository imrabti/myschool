package com.gsr.myschool.server.service;

import com.gsr.myschool.common.shared.dto.EmailDTO;
import com.gsr.myschool.common.shared.type.EmailType;

import java.util.Map;

public interface EmailService {
    EmailDTO populateEmail(EmailType code, String to, String from, Map<String, Object> params, String cc,
                                  String bcc) throws Exception;

    void send(EmailDTO mail) throws Exception;
}
