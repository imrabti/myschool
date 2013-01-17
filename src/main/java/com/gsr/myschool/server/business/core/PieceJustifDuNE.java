/***********************************************************************
 * Module:  PieceJustifDuNE.java
 * Author:  mbouayad
 * Purpose: Defines the Class PieceJustifDuNE
 ***********************************************************************/

package com.gsr.myschool.server.business.core;

@Entity
public class PieceJustifDuNE implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public PieceJustif pieceJustif;
    public NiveauEtude niveauEtude;

    /**
     * Empty constructor which is required by Hibernate
     */
    public PieceJustifDuNE() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @pdGenerated default parent getter
     */
    public PieceJustif getPieceJustif() {
        return pieceJustif;
    }

    /**
     * @param newPieceJustif
     * @pdGenerated default parent setter
     */
    public void setPieceJustif(PieceJustif newPieceJustif) {
        this.pieceJustif = newPieceJustif;
    }

    /**
     * @pdGenerated default parent getter
     */
    public NiveauEtude getNiveauEtude() {
        return niveauEtude;
    }

    /**
     * @param newNiveauEtude
     * @pdGenerated default parent setter
     */
    public void setNiveauEtude(NiveauEtude newNiveauEtude) {
        this.niveauEtude = newNiveauEtude;
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
        return hashCode;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer ret = new StringBuffer();
        ret.append("com.gsr.myschool.server.business.core.PieceJustifDuNE: ");
        ret.append("id='" + id + "'");
        return ret.toString();
    }

}