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

import com.gsr.myschool.server.util.BeanMapper;
import org.adorsys.xlseasy.annotation.CellAlign;
import org.adorsys.xlseasy.annotation.Sheet;
import org.adorsys.xlseasy.annotation.SheetCellStyle;
import org.adorsys.xlseasy.annotation.SheetColumn;

import java.io.Serializable;
import java.util.Map;

@Sheet(autoSizeColumns = true,
        columnOrder = {"generatedNumDossier", "name", "birthDate",
                "etablissement", "session", "fraterieGsr", "parentGsr"})
public class DossierConvoqueExcelDTO implements Serializable {
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Numéro dossier")
    private String generatedNumDossier;

    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Nom Prénom")
    private String name;

    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Date de naissance")
    private String birthDate;

    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Etablissement d'origine")
    private String etablissement;

    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Session")
    private String session;

    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Fraterie au GSR")
    private String fraterieGsr;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Parent au GSR")
    private String parentGsr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFraterieGsr() {
        return fraterieGsr;
    }

    public void setFraterieGsr(String fraterieGsr) {
        this.fraterieGsr = fraterieGsr;
    }

    public String getParentGsr() {
        return parentGsr;
    }

    public void setParentGsr(String parentGsr) {
        this.parentGsr = parentGsr;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGeneratedNumDossier() {
        return generatedNumDossier;
    }

    public void setGeneratedNumDossier(String generatedNumDossier) {
        this.generatedNumDossier = generatedNumDossier;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(String etablissement) {
        this.etablissement = etablissement;
    }

    public Map getReportsAttributes() {
        return BeanMapper.beanToMap(this);
    }
}
