package com.gsr.myschool.common.client.util;

import com.gsr.myschool.common.client.proxy.*;
import com.gsr.myschool.common.shared.type.ValueTypeCode;

import java.util.List;

public interface ValueList {
    List<FiliereProxy> getFiliereList();

    List<EtablissementScolaireProxy> getEtablissementScolaireList();

    List<NiveauEtudeProxy> getNiveauEtudeList(Long filiere);

    List<NiveauEtudeProxy> getNiveauEtudeList();

    List<SessionExamenProxy> getSessionsList();

    List<ValueListProxy> getValueListByCode(ValueTypeCode valueTypeCode);

    List<ValueListProxy> getValueListByCode(ValueTypeCode valueTypeCode, Boolean asc);

    void initFiliereList();

    void initEtablissementScolaireList();

    void initNiveauEtudeMap();

    void initValueListMap();

    List<SessionExamenProxy> getClosedSessionsList();
}
