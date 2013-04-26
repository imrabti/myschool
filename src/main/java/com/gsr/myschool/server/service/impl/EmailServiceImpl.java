package com.gsr.myschool.server.service.impl;

import com.google.common.base.Strings;
import com.gsr.myschool.common.shared.dto.EmailDTO;
import com.gsr.myschool.server.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Transactional
    public void send(final EmailDTO mail) throws Exception {
        mailSender.send(new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws MessagingException {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
                message.setFrom(mail.getFrom());
                message.setTo(mail.getTo());
                if (!Strings.isNullOrEmpty(mail.getCc())) {
                    message.setCc(mail.getCc());
                }
                message.setSubject(mail.getSubject());
                message.setText(mail.getMessage(), true);
            }
        });
    }
}
