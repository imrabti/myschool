package com.gsr.myschool.common.client.resource.message;

import com.google.gwt.i18n.client.Messages;

public interface HelpMessageBundle extends Messages {
    String loginIdHelp();

    String passwordHelp();

    String emailHelp();

    String infoParentNom(String parent);

    String infoParentPrenom(String parent);

    String infoParentLienParente();

    String infoParentFonction(String parent);

    String infoParentInstitution(String parent);

    String infoParentAdresseEmail(String parent);

    String infoParentTelMaison(String parent);

    String infoParentTelTravail(String parent);

    String infoParentTelMobile(String parent);

    String infoParentAdresse(String parent);

    String infoParentDateNaissance(String parent);

    String infoParentLieuNaissance(String parent);

    String infoParentNationnalite(String parent);

    String infoParentCivilite();

    String candidatNom();

    String candidatPrenom();

    String candidatDateNaissance();

    String candidatLieuNaissance();

    String candidatAdresseEmail();

    String candidatTelephone();

    String candidatCin();

    String candidatCne();

    String candidatNationnalite();

    String candidatBacSerie();

    String candidatBacAnnee();

    String fraterieNom();

    String frateriePrenom();

    String fraterieNumDossier();

    String fraterieClasse();

    String fraterieNiveau();

    String fraterieEtablissement();

    String fraterieType();

    String scolariteEtablissement();

    String scolariteNiveauEtude();

    String scolariteTypeEnseignement();

    String niveauScolaireFiliere();

    String niveauScolaireNiveauEtudes();
}
