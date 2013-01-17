/***********************************************************************
 * Module:  PieceJustif.java
 * Author:  mbouayad
 * Purpose: Defines the Class PieceJustif
 ***********************************************************************/

package com.gsr.myschool.server.business.core;

@Entity
public class PieceJustif implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    private String nom;

    /**
     * Empty constructor which is required by Hibernate
     */
    public PieceJustif() {
        // TODO Auto-generated constructor stub
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
        if (this.nom != null)
            hashCode = 29 * hashCode + nom.hashCode();
        return hashCode;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer ret = new StringBuffer();
        ret.append("com.gsr.myschool.server.business.core.PieceJustif: ");
        ret.append("id='" + id + "'");
        ret.append("nom='" + nom + "'");
        return ret.toString();
    }

}