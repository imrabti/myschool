package com.gsr.myschool.server.service;

import com.gsr.myschool.server.business.Inscription;
import com.gsr.myschool.server.business.core.Filiere;
import com.gsr.myschool.server.business.core.NiveauEtude;

import java.util.List;

public interface InscriptionService {
    List<Inscription> findAllInscriptionsByUser(Long userId);

    List<Filiere> findAllFiliere();

    List<NiveauEtude> findAllNiveauEtude();
}
