package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.common.shared.dto.EmailDTO;
import com.gsr.myschool.common.shared.type.EmailType;
import com.gsr.myschool.server.business.EmailTemplate;
import com.gsr.myschool.server.repos.EmailTemplateRepos;
import com.gsr.myschool.server.service.EmailService;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private VelocityEngine velocityEngine;
    @Autowired
    private EmailTemplateRepos emailTemplateRepos;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public EmailDTO populateEmail(EmailType code, String to, String from, Map<String, Object> params, String cc,
                                  String bcc) throws Exception {
        velocityEngine.init();
        VelocityContext context = new VelocityContext();

        Iterator<Object> itValue = params.values().iterator();
        Iterator<String> itKey = params.keySet().iterator();

        while (itValue.hasNext()) {
            context.put(itKey.next(), itValue.next());
        }

        EmailTemplate emailTemplate = emailTemplateRepos.findByCode(code);
        String template = emailTemplate.getMessage();

        StringWriter contenuWriter = new StringWriter();
        velocityEngine.evaluate(context, contenuWriter, "", template);
        String message = contenuWriter.toString();

        EmailDTO resultMail = new EmailDTO();
        resultMail.setSubject(emailTemplate.getSubject());
        resultMail.setFrom(from);
        resultMail.setTo(to);
        resultMail.setCc(cc);
        resultMail.setBcc(bcc);
        resultMail.setMessage(message);

        return resultMail;
    }

    @Override
    @Async
    public void send(final EmailDTO mail) throws Exception {
        mailSender.send(new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws MessagingException {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
                message.setFrom(mail.getFrom());
                message.setTo(mail.getTo());
                message.setSubject(mail.getSubject());
                message.setText(mail.getMessage(), true);
            }
        });
    }
}
