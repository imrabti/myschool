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

package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.service.DossierService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DossierServiceImpl implements DossierService {

    @Override
    @Transactional(readOnly = true)
    public List<Dossier> findAllDossiersByUser(Long userId) {
        List<Dossier> Dossiers = new ArrayList<Dossier>();

        Dossier dossier = new Dossier();
        dossier.setNote("001S2013");
        dossier.setCreateDate(new Date());
        dossier.setSubmitDate(new Date());
        dossier.setId(1);
        dossier.setStatus(DossierStatus.INVITED_TO_TEST);
        Dossiers.add(dossier);

        dossier = new Dossier();
        dossier.setNote("002S2013");
        dossier.setCreateDate(new Date());
        dossier.setSubmitDate(new Date());
        dossier.setId(2);
        dossier.setStatus(DossierStatus.RECEIVED);
        Dossiers.add(dossier);

        dossier = new Dossier();
        dossier.setNote("002S2sds3");
        dossier.setCreateDate(new Date());
        dossier.setSubmitDate(new Date());
        dossier.setId(3);
        dossier.setStatus(DossierStatus.ACCEPTED_FOR_TEST);
        Dossiers.add(dossier);

        return Dossiers;
    }
}