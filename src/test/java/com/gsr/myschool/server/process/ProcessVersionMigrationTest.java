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

import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.repos.DossierRepos;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
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
        "classpath*:/META-INF/applicationContext-security.xml",
        "classpath*:/META-INF/applicationContext-mq.xml"
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
    @Autowired
    DossierRepos dossierRepos;
    @Autowired
    ValidationProcessService dossierService;

    private Log logger = LogFactory.getLog("Log");

    // @Test
    public void myTest() throws Exception {
        List<Execution> list = runtimeService.createExecutionQuery().processDefinitionKey("validation").list();
        for (Execution exec : list) {
            try {
                Dossier d = (Dossier) runtimeService.getVariable(exec.getId(), "dossier");
                runtimeService.setVariable(exec.getId(), "dossierId", d.getId());
            } catch (Exception e) {
                logger.debug("Execution ID : " + exec.getId() + "Process Instance :" + exec.getProcessInstanceId());
                try {
                   // runtimeService.deleteProcessInstance(exec.getProcessInstanceId(), "no dossier found");
                } catch (Exception e1) {
                    logger.debug("Execution ID : " + exec.getId() + "Process Instance :" + exec.getProcessInstanceId());

                }
            }
            logger.debug("FIN");
        }
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

    //@Test
    public void myOtherTest() {

        List<Execution> listE = runtimeService.createExecutionQuery().processDefinitionKey("validation").list();
        for (Execution exec : listE) {
            try{
               runtimeService.deleteProcessInstance(exec.getProcessInstanceId(), "Changing to major code");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        List<Dossier> list = dossierRepos.findAll();
        for (Dossier dossier : list) {
            if (dossier.getStatus() == DossierStatus.SUBMITTED) {
                try{
                dossierService.startProcess(dossier);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    @Test
    public void mytest() {
        Execution pi = runtimeService.createExecutionQuery().processDefinitionKey("validation").processInstanceBusinessKey("633").singleResult();
        if (pi != null) {
            runtimeService.deleteProcessInstance(pi.getProcessInstanceId(),"deleted from test");
            System.out.println("DELETED !!!");
        }
        else
            System.out.println("NADA !!!");
    }
}
