package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.common.shared.dto.EmailDTO;
import com.gsr.myschool.common.shared.type.EmailType;
import com.gsr.myschool.server.business.EmailTemplate;
import com.gsr.myschool.server.repos.EmailTemplateRepos;
import com.gsr.myschool.server.service.EmailPreparatorService;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

@Service
public class EmailPreparatorServiceImpl implements EmailPreparatorService {
    @Autowired
    private VelocityEngine velocityEngine;
    @Autowired
    private EmailTemplateRepos emailTemplateRepos;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${dev.mode}")
    private String devMode;
    @Value("${dev.mail.to}")
    private String devEmailTo;
    @Value("${dev.mail.cc}")
    private String devEmailCc;
    @Value("${dev.mail.bcc}")
    private String devEmailBcc;

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

        if ("true".equals(devMode)) {
            resultMail.setTo(devEmailTo);
            resultMail.setCc(devEmailCc);
            resultMail.setBcc(devEmailBcc);
        } else {
            resultMail.setTo(to);
            resultMail.setCc(cc);
            resultMail.setBcc(bcc);
        }

        resultMail.setSubject(emailTemplate.getSubject());
        resultMail.setFrom(from);
        resultMail.setMessage(message);

        return resultMail;
    }

    @Override
    @Transactional
    public void prepare(final EmailDTO mail) throws Exception {
        rabbitTemplate.convertAndSend(mail);
    }
}
