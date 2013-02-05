package com.gsr.myschool.server.repos;

import com.gsr.myschool.common.shared.type.InboxMessageStatus;
import com.gsr.myschool.server.business.InboxMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InboxMessageRepos extends JpaRepository<InboxMessage, Long> {
    List<InboxMessage> findByParentUser_id(Long parentUserId);

    List<InboxMessage> findByParentUser_idAndMsgStatus(Long parentUserId, InboxMessageStatus status);
}
