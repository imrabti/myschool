package com.gsr.myschool.server.service;

import com.gsr.myschool.server.business.core.Filiere;
import com.gsr.myschool.server.business.core.NiveauEtude;

import java.util.List;

public interface CachedListValueService {
    List<Filiere> findAllFiliere();

    List<NiveauEtude> findAllNiveauEtude();
}
