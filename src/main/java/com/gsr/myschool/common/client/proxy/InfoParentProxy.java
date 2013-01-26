package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.common.shared.type.ParentType;
import com.gsr.myschool.server.business.InfoParent;

@ProxyFor(InfoParent.class)
public interface InfoParentProxy extends ValueProxy {
    Long getId();

    void setId(Long id);

    String getNom();

    void setNom(String nom);

    String getPrenom();

    void setPrenom(String prenom);

    String getTelGsm();

    void setTelGsm(String telGsm);

    String getTelDom();

    void setTelDom(String telDom);

    String getTelBureau();

    void setTelBureau(String telBureau);

    String getEmail();

    void setEmail(String email);

    String getAddress();

    void setAddress(String address);

    String getFonction();

    void setFonction(String fonction);

    ParentType getParentType();

    void setParentType(ParentType parentType);

    String getInstitution();

    void setInstitution(String institution);
}
