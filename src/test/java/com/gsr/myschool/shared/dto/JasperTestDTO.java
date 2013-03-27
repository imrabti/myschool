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

package com.gsr.myschool.shared.dto;

import com.gsr.myschool.server.util.BeanMapper;

import java.io.Serializable;
import java.util.Map;

public class JasperTestDTO implements Serializable {

    private String nom;
    private String de;
    private String a;

    public JasperTestDTO(String nom, String de, String a) {
        this.nom = nom;
        this.de = de;
        this.a = a;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public Map getReportsAttributes() {
        return BeanMapper.beanToMap(this);
    }
}
