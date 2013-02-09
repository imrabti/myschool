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

import java.util.Map;

public interface ForgottenPasswordService {
    /**
     * Start the process by sending and email with a token inside, the link will be
     * added to the email with gwt parameter ";token=variable"
     *
     * if the user didn't get to complete the task with the given token in the sent email
     * the process will end in 5 days
     *
     * @param email of the user who wants to recuperate his forgotten password
     * @param link  of the host application
     * @throws Exception if velocity can't populate the email template
     */
    void startProcessWithValidEmail(String email, String link) throws Exception;

    /**
     * Starts the process with no parameters inside
     * <b>do not use (for test only)</b>
     *
     * @param email
     * @throws Exception
     */
    void startProcess(String email) throws Exception;

    /**
     * Checks if there is a task registered in activiti engine with the given token
     * it returns a HashMap<email, TaskId> describing the task id and the user's email address
     * wanting to change his password
     *
     * @param token
     * @return
     * @throws Exception if there is no task registered with the given token
     */
    Map<String, String> getTaskAndUserId(String token) throws Exception;

    /**
     * This will complete a task with the given task id
     *
     * @param taskId
     */
    void completeTask(String taskId);
}
