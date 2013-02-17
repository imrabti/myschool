package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.common.shared.dto.EtablissementFilterDTO;
import com.gsr.myschool.common.shared.type.EtablissementType;

@ProxyFor(EtablissementFilterDTO.class)
public interface EtablissementFilterDTOProxy extends ValueProxy {
    String getNom();

    void setNom(String nom);

    ValueListProxy getVille();

    void setVille(ValueListProxy ville);

    EtablissementType getType();

    void setType(EtablissementType type);
}
