package com.gsr.myschool.common.shared.dto;

import com.google.gwt.thirdparty.guava.common.base.Objects;
import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.business.InfoParent;
import com.gsr.myschool.server.business.User;
import org.adorsys.xlseasy.annotation.CellAlign;
import org.adorsys.xlseasy.annotation.Sheet;
import org.adorsys.xlseasy.annotation.SheetCellStyle;
import org.adorsys.xlseasy.annotation.SheetColumn;

import java.util.List;

@Sheet(autoSizeColumns = true,
        columnOrder = {
                "compte", "numDossier", "candidat", "etablissement", "pere",
                "mere", "tuteur", "filiere", "niveauEtude", "statut"
        })
public class DossierMultiple {
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true), columnName = "Compte")
    private String compte;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true), columnName = "Num dossier")
    private String numDossier;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true), columnName = "Candidat")
    private String candidat;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true), columnName = "Etablissement")
    private String etablissement;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true), columnName = "Père")
    private String pere;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true), columnName = "Mère")
    private String mere;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true), columnName = "Tuteur")
    private String tuteur;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true), columnName = "Filière")
    private String filiere;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true), columnName = "Niveau d'étude")
    private String niveauEtude;
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true), columnName = "Statut")
    private String statut;

    public DossierMultiple() {
    }

    public DossierMultiple(User user, Dossier dossier, List<InfoParent> infoParents) {
        compte = user.getEmail();
        numDossier = dossier.getGeneratedNumDossier();
        statut = dossier.getStatus().toString();
        if (null != dossier.getCandidat()) {
            candidat = dossier.getCandidat().getFirstname() + " " + dossier.getCandidat().getLastname();
        }

        if (null != dossier.getScolariteActuelle().getEtablissement()) {
            etablissement = dossier.getScolariteActuelle().getEtablissement().getNom();
        }

        if (null != dossier.getFiliere()) {
            filiere = dossier.getFiliere().getNom();
        }

        if (null != dossier.getNiveauEtude()) {
            niveauEtude = dossier.getNiveauEtude().getNom();
        }

        for (InfoParent item : infoParents) {
            switch (item.getParentType()) {
                case PERE:
                    pere = Objects.firstNonNull(item.getPrenom(), "") + " " + Objects.firstNonNull(item.getNom(), "");
                    break;
                case MERE:
                    mere = Objects.firstNonNull(item.getPrenom(), "") + " " + Objects.firstNonNull(item.getNom(), "");
                    break;
                case TUTEUR:
                    tuteur = Objects.firstNonNull(item.getPrenom(), "") + " " + Objects.firstNonNull(item.getNom(), "");
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

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
