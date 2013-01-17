/***********************************************************************
 * Module:  NiveauEtude.java
 * Author:  mbouayad
 * Purpose: Defines the Class NiveauEtude
 ***********************************************************************/

package com.gsr.myschool.server.business.core;

@Entity
@UniqueConstraint(columnNames = {"filiere", "annee"})
@UniqueConstraint(columnNames = {"filiere", "nom"})
public class NiveauEtude implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer annee;
    private String nom;

    public Filiere filiere;

    /**
     * Empty constructor which is required by Hibernate
     */
    public NiveauEtude() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @pdGenerated default parent getter
     */
    public Filiere getFiliere() {
        return filiere;
    }

    /**
     * @param newFiliere
     * @pdGenerated default parent setter
     */
    public void setFiliere(Filiere newFiliere) {
        this.filiere = newFiliere;
    }

    /**
     * Get value of id
     *
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set value of id
     *
     * @param newId
     */
    public void setId(Integer newId) {
        this.id = newId;
    }

    /**
     * Get value of annee
     *
     * @return annee
     */
    public Integer getAnnee() {
        return annee;
    }

    /**
     * Set value of annee
     *
     * @param newAnnee
     */
    public void setAnnee(Integer newAnnee) {
        this.annee = newAnnee;
    }

    /**
     * Get value of nom
     *
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Set value of nom
     *
     * @param newNom
     */
    public void setNom(String newNom) {
        this.nom = newNom;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object other) {


        //TODO to be implemented
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        int hashCode = 0;
        if (this.id != null)
            hashCode = 29 * hashCode + id.hashCode();
        if (this.annee != null)
            hashCode = 29 * hashCode + annee.hashCode();
        if (this.nom != null)
            hashCode = 29 * hashCode + nom.hashCode();
        return hashCode;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer ret = new StringBuffer();
        ret.append("com.gsr.myschool.server.business.core.NiveauEtude: ");
        ret.append("id='" + id + "'");
        ret.append("annee='" + annee + "'");
        ret.append("nom='" + nom + "'");
        return ret.toString();
    }

}