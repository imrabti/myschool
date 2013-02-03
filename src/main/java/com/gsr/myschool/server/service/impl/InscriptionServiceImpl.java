package com.gsr.myschool.server.service.impl;

import com.google.common.base.Strings;
import com.gsr.myschool.common.shared.dto.ScolariteAnterieurDTO;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.server.business.Candidat;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.business.EtablissementScolaire;
import com.gsr.myschool.server.business.Fraterie;
import com.gsr.myschool.server.business.InfoParent;
import com.gsr.myschool.server.business.ScolariteAnterieur;
import com.gsr.myschool.server.business.User;
import com.gsr.myschool.server.process.ValidationProcessService;
import com.gsr.myschool.server.repos.CandidatRepos;
import com.gsr.myschool.server.repos.DossierRepos;
import com.gsr.myschool.server.repos.EtablissementScolaireRepos;
import com.gsr.myschool.server.repos.FiliereRepos;
import com.gsr.myschool.server.repos.FraterieRepos;
import com.gsr.myschool.server.repos.InfoParentRepos;
import com.gsr.myschool.server.repos.NiveauEtudeRepos;
import com.gsr.myschool.server.repos.ScolariteAnterieurRepos;
import com.gsr.myschool.server.repos.ValueListRepos;
import com.gsr.myschool.server.security.SecurityContextProvider;
import com.gsr.myschool.server.service.InscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class InscriptionServiceImpl implements InscriptionService {
    @Autowired
    private SecurityContextProvider securityContextProvider;
    @Autowired
    private DossierRepos dossierRepos;
    @Autowired
    private InfoParentRepos infoParentRepos;
    @Autowired
    private CandidatRepos candidatRepos;
    @Autowired
    private EtablissementScolaireRepos etablissementScolaireRepos;
    @Autowired
    private ScolariteAnterieurRepos scolariteAnterieurRepos;
    @Autowired
    private ValueListRepos valueListRepos;
    @Autowired
    private FraterieRepos fraterieRepos;
    @Autowired
    private FiliereRepos filiereRepos;
    @Autowired
    private NiveauEtudeRepos niveauEtudeRepos;
    @Autowired
    private ValidationProcessService validationProcessService;

    @Override
    @Transactional(readOnly = true)
    public List<Dossier> findAllDossiers() {
        Long id = securityContextProvider.getCurrentUser().getId();
        return dossierRepos.findByOwnerId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Dossier findDossierById(Long id) {
        return dossierRepos.findOne(id);
    }

    @Override
    public Dossier createNewInscription() {
        Candidat candidat = new Candidat();
        candidatRepos.save(candidat);

        InfoParent infoParent = new InfoParent();
        infoParentRepos.save(infoParent);

        User user = securityContextProvider.getCurrentUser();
        Dossier dossier = new Dossier();
        dossier.setStatus(DossierStatus.CREATED);
        dossier.setOwner(user);
        dossier.setInfoParent(infoParent);
        dossier.setCandidat(candidat);
        dossier.setCreateDate(new Date());
        dossierRepos.save(dossier);

        return dossier;
    }

    @Override
    public void deleteInscription(Long dossierId) {
        Dossier currentDossier = dossierRepos.findOne(dossierId);
        dossierRepos.delete(currentDossier);
        infoParentRepos.delete(currentDossier.getInfoParent());
        candidatRepos.delete(currentDossier.getCandidat());
    }

    @Override
    public Dossier updateDossier(Dossier dossier) {
        Dossier currentDossier = dossierRepos.findOne(dossier.getId());
        currentDossier.setFiliere(filiereRepos.findOne(dossier.getFiliere().getId()));
        currentDossier.setNiveauEtude(niveauEtudeRepos.findOne(dossier.getNiveauEtude().getId()));
        dossierRepos.save(currentDossier);
        return currentDossier;
    }

    @Override
    public InfoParent updateParent(InfoParent infoParent) {
        InfoParent currentInfoParent = infoParentRepos.findOne(infoParent.getId());
        currentInfoParent.setNom(infoParent.getNom());
        currentInfoParent.setPrenom(infoParent.getPrenom());
        currentInfoParent.setTelGsm(infoParent.getTelGsm());
        currentInfoParent.setTelDom(infoParent.getTelDom());
        currentInfoParent.setTelBureau(infoParent.getTelBureau());
        currentInfoParent.setEmail(infoParent.getEmail());
        currentInfoParent.setAddress(infoParent.getAddress());
        currentInfoParent.setFonction(infoParent.getFonction());
        currentInfoParent.setParentType(infoParent.getParentType());
        currentInfoParent.setInstitution(infoParent.getInstitution());
        infoParentRepos.save(currentInfoParent);
        return currentInfoParent;
    }

    @Override
    public Candidat updateCandidat(Candidat candidat) {
        Candidat currentCandidat = candidatRepos.findOne(candidat.getId());
        currentCandidat.setFirstname(candidat.getFirstname());
        currentCandidat.setLastname(candidat.getLastname());
        currentCandidat.setBirthDate(candidat.getBirthDate());
        currentCandidat.setBirthLocation(candidat.getBirthLocation());
        currentCandidat.setPhone(candidat.getPhone());
        currentCandidat.setCin(candidat.getCin());
        currentCandidat.setCne(candidat.getCne());
        currentCandidat.setEmail(candidat.getEmail());
        currentCandidat.setGsm(candidat.getGsm());
        currentCandidat.setNationality(valueListRepos.findOne(candidat.getNationality().getId()));
        currentCandidat.setBacSerie(valueListRepos.findOne(candidat.getBacSerie().getId()));
        currentCandidat.setBacYear(valueListRepos.findOne(candidat.getBacYear().getId()));
        candidatRepos.save(currentCandidat);
        return currentCandidat;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScolariteAnterieur> findScolariteAnterieursByDossierId(Long dossierId) {
        Dossier dossier = dossierRepos.findOne(dossierId);
        if (dossier != null) {
            return scolariteAnterieurRepos.findByCandidatId(dossier.getCandidat().getId());
        } else {
            return new ArrayList<ScolariteAnterieur>();
        }
    }

    @Override
    public void createNewScolariteAnterieur(ScolariteAnterieurDTO scolariteAnterieur, Long dossierId) {
        EtablissementScolaire etablissementScolaire;

        if (!Strings.isNullOrEmpty(scolariteAnterieur.getNewEtablissementScolaire())) {
            etablissementScolaire = new EtablissementScolaire();
            etablissementScolaire.setNom(scolariteAnterieur.getNewEtablissementScolaire());
            etablissementScolaire.setReference(false);
            etablissementScolaireRepos.save(etablissementScolaire);
        } else {
            etablissementScolaire = scolariteAnterieur.getEtablissement();
        }

        ScolariteAnterieur newScolariteAnterieur = new ScolariteAnterieur();
        newScolariteAnterieur.setClasse(scolariteAnterieur.getClasse());
        newScolariteAnterieur.setTypeNiveauEtude(scolariteAnterieur.getTypeNiveauEtude());
        newScolariteAnterieur.setAnneeScolaire(scolariteAnterieur.getAnneeScolaire());
        newScolariteAnterieur.setEtablissement(etablissementScolaire);
        newScolariteAnterieur.setCandidat(dossierRepos.findOne(dossierId).getCandidat());
        scolariteAnterieurRepos.save(newScolariteAnterieur);
    }

    @Override
    public void deleteScolariteAnterieur(Long scolariteAnterieurId) {
        ScolariteAnterieur scolariteAnterieur = scolariteAnterieurRepos.findOne(scolariteAnterieurId);
        scolariteAnterieurRepos.delete(scolariteAnterieur);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Fraterie> findFraterieByDossierId(Long dossierId) {
        Dossier dossier = dossierRepos.findOne(dossierId);
        if (dossier != null) {
            return fraterieRepos.findByCandidatId(dossier.getCandidat().getId());
        } else {
            return new ArrayList<Fraterie>();
        }
    }

    @Override
    public void createNewFraterie(Fraterie fraterie, Long dossierId) {
        Dossier dossier = dossierRepos.findOne(dossierId);
        fraterie.setCandidat(dossier.getCandidat());
        fraterie.setValide(false);
        fraterieRepos.save(fraterie);
    }

    @Override
    public void deleteFraterie(Long fraterieId) {
        Fraterie fraterie = fraterieRepos.findOne(fraterieId);
        fraterieRepos.delete(fraterie);
    }

    @Override
    @Transactional(readOnly = true)
    public void submitInscription(Long dossierId) {
        Dossier dossier = dossierRepos.findOne(dossierId);
        validationProcessService.startProcess(dossier);
    }
}
