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

import com.gsr.myschool.common.shared.dto.PiecejustifDTO;
import com.gsr.myschool.server.business.Dossier;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

public interface ValidationProcessService {
    /**
     * Process starts when a parent submit a pre-inscription Dossier
     * this will create a new Task waiting for the arrival of the physical Dossier
     *
     * @param dossier
     */
    void startProcess(Dossier dossier);

    /**
     * Returns all the Dossier not received yet
     *
     * @return
     */
    Map<Dossier, Task> getAllNonReceivedDossiers();

    /**
     * Finish the Task
     *
     * @param task
     * @param piecejustifDTOs
     */
    void receiveDossier(Task task, List<PiecejustifDTO> piecejustifDTOs);

    /**
     * Returns the list of the Dossier that had been received,
     * the user should validate the arrival of all the files to proceed with an analyse
     *
     * @return
     */
    Map<Dossier, Task> getAllReceivedDossiers();

    /**
     * Finish the validation of the Dossier task after checking the arrival
     * of all files required to this apply the folder with a REJECTED status
     * and turn it into WAITING this action will send an email to the parent
     * with the rest of files needed
     *
     * @param task
     * @param pieceNonavailable
     */
    void rejectDossier(Task task, List<PiecejustifDTO> pieceNonavailable);

    /**
     * Finish the validation of the Dossier task after checking the arrival
     * of all files required to this apply the folder with a ACCEPTED_FOR_ANALYSE status
     *
     * @param task
     */
    void acceptDossier(Task task);

    List<Dossier> getAllNonAnalysedDossiers();

    void rejectAnalysedDossier(Dossier dossier);

    void acceptAnalysedDossier(Dossier dossier);

    List<Dossier> getAllAnalysedDossiers();

    List<PiecejustifDTO> getPiecejustifFromProcess(Dossier dossier);

    void receiveDossier(Task task);

    Task getAllNonReceivedDossiers(Long dossierId);

    Task getAllReceivedDossiers(Long dossierId);
}
