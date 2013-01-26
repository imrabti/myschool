package com.gsr.myschool.front.client.util;

import com.gsr.myschool.common.client.proxy.FiliereProxy;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;

import java.util.List;

public interface ValueList {
    List<FiliereProxy> getFiliereList();

    List<NiveauEtudeProxy> getNiveauEtudeList(String filiere);

    void initFiliereList();

    void initNiveauEtudeMap();
}
