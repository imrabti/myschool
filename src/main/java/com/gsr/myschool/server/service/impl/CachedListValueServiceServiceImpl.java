package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.common.shared.type.SettingsKey;
import com.gsr.myschool.server.business.EtablissementScolaire;
import com.gsr.myschool.server.business.Settings;
import com.gsr.myschool.server.business.core.Filiere;
import com.gsr.myschool.server.business.core.MatiereExamen;
import com.gsr.myschool.server.business.core.NiveauEtude;
import com.gsr.myschool.server.business.core.PieceJustif;
import com.gsr.myschool.server.business.valuelist.ValueList;
import com.gsr.myschool.server.repos.DossierRepos;
import com.gsr.myschool.server.repos.EtablissementScolaireRepos;
import com.gsr.myschool.server.repos.FiliereRepos;
import com.gsr.myschool.server.repos.MatiereExamenRepos;
import com.gsr.myschool.server.repos.NiveauEtudeRepos;
import com.gsr.myschool.server.repos.PieceJustifRepos;
import com.gsr.myschool.server.repos.SettingsRepos;
import com.gsr.myschool.server.service.CachedListValueService;
import com.gsr.myschool.server.service.ValueListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CachedListValueServiceServiceImpl implements CachedListValueService {
    @Autowired
    private FiliereRepos filiereRepos;
    @Autowired
    private NiveauEtudeRepos niveauEtudeRepos;
    @Autowired
    private EtablissementScolaireRepos etablissementScolaireRepos;
    @Autowired
    private ValueListService valueListService;
    @Autowired
    private DossierRepos dossierRepos;
    @Autowired
    private SettingsRepos settingsRepos;
    @Autowired
    private PieceJustifRepos pieceJustifRepos;
    @Autowired
    private MatiereExamenRepos matiereExamenRepos;

    @Override
    @Cacheable("filiere")
    public List<Filiere> findAllFiliere() {
        return filiereRepos.findAll();
    }


    @Override
    @Cacheable("filiereFront")
    public List<Filiere> findFilieres() {
        Settings settings = settingsRepos.findOne(SettingsKey.FILIERE_GENERAL_CLOSED);
        if (settings == null) {
            settings = new Settings();
            settings.setSetting(SettingsKey.FILIERE_GENERAL_CLOSED);
            settings.setValue(GlobalParameters.APP_STATUS_OPENED);
            settingsRepos.save(settings);
        }
        if (GlobalParameters.APP_STATUS_OPENED.equals(settings.getValue())) {
            return filiereRepos.findAll();
        } else {
            return filiereRepos.findByIdGreaterThan(25L);
        }
    }

    @Override
    @Cacheable("etablissementScolaire")
    public List<EtablissementScolaire> findAllEtablissementScolaire() {
        List<EtablissementScolaire> result = etablissementScolaireRepos.findAll();
        EtablissementScolaire autres = new EtablissementScolaire();
        autres.setNom("- Autres -");
        result.add(autres);
        return result;
    }

    @Override
    @Cacheable("niveauEtude")
    public List<NiveauEtude> findAllNiveauEtude() {
        return niveauEtudeRepos.findAll(new Sort(new Order("annee")));
    }

    @Override
    @Cacheable("pieces")
    public List<String> findPieces() {
        List<String> pieces = new ArrayList<String>();
        for (PieceJustif piece: pieceJustifRepos.findAll()) {
            pieces.add(piece.getNom());
        }
        return pieces;
    }

    @Override
    @Cacheable("matieres")
    public List<String> findMatieres() {
        List<String> matieres = new ArrayList<String>();
        for (MatiereExamen matiere: matiereExamenRepos.findAll()) {
            matieres.add(matiere.getNom());
        }
        return matieres;
    }

    @Override
    @Cacheable("valueList")
    public List<ValueList> findAllValueList() {
        return valueListService.findAll();
    }

    @Override
    public List<String> findAllNumDossier() {
        return dossierRepos.findAllNumDossier();
    }
}
