package com.gsr.myschool.server.process;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/applicationContext.xml",
        "classpath*:/META-INF/applicationContext-security.xml"})
public class FirstProcessTest {
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    ProcessEngine processEngine;
    @Autowired
    TaskService taskService;

    @After
    public void closeProcessEngine() {
        processEngine.close();
    }

    @Deployment
    @Test
    public void test() {
        String assignee = "Kecha";
        String email = "kecha.mohamed@gmail.com";

        // create HashMap for process variables
        Map<String, Object> processVariables = new HashMap<String, Object>();
        processVariables.put("key", "My Value");
        processVariables.put("assignee", assignee);

        // start process with process variables
        runtimeService.startProcessInstanceByKey("testing", processVariables);

        //verify the humantask
        assertEquals(1, taskService.createTaskQuery().taskAssignee(assignee).count());

        // get task for assignee
        Task task = taskService.createTaskQuery().taskAssignee(assignee).singleResult();

        // show how to retreive variables from process and to inject process variables
        runtimeService.setVariable(task.getExecutionId(), "email", email);
        String valueFromProcess = (String) runtimeService.getVariable(task.getExecutionId(), "key");

        System.out.println("\n The value from process : " + valueFromProcess + "\n");

        // complete task
        taskService.complete(task.getId());

        // check if task is completed
        assertEquals(0, taskService.createTaskQuery().taskAssignee(assignee).count());
    }
}
