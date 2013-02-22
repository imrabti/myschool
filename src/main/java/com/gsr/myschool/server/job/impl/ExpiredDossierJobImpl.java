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

import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.common.shared.dto.DossierFilterDTO;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.job.Worker;
import com.gsr.myschool.server.service.DossierService;
import com.gsr.myschool.server.service.InscriptionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Component("expiredDossierJob")
public class ExpiredDossierJobImpl implements Worker {

    private Log logger = LogFactory.getLog("Expired Dossier Job");

    @Autowired
    private InscriptionService inscriptionService;
    @Autowired
    private DossierService dossierService;

    public void work() {
        SimpleDateFormat dateformat = new SimpleDateFormat(GlobalParameters.DATE_FORMAT);

        logger.info("Job started at " + dateformat.format(new Date()));

        int number = 0;
        DossierFilterDTO filter = new DossierFilterDTO();
        filter.setStatus(DossierStatus.CREATED);

        List<Dossier> dossiers = dossierService.findAllDossiersByCriteria(filter);

        for (Dossier dossier : dossiers) {
            if (dossier.getCreateDate() != null) {
                Calendar date = new GregorianCalendar();
                date.setTime(dossier.getCreateDate());
                date.add(Calendar.DAY_OF_WEEK, 1);
                Calendar deleteAfterThis = new GregorianCalendar();

                if (date.after(deleteAfterThis)) {
                    logger.info("deleting dossier id : " + dossier.getId() + ".");
                    try {
                        inscriptionService.deleteInscription(dossier.getId());
                        number++;
                    } catch (Exception e) {
                        logger.fatal(e.getMessage());
                    }
                }
            }
        }

        logger.info("Job ended at " + dateformat.format(new Date()) + " with " + number + " Dossier deleted");
    }
}
