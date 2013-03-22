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

import com.gsr.myschool.server.business.core.PieceJustif;

import java.io.Serializable;

public class PiecejustifDTO implements Serializable {
    private Long id;
    private String nom;
    private String motif;
    private Boolean available;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMotif() {
        if ("null".equals(motif)) {
            return "";
        }
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public static PiecejustifDTO mapper(PieceJustif piecejustif) {
        PiecejustifDTO piecejustifDTO = new PiecejustifDTO();
        piecejustifDTO.setId(piecejustif.getId());
        piecejustifDTO.setNom(piecejustif.getNom());
        piecejustifDTO.setAvailable(false);

        return piecejustifDTO;
    }
}
