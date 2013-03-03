package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.common.shared.constants.GlobalParameters;
import com.gsr.myschool.common.shared.dto.EtablissementFilterDTO;
import com.gsr.myschool.common.shared.dto.FraterieDTO;
import com.gsr.myschool.common.shared.dto.ScolariteActuelleDTO;
import com.gsr.myschool.common.shared.exception.UnAuthorizedException;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.common.shared.type.Gender;
import com.gsr.myschool.common.shared.type.ParentType;
import com.gsr.myschool.common.shared.type.ValueTypeCode;
import com.gsr.myschool.server.business.*;
import com.gsr.myschool.server.business.core.NiveauEtude;
import com.gsr.myschool.server.business.valuelist.ValueList;
import com.gsr.myschool.server.process.ValidationProcessService;
import com.gsr.myschool.server.repos.*;
import com.gsr.myschool.server.repos.spec.EtablissementScolaireSpec;
import com.gsr.myschool.server.security.SecurityContextProvider;
import com.gsr.myschool.server.service.InscriptionService;
import com.gsr.myschool.server.util.DateUtils;
import com.gsr.myschool.server.util.I18nMessageBean;
import com.gsr.myschool.server.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.util.*;

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
    private ScolariteActuelleRepos scolariteActuelleRepos;
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
    @Autowired
    private Validator validator;
    @Autowired
    private I18nMessageBean messageBean;

    @Override
    @Transactional(readOnly = true)
    public List<Dossier> findAllDossiers() {
        Long id = securityContextProvider.getCurrentUser().getId();
        return dossierRepos.findByOwnerId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Dossier findDossierById(Long id) throws UnAuthorizedException {
        Dossier dossier = dossierRepos.findOne(id);
        if (securityContextProvider.getCurrentAdmin() != null) {
            return dossier;
        }

        Long loggedInOwner = securityContextProvider.getCurrentUser().getId();

        if (dossier == null || dossier.getOwner().getId() != loggedInOwner) {
            throw new UnAuthorizedException();
        } else {
            return dossier;
        }
    }

    @Override
    public Dossier createNewInscription() {
        Candidat candidat = new Candidat();
        candidatRepos.save(candidat);

        ScolariteActuelle scolariteActuelle = new ScolariteActuelle();
        scolariteActuelleRepos.save(scolariteActuelle);

        User user = securityContextProvider.getCurrentUser();
        String currentAnneeScolaire = DateUtils.currentYear() + "-" + (DateUtils.currentYear() + 1);

        Dossier dossier = new Dossier();
        dossier.setGeneratedNumDossier("GSR_" + DateUtils.currentYear() + "_" + UUIDGenerator.generateUUID());
        dossier.setStatus(DossierStatus.CREATED);
        dossier.setOwner(user);
        dossier.setCandidat(candidat);
        dossier.setScolariteActuelle(scolariteActuelle);
        dossier.setCreateDate(new Date());
        dossier.setAnneeScolaire(valueListRepos.findByValueAndValueTypeCode(currentAnneeScolaire,
                ValueTypeCode.SCHOOL_YEAR));
        dossierRepos.save(dossier);

        InfoParent pere = new InfoParent();
        pere.setParentType(ParentType.PERE);
        pere.setDossier(dossier);
        pere.setCivilite(Gender.MALE);
        infoParentRepos.save(pere);

        InfoParent mere = new InfoParent();
        mere.setParentType(ParentType.MERE);
        mere.setDossier(dossier);
        pere.setCivilite(Gender.FEMALE);
        infoParentRepos.save(mere);

        InfoParent tuteur = new InfoParent();
        tuteur.setParentType(ParentType.TUTEUR);
        tuteur.setDossier(dossier);
        infoParentRepos.save(tuteur);

        return dossier;
    }

    @Override
    public void deleteInscription(Long dossierId) {
        List<InfoParent> infoParents = infoParentRepos.findByDossierId(dossierId);
        for (InfoParent infoParent : infoParents) {
            infoParentRepos.delete(infoParent);
        }

        Dossier currentDossier = dossierRepos.findOne(dossierId);
        dossierRepos.delete(currentDossier);
        candidatRepos.delete(currentDossier.getCandidat());
    }

    @Override
    public Dossier updateDossier(Dossier dossier) {
        Dossier currentDossier = dossierRepos.findOne(dossier.getId());
        currentDossier.setFiliere(filiereRepos.findOne(dossier.getFiliere().getId()));
        currentDossier.setNiveauEtude(niveauEtudeRepos.findOne(dossier.getNiveauEtude().getId()));

        currentDossier.setFiliere2(dossier.getFiliere2() != null ?
                filiereRepos.findOne(dossier.getFiliere2().getId()) : null);

        currentDossier.setNiveauEtude2(dossier.getNiveauEtude2() != null ?
                niveauEtudeRepos.findOne(dossier.getNiveauEtude2().getId()) : null);

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
        currentInfoParent.setInstitution(infoParent.getInstitution());
        currentInfoParent.setBirthLocation(infoParent.getBirthLocation());
        currentInfoParent.setBirthDate(infoParent.getBirthDate());
        currentInfoParent.setCivilite(infoParent.getCivilite());
        currentInfoParent.setLientParente(infoParent.getLientParente());
        currentInfoParent.setPromotionGsr(infoParent.getPromotionGsr());
        currentInfoParent.setFormationGsr(infoParent.getFormationGsr());
        currentInfoParent.setParentGsr(infoParent.getParentGsr());

        if (infoParent.getNationality() != null) {
            currentInfoParent.setNationality(valueListRepos.findOne(infoParent.getNationality().getId()));
        } else {
            currentInfoParent.setNationality(null);
        }

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

        if (candidat.getNationality() != null) {
            currentCandidat.setNationality(valueListRepos.findOne(candidat.getNationality().getId()));
        } else {
            currentCandidat.setNationality(null);
        }

        if (candidat.getBacYear() != null) {
            currentCandidat.setBacYear(valueListRepos.findOne(candidat.getBacYear().getId()));
        } else {
            currentCandidat.setBacYear(null);
        }

        if (candidat.getBacSerie() != null) {
            currentCandidat.setBacSerie(valueListRepos.findOne(candidat.getBacSerie().getId()));
        } else {
            currentCandidat.setBacSerie(null);
        }

        candidatRepos.save(currentCandidat);
        return currentCandidat;
    }

    @Override
    public ScolariteActuelle updateScolariteActuelle(ScolariteActuelleDTO scolariteActuelle) {
        ScolariteActuelle currentScolariteActuelle = scolariteActuelleRepos.findOne(scolariteActuelle.getId());

        if (scolariteActuelle.getEtablissement() != null) {
            Long etablissementId = scolariteActuelle.getEtablissement().getId();
            currentScolariteActuelle.setEtablissement(etablissementScolaireRepos.findOne(etablissementId));
        } else {
            currentScolariteActuelle.setEtablissement(null);
        }

        if (scolariteActuelle.getTypeEnseignement() != null) {
            Long idFiliere = scolariteActuelle.getTypeEnseignement().getId();
            currentScolariteActuelle.setFiliere(filiereRepos.findOne(idFiliere));
        } else {
            currentScolariteActuelle.setFiliere(null);
        }

        if (scolariteActuelle.getNiveauEtude() != null) {
            Long niveauEtudeId = scolariteActuelle.getNiveauEtude().getId();
            currentScolariteActuelle.setNiveauEtude(niveauEtudeRepos.findOne(niveauEtudeId));
        } else {
            currentScolariteActuelle.setNiveauEtude(null);
        }

        scolariteActuelleRepos.save(currentScolariteActuelle);

        return currentScolariteActuelle;
    }

    @Override
    @Transactional(readOnly = true)
    public List<InfoParent> findInfoParentByDossierId(Long dossierId) {
        List<InfoParent> infoParentList = infoParentRepos.findByDossierId(dossierId);
        return infoParentList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EtablissementScolaire> findEtablissementByFilter(EtablissementFilterDTO filter) {
        Specifications spec = Specifications.where(EtablissementScolaireSpec.nomLike(filter.getNom()));

        if (filter.getVille() != null) {
            spec = spec.and(EtablissementScolaireSpec.villeEqual(filter.getVille()));
        }

        if (filter.getType() != null) {
            spec = spec.and(EtablissementScolaireSpec.typeEqual(filter.getType()));
        }

        return etablissementScolaireRepos.findAll(spec);
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
    public void createNewFraterie(FraterieDTO fraterieDTO, Long dossierId) {
        Fraterie fraterie = new Fraterie();
        fraterie.setBirthDate(fraterieDTO.getBirthDate());
        fraterie.setBirthLocation(fraterieDTO.getBirthLocation());
        fraterie.setNom(fraterieDTO.getNom());
        fraterie.setPrenom(fraterieDTO.getPrenom());
        fraterie.setScolarise(fraterieDTO.getScolarise());
        Dossier dossier = dossierRepos.findOne(dossierId);
        fraterie.setCandidat(dossier.getCandidat());
        fraterie.setValide(false);
        if (fraterieDTO.getEtablissement() != null) {
            fraterie.setEtablissement(etablissementScolaireRepos.findOne(fraterieDTO.getEtablissement().getId()));
        }
        if (fraterieDTO.getNiveau() != null) {
            fraterie.setNiveau(niveauEtudeRepos.findOne(fraterieDTO.getNiveau().getId()));
        }
        if (fraterieDTO.getFiliere() != null) {
            fraterie.setFiliere(filiereRepos.findOne(fraterieDTO.getFiliere().getId()));
        }
        fraterieRepos.save(fraterie);
    }

    @Override
    public void deleteFraterie(Long fraterieId) {
        Fraterie fraterie = fraterieRepos.findOne(fraterieId);
        fraterieRepos.delete(fraterie);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> submitInscription(Long dossierId) {
        Set<String> validationErrors = new HashSet<String>();

        validatedDossier(dossierId, validationErrors);
        validateInfoParent(dossierId, validationErrors);

        if (validationErrors.isEmpty()) {
            Dossier dossier = dossierRepos.findOne(dossierId);
            validationProcessService.startProcess(dossier);
        }

        return new ArrayList<String>(validationErrors);
    }

    private void validatedDossier(Long dossierId, Set<String> errors) {
        Dossier dossier = dossierRepos.findOne(dossierId);
        if (!validator.validate(dossier).isEmpty()) {
            errors.add(messageBean.getMessage("missingDossierInfo"));
        } else {
            NiveauEtude niveauEtude = dossier.getNiveauEtude();
            if (niveauEtude.getId() == GlobalParameters.PETITE_SECTION) {
                Integer validBirthYear = DateUtils.currentYear() - niveauEtude.getAnnee();
                Integer birthYear = DateUtils.getYear(dossier.getCandidat().getBirthDate());

                if (birthYear.compareTo(validBirthYear) != 0) {
                    errors.add(messageBean.getMessage("petiteSectionAge"));
                }
            } else if (niveauEtude.getAnnee() == null) {
                if (dossier.getCandidat().getBacSerie() == null
                        || dossier.getCandidat().getBacYear() == null) {
                    errors.add(messageBean.getMessage("bacRequired"));
                    return;
                }

                ValueList bacSerie = dossier.getCandidat().getBacSerie();
                if (niveauEtude.getId() == GlobalParameters.BAC_SGT_ECO
                        && bacSerie.getId() != GlobalParameters.BAC_ECO) {
                    errors.add(messageBean.getMessage("bacECORequired"));
                    return;
                }

                if (niveauEtude.getId() == GlobalParameters.BAC_AUTRES
                        && bacSerie.getId() == GlobalParameters.BAC_ECO) {
                    errors.add(messageBean.getMessage("bacAutresRequired"));
                    return;
                }
            }
        }
    }

    private void validateInfoParent(Long dossierId, Set<String> errors) {
        Map<ParentType, InfoParent> infoParentMap = new HashMap<ParentType, InfoParent>();
        for (InfoParent infoParent : infoParentRepos.findByDossierId(dossierId)) {
            infoParentMap.put(infoParent.getParentType(), infoParent);
        }

        InfoParent pere = infoParentMap.get(ParentType.PERE);
        InfoParent mere = infoParentMap.get(ParentType.MERE);
        InfoParent tuteur = infoParentMap.get(ParentType.TUTEUR);

        if (!pere.isInfoParentEmpty() || !mere.isInfoParentEmpty() || !tuteur.isInfoParentEmpty()) {
            if (!pere.isInfoParentEmpty() && !validator.validate(pere).isEmpty()) {
                errors.add(messageBean.getMessage("missingParentInfo"));
            }

            if (!mere.isInfoParentEmpty() && !validator.validate(mere).isEmpty()) {
                errors.add(messageBean.getMessage("missingParentInfo"));
            }

            if (!tuteur.isInfoParentEmpty() && !validator.validate(tuteur).isEmpty()) {
                errors.add(messageBean.getMessage("missingParentInfo"));
            }
        } else {
            errors.add(messageBean.getMessage("requiredParentInfo"));
        }
    }
}
