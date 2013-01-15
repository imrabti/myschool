package com.gsr.myschool.server.service;

import com.gsr.myschool.server.business.Inscription;

import java.util.List;

public interface InscriptionService {
    List<Inscription> findAllInscriptionsByUser(Long userId);
}
