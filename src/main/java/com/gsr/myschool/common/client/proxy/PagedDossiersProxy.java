package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.common.shared.dto.PagedDossiers;

import java.util.List;

@ProxyFor(PagedDossiers.class)
public interface PagedDossiersProxy extends ValueProxy {
    public List<DossierProxy> getDossiers();

    public void setDossiers(List<DossierProxy> dossiers);

    public List<DossierConvocationDTOProxy> getDossierConvocationDTOs();

    public void setDossierConvocationDTOs(List<DossierConvocationDTOProxy> dossierConvocationDTOs);

    public Integer getTotalElements();

    public void setTotalElements(Integer totalElements);
}
