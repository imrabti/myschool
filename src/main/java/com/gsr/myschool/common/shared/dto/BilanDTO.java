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

package com.gsr.myschool.common.shared.dto;

import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.common.shared.type.TypeNiveauEtude;
import com.gsr.myschool.server.util.BeanMapper;

import java.io.Serializable;
import java.util.Map;

public class BilanDTO implements Serializable {
    private Long filiere;
    private String niveau;
    private Long total;

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public BilanDTO() {
    }

    public Long getFiliere() {
        return filiere;
    }

    public void setFiliere(Long filiere) {
        this.filiere = filiere;
    }

    public BilanDTO(String niveau, Long filiere, Long total) {
        this.niveau = niveau;
        this.filiere = filiere;
        this.total = total;
    }

    public BilanDTO(TypeNiveauEtude niveau, Long filiere, Long total) {
        this.niveau = niveau.toString();
        this.filiere = filiere;
        this.total = total;
    }

    public Map getReportsAttributes() {
        return BeanMapper.beanToMap(this);
    }
}
