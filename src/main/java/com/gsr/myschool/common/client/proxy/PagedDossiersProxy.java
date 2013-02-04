package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.server.dto.PagedDossiers;

import java.util.List;

@ProxyFor(PagedDossiers.class)
public interface PagedDossiersProxy extends ValueProxy {
    List<DossierProxy> getDossiers();

    void setDossiers(List<DossierProxy> dossiers);

    Integer getTotalElements();

    void setTotalElements(Integer totalElements);
}
