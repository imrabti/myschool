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

import org.adorsys.xlseasy.annotation.CellAlign;
import org.adorsys.xlseasy.annotation.Sheet;
import org.adorsys.xlseasy.annotation.SheetCellStyle;
import org.adorsys.xlseasy.annotation.SheetColumn;

import java.io.Serializable;

@Sheet(autoSizeColumns = true,
        columnOrder = {"niveau", "created", "submitted", "received", "rejected", "acceptedForStudy",
                "standBy", "acceptedForTest", "invitedToTest", "notAcceptedForTest", "affected", "notAdmitted", "toBeRegistred"})
public class SummaryExcelDTO implements Serializable {
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Niveau scolaire")
    private String niveau;

    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Créé")
    private Long created;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Soumis")
    private Long submitted;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Reçu")
    private Long received;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Rejeté")
    private Long rejected;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Accepté pour étude")
    private Long acceptedForStudy;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "En attente")
    private Long standBy;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Accepté pour test")
    private Long acceptedForTest;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Convoqué pour test")
    private Long invitedToTest;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Refusé pour test")
    private Long notAcceptedForTest;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Affecté pour test")
    private Long affected;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Non Admis")
    private Long notAdmitted;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true),
            columnName = "Admis à l'inscription")
    private Long toBeRegistred;

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Long submitted) {
        this.submitted = submitted;
    }

    public Long getReceived() {
        return received;
    }

    public void setReceived(Long received) {
        this.received = received;
    }

    public Long getRejected() {
        return rejected;
    }

    public void setRejected(Long rejected) {
        this.rejected = rejected;
    }

    public Long getAcceptedForStudy() {
        return acceptedForStudy;
    }

    public void setAcceptedForStudy(Long acceptedForStudy) {
        this.acceptedForStudy = acceptedForStudy;
    }

    public Long getStandBy() {
        return standBy;
    }

    public void setStandBy(Long standBy) {
        this.standBy = standBy;
    }

    public Long getAcceptedForTest() {
        return acceptedForTest;
    }

    public void setAcceptedForTest(Long acceptedForTest) {
        this.acceptedForTest = acceptedForTest;
    }

    public Long getInvitedToTest() {
        return invitedToTest;
    }

    public void setInvitedToTest(Long invitedToTest) {
        this.invitedToTest = invitedToTest;
    }

    public Long getNotAcceptedForTest() {
        return notAcceptedForTest;
    }

    public void setNotAcceptedForTest(Long notAcceptedForTest) {
        this.notAcceptedForTest = notAcceptedForTest;
    }

    public Long getAffected() {
        return affected;
    }

    public void setAffected(Long affected) {
        this.affected = affected;
    }

    public Long getNotAdmitted() {
        return notAdmitted;
    }

    public void setNotAdmitted(Long notAdmitted) {
        this.notAdmitted = notAdmitted;
    }

    public Long getToBeRegistred() {
        return toBeRegistred;
    }

    public void setToBeRegistred(Long toBeRegistred) {
        this.toBeRegistred = toBeRegistred;
    }
}
