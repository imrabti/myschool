package com.gsr.myschool.server.business;

import com.gsr.myschool.common.shared.type.InboxMessageStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
public class InboxMessage implements java.io.Serializable {
    @ManyToOne
    private User parentUser;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String subject;
    private String content;
    private Date msgDate;
    @Enumerated
    private InboxMessageStatus msgStatus;

    public User getParentUser() {
        return parentUser;
    }

    public void setParentUser(User newUser) {
        this.parentUser = newUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int newId) {
        this.id = newId;
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
}