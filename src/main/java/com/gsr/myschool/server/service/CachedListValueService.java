package com.gsr.myschool.server.service;

import com.gsr.myschool.server.business.EtablissementScolaire;
import com.gsr.myschool.server.business.core.Filiere;
import com.gsr.myschool.server.business.core.NiveauEtude;
import com.gsr.myschool.server.business.valuelist.ValueList;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface CachedListValueService {
    List<Filiere> findAllFiliere();

    List<EtablissementScolaire> findAllEtablissementScolaire();

    List<NiveauEtude> findAllNiveauEtude();

    List<ValueList> findAllValueList();

    List<String> findAllNumDossier();

    List<Filiere> findFilieres();

    List<String> findPieces();

    List<String> findMatieres();

    List<NiveauEtude> findNiveauEtudes();

    List<NiveauEtude> findNiveauEtudes(Boolean isSuper);

    List<NiveauEtude> findNiveauEtudesByFiliere(Long filiere, Boolean isSuper);
}
