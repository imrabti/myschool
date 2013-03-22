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

package com.gsr.myschool.server.job.impl;

import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.job.Worker;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("patchedProcessJob")
public class PatchedProcessJobImpl implements Worker {

    private Log logger = LogFactory.getLog("Patched Process Job");
    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void work() {
        List<Execution> list = runtimeService.createExecutionQuery().processDefinitionKey("validation").list();
        for (Execution exec : list) {
            Dossier d = (Dossier) runtimeService.getVariable(exec.getId(), "dossier");
            runtimeService.setVariable(exec.getId(), "dossierId", d.getId());
        }
    }
}
