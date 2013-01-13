package com.gsr.myschool.server.business;

import com.gsr.myschool.common.shared.type.Email;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class EmailTemplate {
    @Id
    private Long id;
    @Enumerated
    private Email code;
    private String message;
    private String subject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Email getCode() {
        return code;
    }

    public void setCode(Email code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
