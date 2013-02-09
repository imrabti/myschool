package com.gsr.myschool.server.business;

import com.gsr.myschool.common.shared.type.InboxMessageStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
public class InboxMessage implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User parentUser;
    private String subject;
    private String content;
    private Date msgDate;
    @Enumerated
    private InboxMessageStatus msgStatus;
    @Transient
    private String rawContent;

    public Long getId() {
        return id;
    }

    public void setId(Long newId) {
        this.id = newId;
    }

    public User getParentUser() {
        return parentUser;
    }

    public void setParentUser(User newUser) {
        this.parentUser = newUser;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String newSubject) {
        this.subject = newSubject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String newContent) {
        this.content = newContent;
    }

    public Date getMsgDate() {
        return msgDate;
    }

    public void setMsgDate(Date newMsgDate) {
        this.msgDate = newMsgDate;
    }

    public InboxMessageStatus getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(InboxMessageStatus newMsgStatus) {
        this.msgStatus = newMsgStatus;
    }

    public String getRawContent() {
        return rawContent;
    }

    public void setRawContent(String rawContent) {
        this.rawContent = rawContent;
    }
}
