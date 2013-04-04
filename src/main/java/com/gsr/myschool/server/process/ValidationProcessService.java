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

public interface ValidationProcessService {
    /**
     * Process starts when a parent submit a pre-inscription Dossier
     * this will create a new Task waiting for the arrival of the physical Dossier
     *
     * @param dossier
     */
    void startProcess(Dossier dossier);

    /**
     * Returns The direct dossier to be receive
     *
     * @return
     */
    Task getDossierToReceive(Long dossierId);

    /**
     * Finish the Task with required paper attached to this Dossier
     * This method is called when the Dossier is on Submitted state
     *
     * @param task
     * @param dossier
     */
    void receiveDossier(Task task, Dossier dossier);

    /**
     * Finish the Task, this method is called when the Dossier is on
     * Standby state waiting for a missing paper
     * @param task
     * @param receivedDossier
     * @param piecejustifDTOs
     */
    void receiveDossier(Task task, Dossier receivedDossier, List<PiecejustifDTO> piecejustifDTOs);

    /**
     * Load list of required papers to gather for this kind of dossier
     * @param dossierId
     * @return
     */
    List<PiecejustifDTO> getPieceJustifFromProcess(Long dossierId);

    /**
     * Returns the Dossier that had been received and ready to be validated,
     * the user should validate the arrival of all the files to proceed with an analyse
     *
     * @return
     */
    Task getDossierToValidate(Long dossierId);

    /**
     * Finish the validation of the Dossier task after checking the arrival
     * of all files required to this apply the folder with a REJECTED status
     * and turn it into WAITING this action will prepare an email to the parent
     * with the rest of files needed
     *
     * @param task
     * @param verifiedDossier
     * @param pieceNonavailable
     */
    void rejectDossier(Task task, Dossier verifiedDossier, List<PiecejustifDTO> pieceNonavailable);

    /**
     * Finish the validation of the Dossier task after checking the arrival
     * of all files required to this apply the folder with a ACCEPTED_FOR_ANALYSE status
     *
     * @param task
     * @param verifiedDossier
     */
    void acceptDossier(Task task, Dossier verifiedDossier);

    void rejectAnalysedDossier(Task task, Dossier dossier);

    void acceptAnalysedDossier(Task task, Dossier dossier);

    Task getDossierToAnalyse(Long dossierId);

    Task getDossierToReAccept(Long dossierId);

    Task getDossierToAdmission(Long dossierId);

    void rejectFinalDossier(Task task, Dossier dossier);

    void admitFinalDossier(Task task, Dossier dossier);

    void deleteProcessInstance(Long dossierId);
}
