/**
 * Copyright 2012 Nuvola Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gsr.myschool.server.process;

import com.gsr.myschool.server.business.Dossier;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.runtime.Execution;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:META-INF/applicationContext.xml",
        "classpath*:META-INF/applicationContext-activiti.xml",
        "classpath*:/META-INF/applicationContext-security.xml"
})
public class ProcessVersionMigrationTest {
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    ProcessEngineConfigurationImpl processEngineConfiguration;
    @Autowired
    TaskService taskService;

    private Log logger = LogFactory.getLog("Log");

    @Test
    public void myTest() throws Exception {
        List<Execution> list = runtimeService.createExecutionQuery().processDefinitionKey("validation").list();
        for (Execution exec : list) {
            try {
                Dossier d = (Dossier) runtimeService.getVariable(exec.getId(), "dossier");
                runtimeService.setVariable(exec.getId(), "dossierId", d.getId());
            } catch (Exception e) {
                logger.debug("Execution ID : " + exec.getId() + "Process Instance :" + exec.getProcessInstanceId());

                   try{
                      runtimeService.deleteProcessInstance(exec.getProcessInstanceId(), "no dossier found");
                   } catch (Exception e1) {
                       logger.debug(e1.getMessage());
                   }

                   }
             }
        logger.debug("FINSHED MIGRATION");
//        runtimeService.startProcessInstanceByKey("validation","1");

//        JdbcTemplate template = new JdbcTemplate(dataSource);
//        List<Execution> list = runtimeService.createExecutionQuery().processDefinitionKey("validation").list();
//        Execution w = runtimeService.createExecutionQuery().processVariableValueEquals("dossierId", 2L).singleResult();
//        Task t = taskService.createTaskQuery().processVariableValueEquals("dossierId", 2L).taskDefinitionKey("validationTask").singleResult();
//        for (Execution exec : list) {
//            Dossier d = (Dossier) runtimeService.getVariable(exec.getId(), "dossier");
//            runtimeService.setVariable(exec.getId(), "dossierId", d.getId());

//
//           // processEngineConfiguration.getCommandExecutorTxRequired().execute(new SetProcessDefinitionVersionCmd(exec.getId(), 2));
//        }
    }
}
