package com.gsr.myschool.common.client.util;

import com.gsr.myschool.common.client.proxy.EtablissementScolaireProxy;
import com.gsr.myschool.common.client.proxy.FiliereProxy;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gsr.myschool.common.client.proxy.ValueListProxy;
import com.gsr.myschool.common.shared.type.ValueTypeCode;

import java.util.List;

public interface ValueList {
    List<FiliereProxy> getFiliereList();

    List<EtablissementScolaireProxy> getEtablissementScolaireList();

    List<NiveauEtudeProxy> getNiveauEtudeList(String filiere);

    List<ValueListProxy> getValueListByCode(ValueTypeCode valueTypeCode);

    void initFiliereList();

    void initEtablissementScolaireList();

    void initNiveauEtudeMap();

    void initValueListMap();
}
