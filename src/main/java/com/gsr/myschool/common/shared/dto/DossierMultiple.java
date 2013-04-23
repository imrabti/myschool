package com.gsr.myschool.common.shared.dto;

import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.business.InfoParent;
import com.gsr.myschool.server.business.User;

import java.util.List;

public class DossierMultiple {
    private String compte;
    private String numDossier;
    private String candidat;
    private String etablissement;
    private String pere;
    private String mere;
    private String tuteur;
    private String filiere;
    private String niveauEtude;

    public DossierMultiple() {
    }

    public DossierMultiple(User user, Dossier dossier, List<InfoParent> infoParents) {
        compte = user.getEmail();
        numDossier = dossier.getGeneratedNumDossier();
        candidat = dossier.getCandidat().getFirstname() + " " + dossier.getCandidat().getLastname();
        etablissement = dossier.getScolariteActuelle().getEtablissement().getNom();
        filiere = dossier.getFiliere().getNom();
        niveauEtude = dossier.getNiveauEtude().getNom();

        for (InfoParent item : infoParents) {
            switch (item.getParentType()) {
                case PERE:
                    pere = item.getPrenom() + " " + item.getNom();
                    break;
                case MERE:
                    mere = item.getPrenom() + " " + item.getNom();
                    break;
                case TUTEUR:
                    tuteur = item.getPrenom() + " " + item.getNom();
                    break;
            }
        }
    }

    public String getCompte() {
        return compte;
    }

    public void setCompte(String compte) {
        this.compte = compte;
    }

    public String getNumDossier() {
        return numDossier;
    }

    public void setNumDossier(String numDossier) {
        this.numDossier = numDossier;
    }

    public String getCandidat() {
        return candidat;
    }

    public void setCandidat(String candidat) {
        this.candidat = candidat;
    }

    public String getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(String etablissement) {
        this.etablissement = etablissement;
    }

    public String getPere() {
        return pere;
    }

    public void setPere(String pere) {
        this.pere = pere;
    }

    public String getMere() {
        return mere;
    }

    public void setMere(String mere) {
        this.mere = mere;
    }

    public String getTuteur() {
        return tuteur;
    }

    public void setTuteur(String tuteur) {
        this.tuteur = tuteur;
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    public String getNiveauEtude() {
        return niveauEtude;
    }

    public void setNiveauEtude(String niveauEtude) {
        this.niveauEtude = niveauEtude;
    }
}
