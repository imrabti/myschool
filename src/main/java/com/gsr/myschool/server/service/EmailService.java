package com.gsr.myschool.server.service;

import com.gsr.myschool.common.shared.dto.EmailDTO;
import com.gsr.myschool.common.shared.type.Email;

import java.util.Map;

public interface EmailService {
    public EmailDTO populateEmail(Email code, String to, String from, Map<String, String> params, String cc,
                                  String bcc) throws Exception;
}
