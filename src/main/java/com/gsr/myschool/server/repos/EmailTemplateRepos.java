package com.gsr.myschool.server.repos;

import com.gsr.myschool.common.shared.type.EmailType;
import com.gsr.myschool.server.business.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailTemplateRepos extends JpaRepository<EmailTemplate, Long> {
    public EmailTemplate findByCode(EmailType code);
}
