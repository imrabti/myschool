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

package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.server.business.Candidat;

import java.util.Date;

@ProxyFor(Candidat.class)
public interface CandidatProxy extends ValueProxy {
    Long getId();

    void setId(Long id);

    String getFirstname();

    void setFirstname(String firstname);

    String getLastname() ;

    void setLastname(String lastname);

    Date getBirthDate();

    void setBirthDate(Date birthDate);

    String getBirthLocation();

    void setBirthLocation(String birthLocation);

    String getPhone();

    void setPhone(String phone);

    String getCin();

    void setCin(String cin);

    String getCne();

    void setCne(String cne);

    String getEmail();

    void setEmail(String email);

    String getGsm();

    void setGsm(String gsm);

    ValueListProxy getBacYear();

    void setBacYear(ValueListProxy bacYear);

    ValueListProxy getBacSerie();

    void setBacSerie(ValueListProxy bacSerie);

    ValueListProxy getNationality();

    void setNationality(ValueListProxy nationality);
}
