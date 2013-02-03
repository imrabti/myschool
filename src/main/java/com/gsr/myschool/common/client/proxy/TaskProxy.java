package com.gsr.myschool.common.client.proxy;

public interface TaskProxy {
    java.lang.String getId();

    java.lang.String getName();

    void setName(java.lang.String s);

    java.lang.String getDescription();

    void setDescription(java.lang.String s);

    int getPriority();

    void setPriority(int i);

    java.lang.String getOwner();

    void setOwner(java.lang.String s);

    java.lang.String getAssignee();

    void setAssignee(java.lang.String s);

    org.activiti.engine.task.DelegationState getDelegationState();

    void setDelegationState(org.activiti.engine.task.DelegationState delegationState);

    java.lang.String getProcessInstanceId();

    java.lang.String getExecutionId();

    java.lang.String getProcessDefinitionId();

    java.util.Date getCreateTime();

    java.lang.String getTaskDefinitionKey();

    java.util.Date getDueDate();

    void setDueDate(java.util.Date date);

    void delegate(java.lang.String s);

    void setParentTaskId(java.lang.String s);

    java.lang.String getParentTaskId();

    boolean isSuspended();
}
