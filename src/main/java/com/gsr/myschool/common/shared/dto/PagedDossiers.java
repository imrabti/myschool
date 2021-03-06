package com.gsr.myschool.common.shared.dto;

import com.gsr.myschool.server.business.Dossier;

import java.util.List;

public class PagedDossiers {
    private List<Dossier> dossiers;
    private List<DossierConvocationDTO> dossierConvocationDTOs;
    private Integer totalElements;

    public PagedDossiers() {
    }

    public PagedDossiers(List<Dossier> dossiers, List<DossierConvocationDTO> dossierConvocationDTOs, Integer totalElements) {
        this.dossiers = dossiers;
        this.dossierConvocationDTOs = dossierConvocationDTOs;
        this.totalElements = totalElements;
    }

    public List<Dossier> getDossiers() {
        return dossiers;
    }

    public void setDossiers(List<Dossier> dossiers) {
        this.dossiers = dossiers;
    }

    public List<DossierConvocationDTO> getDossierConvocationDTOs() {
        return dossierConvocationDTOs;
    }

    public void setDossierConvocationDTOs(List<DossierConvocationDTO> dossierConvocationDTOs) {
        this.dossierConvocationDTOs = dossierConvocationDTOs;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }
}
