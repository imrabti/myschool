package com.gsr.myschool.common.client.util;

import com.gsr.myschool.common.client.proxy.*;
import com.gsr.myschool.common.shared.type.ValueTypeCode;

import java.util.List;

public interface ValueList {
    List<FiliereProxy> getFiliereList();

    List<FiliereProxy> getFiliereList(Boolean isSuperUser);

    List<EtablissementScolaireProxy> getEtablissementScolaireList();

    List<NiveauEtudeProxy> getNiveauEtudeList(Long filiere, Boolean isSuperUser);

    List<NiveauEtudeProxy> getNiveauEtudeList(Long filiere);

    List<NiveauEtudeProxy> getNiveauEtudeList(Boolean isSuperUser);

    List<NiveauEtudeProxy> getNiveauEtudeList();

    List<SessionExamenProxy> getSessionsList();

    List<ValueListProxy> getValueListByCode(ValueTypeCode valueTypeCode);

    List<ValueListProxy> getValueListByCode(ValueTypeCode valueTypeCode, Boolean asc);

    void initFiliereList(Boolean isSuperUser);

    void initEtablissementScolaireList();

    void initValueListMap();

    List<SessionExamenProxy> getClosedSessionsList();

    List<SessionExamenProxy> getClosedSessionsList(ValueListProxy anneeScolaire);

    void initNiveauEtudeMap(Boolean isSuperUser);
}
