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

package com.gsr.myschool.common.shared.type;

public enum TypeEnseignement {
    MISSION("Mission", "Section FR", 10L), BILINGUE("Section Bilingue", "Section Bilingue", 20L);

    private String label;
    private String nomFiliere;
    private Long id;

    private TypeEnseignement(String label, String nomFiliere, Long id) {
        this.label = label;
        this.nomFiliere = nomFiliere;
        this.id = id;
    }

    public static TypeEnseignement getByNomFilere(String nomFiliere) {
        if (nomFiliere.equals(MISSION.getNomFiliere())) {
            return MISSION;
        } else if (nomFiliere.equals(BILINGUE.getNomFiliere())) {
            return BILINGUE;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return label;
    }

    public String getNomFiliere() {
        return nomFiliere;
    }

    public Long getId() {
        return id;
    }
}
