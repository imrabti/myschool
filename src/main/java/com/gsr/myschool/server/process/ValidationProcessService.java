package com.gsr.myschool.server.process;

import com.gsr.myschool.server.business.Dossier;

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
     * Returns all the Dossier not received yet
     *
     * @return
     */
    List<Dossier> getAllNonReceivedDossiers();

    /**
     * Finish the Task
     *
     * @param dossier
     */
    void receiveDossier(Dossier dossier);

    /**
     * Returns the list of the Dossier that had been received,
     * the user should validate the arrival of all the files to proceed with an analyse
     *
     * @return
     */
    List<Dossier> getAllReceivedDossiers();

    /**
     * Finish the validation of the Dossier task after checking the arrival
     * of all files required to this apply the folder with a REJECTED status
     * and turn it into WAITING this action will send an email to the parent
     * with the rest of files needed
     *
     * @param dossier
     */
    void rejectDossier(Dossier dossier);

    /**
     * Finish the validation of the Dossier task after checking the arrival
     * of all files required to this apply the folder with a ACCEPTED_FOR_ANALYSE status
     *
     * @param dossier
     */
    void acceptDossier(Dossier dossier);

    List<Dossier> getAllNonAnalysedDossiers();

    void rejectAnalysedDossier(Dossier dossier);

    void acceptAnalysedDossier(Dossier dossier);

    List<Dossier> getAllAnalysedDossiers();
}
