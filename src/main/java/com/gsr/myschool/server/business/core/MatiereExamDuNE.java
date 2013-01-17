/***********************************************************************
 * Module:  MatiereExamDuNE.java
 * Author:  mbouayad
 * Purpose: Defines the Class MatiereExamDuNE
 ***********************************************************************/

package com.gsr.myschool.server.business.core;

@Entity
public class MatiereExamDuNE implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public NiveauEtude niveauEtude;
    public MatiereExamen matiereExamen;

    /**
     * Empty constructor which is required by Hibernate
     */
    public MatiereExamDuNE() {
        // TODO Auto-generated constructor stub
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
     * @pdGenerated default parent getter
     */
    public MatiereExamen getMatiereExamen() {
        return matiereExamen;
    }

    /**
     * @param newMatiereExamen
     * @pdGenerated default parent setter
     */
    public void setMatiereExamen(MatiereExamen newMatiereExamen) {
        this.matiereExamen = newMatiereExamen;
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
        ret.append("com.gsr.myschool.server.business.core.MatiereExamDuNE: ");
        ret.append("id='" + id + "'");
        return ret.toString();
    }

}